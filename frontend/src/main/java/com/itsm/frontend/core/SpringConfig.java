package com.itsm.frontend.core;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.State;
import com.itsm.frontend.factory.ClientFactory;
import com.itsm.frontend.factory.ProductFactory;
import com.itsm.frontend.factory.StateFactory;
import com.itsm.frontend.factory.TransactionFactory;
import com.itsm.frontend.session.ConsoleGreeter;
import com.itsm.frontend.session.PasswordEncryptor;
import com.itsm.frontend.session.UserSessionHolder;
import com.itsm.frontend.storage.Storage;
import com.itsm.frontend.storage.imp.UserStorage;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.itsm.frontend")
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class SpringConfig {

    @Autowired
    private Environment environment;


    @Value("${database.url}")
    private String URL;

    @Value("${database.user}")
    private String databaseUsername;


    @Value("${database.password}")
    private String databasePassword;

    @Value("${database,driver}")
    private String databaseDriver;

    @Bean
    public UserSessionHolder userSessionHolder() {return new UserSessionHolder();}

    @Bean
    public PasswordEncryptor passwordEncryptor() {return new PasswordEncryptor();}

    @Bean
    public DataSource ds() {
        ViburDBCPDataSource ds = new ViburDBCPDataSource();

        ds.setDriverClassName(databaseDriver);
        ds.setJdbcUrl(URL);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);

        ds.start();

        return ds;
    }

    @Bean
    public StateFactory stateFactory() {
        return new StateFactory();
    }

    @Bean
    public ClientFactory clientFactory(Storage<State> stateStorage) {
        return new ClientFactory(stateStorage);
    }

    @Bean
    public ProductFactory productFactory(Storage<State> stateStorage) {
        return new ProductFactory(stateStorage);
    }

    @Bean
    public TransactionFactory transactionFactory(Storage<Client> clientStorage, Storage<Product> productStorage){
        return new TransactionFactory(clientStorage,productStorage);
    }



    @Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(ds);
        liquibase.setChangeLog("classpath:liquibase/db.changelog.master.xml");
        liquibase.setContexts("development, production");
        return liquibase;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.format_sql",   environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql",     environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect",      environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sessionFactory(JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties, DataSource ds) {
        LocalContainerEntityManagerFactoryBean factoryBean
                = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(ds);
        factoryBean.setPackagesToScan("com.itsm.common.entity");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(jpaProperties);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

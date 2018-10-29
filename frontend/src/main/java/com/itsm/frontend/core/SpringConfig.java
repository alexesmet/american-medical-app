package com.itsm.frontend.core;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.State;
import com.itsm.frontend.storage.ClientStorage;
import com.itsm.frontend.storage.ProductStorage;
import com.itsm.frontend.storage.StateStorage;
import com.itsm.frontend.factory.ClientFactory;
import com.itsm.frontend.factory.ProductFactory;
import com.itsm.frontend.factory.StateFactory;
import com.itsm.frontend.factory.TransactionFactory;
import com.itsm.frontend.storage.Storage;
import com.itsm.frontend.util.AuditOperationManager;
import com.itsm.frontend.util.TransactionManager;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.itsm.frontend")
@PropertySource("classpath:database.properties")
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
    public StateStorage stateStorage(DataSource dataSource) {
        return new StateStorage(dataSource);
    }

    @Bean
    public ClientStorage clientStorage(Storage<State> stateStorage, DataSource dataSource) {
        return new ClientStorage(dataSource, stateStorage);
    }

    @Bean
    public ProductStorage productStorage(Storage<State> stateStorage, DataSource dataSource) {
        return new ProductStorage(dataSource, stateStorage);
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
    public TransactionManager transactionManager(){
        return new TransactionManager(URL);
    }

    @Bean
    public AuditOperationManager auditOperationManager(){
        return new AuditOperationManager(URL);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(ds);
        liquibase.setChangeLog("classpath:liquibase/db.changelog.master.xml");
        liquibase.setContexts("development, production");
        return liquibase;
    }

}

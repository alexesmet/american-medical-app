package com.itsm.core;

import com.itsm.factory.ClientFactory;
import com.itsm.factory.ProductFactory;
import com.itsm.factory.StateFactory;
import com.itsm.factory.TransactionFactory;
import com.itsm.storage.ClientStorage;
import com.itsm.storage.ProductStorage;
import com.itsm.storage.StateStorage;
import com.mysql.cj.jdbc.MysqlDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.itsm")
@PropertySource("classpath:database.properties")
public class SpringConfig {

    @Autowired
    private Environment environment;

    @Value("${database.fullurl}")
    private String URL;

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        return dataSource;
    }

    @Bean
    public StateStorage stateStorage() {
        return new StateStorage(URL);
    }

    @Bean
    public ClientStorage clientStorage(StateStorage stateStorage) {
        return new ClientStorage(URL, stateStorage);
    }

    @Bean
    public ProductStorage productStorage(StateStorage stateStorage) {
        return new ProductStorage(URL, stateStorage);
    }

    @Bean
    public StateFactory stateFactory() {
        return new StateFactory();
    }

    @Bean
    public ClientFactory clientFactory(StateStorage stateStorage) {
        return new ClientFactory(stateStorage);
    }

    @Bean
    public ProductFactory productFactory(StateStorage stateStorage) {
        return new ProductFactory(stateStorage);
    }

    @Bean
    public TransactionFactory transactionFactory(ClientStorage clientStorage, ProductStorage productStorage){
        return new TransactionFactory(clientStorage,productStorage);
    }

    @Bean
    public TransactionManager transactionManager(TransactionFactory transactionFactory){
        return new TransactionManager(URL,transactionFactory);
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

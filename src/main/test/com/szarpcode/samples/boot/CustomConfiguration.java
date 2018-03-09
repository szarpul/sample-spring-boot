package com.szarpcode.samples.boot;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.szarpcode.samples.boot")
public class CustomConfiguration {

    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean create(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.szarpcode.samples.boot");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(vendorAdapter);
        bean.setJpaProperties(additionalJpaProperties());
        bean.setPersistenceUnitName("test");

        return bean;
    }

    @Bean
    Properties additionalJpaProperties() {
        Properties properties = new Properties();

        properties.setProperty(
                "hibernate.implicit_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        properties.setProperty("hibernate.id.new_generator_mappings", "false");

        properties.setProperty(
                "hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        poolProperties.setUsername("sa");
        poolProperties.setDriverClassName("org.h2.Driver");
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(create(dataSource()).getObject());
        return manager;
    }



}

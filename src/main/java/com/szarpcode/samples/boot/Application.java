package com.szarpcode.samples.boot;


import com.szarpcode.samples.boot.other.Product;
import com.szarpcode.samples.boot.other.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.math.BigDecimal;
import java.util.Properties;

import javax.sql.DataSource;


@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, ProductRepository productRepository) {

        return (args) -> {

            customerRepository.save(new Customer("Jan", "Kowalski"));
            customerRepository.save(new Customer("Adam", "Nowak"));
            customerRepository.save(new Customer("Paweł", "Kościelny"));
            customerRepository.save(new Customer("Anna", "Ogórek"));

            log.info("-------- All customers -----------");
            customerRepository.findAll().forEach(customer -> log.info(customer.toString()));
            log.info("----------------------------------");

            productRepository.save(new Product("car", new BigDecimal("30000")));
            productRepository.save(new Product("bicycle", new BigDecimal("1000")));
            productRepository.save(new Product("motorbike", new BigDecimal("15000")));

            BigDecimal priceLimit = new BigDecimal("1500");

            log.info(String.format("-------- All products cheaper then %s -----------", priceLimit.toString()));
            productRepository.findProductByPriceLessThan(priceLimit).forEach(p -> log.info(p.toString()));
            log.info("--------------------------------------------------------");


        };
    }

    @Bean
    public Object sampleObject(LocalContainerEntityManagerFactoryBean bean) {
        return new Object();
    }

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

}


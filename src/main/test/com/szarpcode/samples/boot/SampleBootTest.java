package com.szarpcode.samples.boot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleBootTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DataSource dataSource;

    @Test
    public void sth() {
        System.out.println(dataSource);
        customerRepository.save(new Customer("Jan", "Kowalski"));
        customerRepository.findAll().forEach(System.out::println);
    }

}

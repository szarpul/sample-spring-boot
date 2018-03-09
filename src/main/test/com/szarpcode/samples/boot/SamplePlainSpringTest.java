package com.szarpcode.samples.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CustomConfiguration.class)
public class SamplePlainSpringTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test() {
        customerRepository.save(new Customer("Jan", "Kowalski"));
        customerRepository.findAll().forEach(System.out::println);
    }
}

package com.szarpcode.samples.boot;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByFirstName(String firstName);

    Customer findBySurname(String surname);
}

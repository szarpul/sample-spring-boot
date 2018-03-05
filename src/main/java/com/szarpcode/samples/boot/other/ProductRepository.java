package com.szarpcode.samples.boot.other;

import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findProductByPriceLessThan(BigDecimal price);
}

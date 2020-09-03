package com.lapin.test_task.repository;

import com.lapin.test_task.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

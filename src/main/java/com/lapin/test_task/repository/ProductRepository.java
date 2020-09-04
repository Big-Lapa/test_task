package com.lapin.test_task.repository;

import com.lapin.test_task.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//public interface ProductRepository extends CrudRepository<Product, Long> {
//    List<Product> findByName(String name);

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
}

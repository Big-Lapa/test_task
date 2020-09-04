package com.lapin.test_task.repository;

import com.lapin.test_task.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
}

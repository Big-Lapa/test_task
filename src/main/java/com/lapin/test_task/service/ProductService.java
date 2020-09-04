package com.lapin.test_task.service;

import java.util.ArrayList;
import java.util.List;

import com.lapin.test_task.model.Product;
import com.lapin.test_task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll(int pageNumber, int rowPerPage) {
        List<Product> users = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        productRepository.findAll(sortedByLastUpdateDesc).forEach(users::add);
        return users;
    }

    public Product save(Product product) throws Exception {
        if (StringUtils.isEmpty(product.getName())) {
            throw new Exception("Name is required");
        }
        if (StringUtils.isEmpty(product.getPrice())) {
            throw new Exception("Price is required");
        }
        if (product.getId() != null && existsById(product.getId())) {
            throw new Exception("Product with id: " + product.getId() + " already exists");
        }
        return productRepository.save(product);
    }

    public void update(Product product) throws Exception {
        if (StringUtils.isEmpty(product.getName())) {
            throw new Exception("Name is required");
        }
        if (StringUtils.isEmpty(product.getPrice())) {
            throw new Exception("Price is required");
        }
        if (!existsById(product.getId())) {
            throw new Exception("Cannot find Product with id: " + product.getId());
        }
        productRepository.save(product);
    }

    public void deleteById(Long id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find User with id: " + id);
        } else {
            productRepository.deleteById(id);
        }
    }

    public Long count() {
        return productRepository.count();
    }
}
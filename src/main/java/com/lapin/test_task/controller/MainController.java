package com.lapin.test_task.controller;

import com.lapin.test_task.model.Product;
import com.lapin.test_task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String main(
            @RequestParam(name = "name", required = false, defaultValue = "User") String name,
            Map<String, Object> model) {
        model.put("name", name);
        return "main";
    }

    @GetMapping("/products")
    public String products(Map<String, Object> model) {
        Iterable<Product> products = productRepository.findAll();
        model.put("product", products);
        return "products";
    }

    @PostMapping("/products")
    public String add(@RequestParam String name,
                      @RequestParam BigDecimal price,
                      @RequestParam("date")
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                      Map<String, Object> model) {
        Product product = new Product(name, price, date);
        productRepository.save(product);
        Iterable<Product> products = productRepository.findAll();
        model.put("product", products);
        return "products";
    }

}

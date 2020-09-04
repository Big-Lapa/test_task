package com.lapin.test_task.controller;


import com.lapin.test_task.service.ProductService;
import com.lapin.test_task.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private ProductService productService;

    @Value("${msg.title}")
    private String title;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }

    @GetMapping(value = "/products")
    public String getProducts(Model model,
                              @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Product> users = productService.findAll(pageNumber, ROW_PER_PAGE);

        long count = productService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("products", users);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "products";
    }

    @GetMapping(value = "/product/{id}")
    public String getProductById(Model model, @PathVariable long id) {
        Product product = null;
        try {
            product = productService.findById(id);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping(value = {"/products/add"})
    public String showAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("add", true);
        model.addAttribute("product", product);

        return "edit";
    }

    @PostMapping(value = "/products/add")
    public String addProduct(Model model,  @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                             @ModelAttribute("product") Product product) {
        try {
            Product newProduct = productService.save(product);
            return "redirect:/products/";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "products";
        }
    }

    @GetMapping(value = {"/products/{id}/edit"})
    public String showEditProduct(Model model, @PathVariable long id) {
        Product product = null;
        try {
            product = productService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping(value = {"/products/{id}/edit"})
    public String updateProduct(Model model,
                                @PathVariable long id,
                                @ModelAttribute("product") Product product) {
        try {
            product.setId(id);
            productService.update(product);
            return "redirect:/products/" + String.valueOf(product.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "edit";
        }
    }

    @GetMapping(value = {"/products/{id}/delete"})
    public String showDeleteProduct(
            Model model, @PathVariable long id) {
        Product product = null;
        try {
            product = productService.findById(id);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping(value = {"/products/{id}/delete"})
    public String deleteProductById(
            Model model, @PathVariable long id) {
        try {
            productService.deleteById(id);
            return "redirect:/products";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "product";
        }
    }
}
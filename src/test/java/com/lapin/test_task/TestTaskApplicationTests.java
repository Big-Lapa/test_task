package com.lapin.test_task;

import com.lapin.test_task.model.Product;
import com.lapin.test_task.service.ProductService;

import java.util.List;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTaskApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductService productService;

    @Test
    public void testFindAllUsers() {
        List<Product> products = productService.findAll(1, 10);
        assertNotNull(products);
        assertEquals(2, products.size());
        for (Product product : products) {
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getPrice());
        }
    }

    @Test
    public void testSaveUpdateDeleteUser() throws Exception {
        Product product = new Product();
        product.setName("Laptop HP");
        product.setPrice(1200);

        productService.save(product);
        assertNotNull(product.getId());

        Product findProduct = productService.findById(product.getId());
        assertEquals(product.getName(), findProduct.getName());
        assertEquals(product.getPrice(), findProduct.getPrice());

        // update price
        product.setPrice(1500);
        productService.update(product);

        // test after update price
        findProduct = productService.findById(product.getId());
        assertEquals(product.getPrice(), findProduct.getPrice());

        // test delete from DB
        productService.deleteById(product.getId());

        // query after delete
        Product productDel = productService.findById(product.getId());
        assertNull(productDel);
    }
}


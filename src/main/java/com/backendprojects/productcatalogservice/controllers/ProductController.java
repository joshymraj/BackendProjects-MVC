package com.backendprojects.productcatalogservice.controllers;

import com.backendprojects.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        products.add(product);
        return products;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Product 2");
        return product;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return product;
    }
}

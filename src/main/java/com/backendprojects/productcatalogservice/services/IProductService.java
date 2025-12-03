package com.backendprojects.productcatalogservice.services;

import com.backendprojects.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product addProduct(Product product);
}

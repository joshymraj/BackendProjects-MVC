package com.backendprojects.productcatalogservice.controllers;

import com.backendprojects.productcatalogservice.dtos.CategoryDto;
import com.backendprojects.productcatalogservice.dtos.FakeStoreProductDto;
import com.backendprojects.productcatalogservice.dtos.ProductDto;
import com.backendprojects.productcatalogservice.exceptions.ProductNotFoundException;
import com.backendprojects.productcatalogservice.models.Category;
import com.backendprojects.productcatalogservice.models.Product;
import com.backendprojects.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if(products == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(from(product));
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        if(id < 1) {
//            throw new IllegalArgumentException("Product id must be greater than 0");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if(product == null) {
//            throw new ProductNotFoundException("Product not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductDto productDto = from(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productDto;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
        if(product.getCategory() != null) {
            Category category = product.getCategory();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}

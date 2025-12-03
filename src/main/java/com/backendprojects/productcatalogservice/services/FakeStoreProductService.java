package com.backendprojects.productcatalogservice.services;

import com.backendprojects.productcatalogservice.dtos.FakeStoreProductDto;
import com.backendprojects.productcatalogservice.models.Category;
import com.backendprojects.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String restUrl = "https://fakestoreapi.com/products/{id}";
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.getForEntity(restUrl, FakeStoreProductDto.class, id);
        if(fakeStoreProductDto.hasBody() && fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return from(fakeStoreProductDto.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String restUrl = "https://fakestoreapi.com/products/";
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDto = restTemplate.getForEntity(restUrl, FakeStoreProductDto[].class);
        if(fakeStoreProductDto.hasBody() && fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            List<Product> products = new ArrayList<>();
            for(FakeStoreProductDto dto : fakeStoreProductDto.getBody()) {
                products.add(from(dto));
            }
            return products;
        }
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}

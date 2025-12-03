package com.backendprojects.productcatalogservice.dtos;

import com.backendprojects.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private CategoryDto category;
}

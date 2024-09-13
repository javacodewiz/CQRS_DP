package com.javacodewiz.service;

import com.javacodewiz.dto.ProductEvent;
import com.javacodewiz.entity.Product;

public interface ProductService {

    public Product saveProduct(ProductEvent event);
    public Product updateProduct(ProductEvent event);
    public String deleteProduct(ProductEvent event);
}

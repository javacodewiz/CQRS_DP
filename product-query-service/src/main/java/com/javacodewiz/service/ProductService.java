package com.javacodewiz.service;


import com.javacodewiz.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAll();
    public Product getById(Long id);

}

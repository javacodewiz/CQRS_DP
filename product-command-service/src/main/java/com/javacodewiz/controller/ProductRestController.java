package com.javacodewiz.controller;


import com.javacodewiz.dto.ProductEvent;
import com.javacodewiz.entity.Product;
import com.javacodewiz.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductRestController {


    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody ProductEvent event)
    {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(event));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductEvent event)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(event));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody ProductEvent event)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(event));
    }
}

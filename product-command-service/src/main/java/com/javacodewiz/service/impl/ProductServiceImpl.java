package com.javacodewiz.service.impl;

import com.javacodewiz.dto.ProductEvent;
import com.javacodewiz.entity.Product;
import com.javacodewiz.exception.ResourceNotFoundException;
import com.javacodewiz.exception.ResourceNotSaveException;
import com.javacodewiz.repository.ProductRepository;
import com.javacodewiz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private KafkaTemplate<String ,Object> kafkaTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Product saveProduct(ProductEvent event) {
        Product p ;
        if(event.getType().equalsIgnoreCase("create-event"))
        {
           p= event.getProduct();
           p=productRepository.save(p);
            kafkaTemplate.send("cqrs",3,null,event);
           return p;
        }
       else {
           throw new ResourceNotSaveException("please send the create type");
        }
    }

    @Override
    public Product updateProduct(ProductEvent event) {
        Product p;
        if(event.getType().equalsIgnoreCase("update-event"))
        {
            p= event.getProduct();
            Product product = productRepository.findById(p.getId()).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
            product.setPName(p.getPName());
            product.setPPrice(p.getPPrice());
            product.setPStock(p.getPStock());
            product=productRepository.save(product);
            kafkaTemplate.send("cqrs",3,null,event);
            return product;
        }
        else {
            throw new ResourceNotSaveException("please send the update type");
        }
    }

    @Override
    public String deleteProduct(ProductEvent event) {
        if(event.getType().equalsIgnoreCase("delete-event"))
        {
            Product product = productRepository.findById(event.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
            productRepository.delete(product);
            kafkaTemplate.send("cqrs",3,null,event);
            return "Product has been deleted";
        }
        else {
            throw  new ResourceNotSaveException("please send delete type");
        }

    }
}

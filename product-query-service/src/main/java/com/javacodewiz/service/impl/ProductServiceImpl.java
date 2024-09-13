package com.javacodewiz.service.impl;

import com.javacodewiz.dto.ProductEvent;
import com.javacodewiz.entity.Product;
import com.javacodewiz.exception.ResourceNotFoundException;
import com.javacodewiz.repository.ProductRepository;
import com.javacodewiz.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired


    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
    }

//    cqrs
    @KafkaListener(topics="cqrs",groupId = "cqrs_dp",topicPartitions = {
            @TopicPartition(topic = "cqrs", partitions = {"3"})
    })
    void listenFromkafka(ProductEvent event)
    {
        Product p ;
        switch(event.getType())
        {
            case "create-event":
            p = event.getProduct();
            productRepository.save(p);
            break;
            case "update-event":
                p = event.getProduct();
                Product product = productRepository.findById(p.getId()).orElseThrow(()->new ResourceNotFoundException("Product not found with Id : "+p.getId()));
                product.setPName(p.getPName());
                product.setPPrice(p.getPPrice());
                product.setPStock(p.getPStock());
                productRepository.save(product);
                break;
            case "delete-event":
                p = productRepository.findById(event.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
                productRepository.delete(p);
                break;
            default:
                log.info("Soming goes wrong...");
                break;
        }

    }
}

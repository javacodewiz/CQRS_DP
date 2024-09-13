package com.javacodewiz.dto;


import com.javacodewiz.entity.Product;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {

    private String type;
    private Product product;
}

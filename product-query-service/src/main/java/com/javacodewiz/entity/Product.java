package com.javacodewiz.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_qr_tbl")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pPrice")
    private double pPrice;
    @JsonProperty("pStock")
    private int pStock;

}

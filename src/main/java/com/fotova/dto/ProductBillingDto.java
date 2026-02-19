package com.fotova.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBillingDto {
    private String productName;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

}

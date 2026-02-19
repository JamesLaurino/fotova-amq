package com.fotova.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingDetailDtoAmq {
    private String UUID = java.util.UUID.randomUUID().toString();
    private String email;
    private List<ProductBillingDto> productBillingDtos;
}

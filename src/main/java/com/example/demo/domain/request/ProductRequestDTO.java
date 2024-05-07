package com.example.demo.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {
    @NotBlank
    private String name;

    @DecimalMin(value = "0.01")
    private BigDecimal price;
}

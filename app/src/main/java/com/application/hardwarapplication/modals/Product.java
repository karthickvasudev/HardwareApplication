package com.application.hardwarapplication.modals;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product implements Serializable {
    private String id;
    private String name;
    private Double quantity;
    private Double price;
}

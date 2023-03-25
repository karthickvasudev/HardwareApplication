package com.application.hardwarapplication.modals;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLines {
    private String orderId;
    private String id;
    private String name;
    private Double quantity;
    private Double price;
}

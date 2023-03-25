package com.application.hardwarapplication.modals;

import com.application.hardwarapplication.utils.PAYMENT_TYPE;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class Payment {
    private String orderId;
    private String customerId;
    private String id;
    private String date;
    private String amount;
    private PAYMENT_TYPE type;
}

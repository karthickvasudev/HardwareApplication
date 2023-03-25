package com.application.hardwarapplication.modals;

import com.application.hardwarapplication.utils.PAYMENT_STATUS;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class Order {
    private String orderId;
    private String customerId;
    private Customer customer;
    private List<OrderLines> orderLines;
    private OrderPaymentDetails paymentDetails;
    private List<Payment> payments;
    private String orderDate;

    @Data
    @Builder
    @ToString
    public static class OrderPaymentDetails {
        private String orderId;
        private Double totalBillAmount;
        private Double balance;
        private Double advance;
        private Double discount;
        private Double paidAmount;
        private PAYMENT_STATUS status;
    }
}

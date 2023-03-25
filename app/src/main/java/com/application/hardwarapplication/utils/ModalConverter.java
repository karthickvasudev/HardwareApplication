package com.application.hardwarapplication.utils;

import android.database.Cursor;

import com.application.hardwarapplication.modals.Customer;
import com.application.hardwarapplication.modals.Order;
import com.application.hardwarapplication.modals.OrderLines;
import com.application.hardwarapplication.modals.Payment;
import com.application.hardwarapplication.modals.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalConverter {
    public static Optional<Customer> convertToCustomer(Cursor cursor) {
        Optional<Customer> optional = Optional.ofNullable(null);
        if (cursor.moveToFirst()) {
            optional = Optional.of(
                    Customer.builder()
                            .id(cursor.getString(0))
                            .name(cursor.getString(1))
                            .phoneNumber(cursor.getString(2))
                            .address(cursor.getString(3))
                            .createdOn(cursor.getString(4))
                            .updatedOn(cursor.getString(5))
                            .build());

        }
        return optional;
    }

    public static List<Customer> convertToCustomerList(Cursor cursor) {
        List<Customer> customerList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                customerList.add(Customer.builder()
                        .id(cursor.getString(0))
                        .name(cursor.getString(1))
                        .phoneNumber(cursor.getString(2))
                        .address(cursor.getString(3))
                        .createdOn(cursor.getString(4))
                        .updatedOn(cursor.getString(5))
                        .build());
            } while (cursor.moveToNext());
        }
        return customerList;
    }

    public static List<Product> covertToProductList(Cursor cursor) {
        List<Product> productList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                productList.add(Product.builder()
                        .id(cursor.getString(0))
                        .name(cursor.getString(1))
                        .quantity(cursor.getDouble(2))
                        .price(cursor.getDouble(3))
                        .build());
            } while (cursor.moveToNext());
        }
        return productList;
    }

    public static Order convertToOrder(Cursor cursor) {
        cursor.moveToFirst();
        return Order.builder()
                .orderId(cursor.getString(0))
                .customerId(cursor.getString(1))
                .orderDate(cursor.getString(2))
                .build();
    }

    public static List<OrderLines> convertToOrderLinesProductList(Cursor cursor) {
        List<OrderLines> orderLines = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                orderLines.add(OrderLines.builder()
                        .orderId(cursor.getString(0))
                        .id(cursor.getString(1))
                        .name(cursor.getString(2))
                        .quantity(Double.parseDouble(String.valueOf(cursor.getInt(3))))
                        .price(Double.parseDouble(String.valueOf(cursor.getInt(4))))
                        .build());
            }while (cursor.moveToNext());
        }
        return orderLines;
    }

    public static Order.OrderPaymentDetails covertToPaymentDetails(Cursor cursor) {
        cursor.moveToFirst();
        return Order.OrderPaymentDetails.builder()
                .orderId(cursor.getString(0))
                .totalBillAmount(Double.parseDouble(String.valueOf(cursor.getString(1))))
                .balance(Double.parseDouble(String.valueOf(cursor.getInt(2))))
                .advance(Double.parseDouble(String.valueOf(cursor.getInt(3))))
                .discount(Double.parseDouble(String.valueOf(cursor.getInt(4))))
                .paidAmount(Double.parseDouble(String.valueOf(cursor.getInt(5))))
                .status(PAYMENT_STATUS.valueOf(cursor.getString(6)))
                .build();
    }

    public static List<Payment> convertToPaymentList(Cursor cursor) {
        List<Payment> paymentList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                paymentList.add(
                        Payment.builder()
                                .orderId(cursor.getString(0))
                                .customerId(cursor.getString(1))
                                .id(cursor.getString(2))
                                .date(cursor.getString(3))
                                .amount(cursor.getString(4))
                                .type(PAYMENT_TYPE.valueOf(cursor.getString(5)))
                                .build()
                );
            }while (cursor.moveToNext());
        }
        return paymentList;
    }
}

package com.application.hardwarapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.hardwarapplication.modals.Order;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;

public class PaymentDetailsDatabase extends DatabaseHelper{

    private final SQLiteDatabase readableDatabase;
    private final SQLiteDatabase writableDatabase;

    public PaymentDetailsDatabase(Context context) {
        super(context);
        readableDatabase = super.getReadableDatabase();
        writableDatabase = super.getWritableDatabase();
    }

    public Order.OrderPaymentDetails findPaymentDetailsByOrderId(String orderId){
        String query = QueryBuilder.builder().query(Query.ORDER_FIND_PAYMENT_DETAILS_BY_ORDER_ID).setParameters(orderId).get();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        return ModalConverter.covertToPaymentDetails(cursor);
    }

    public boolean createOrderPaymentDetails(String orderId, Order.OrderPaymentDetails paymentDetails) {
        ContentValues values = new ContentValues();
        values.put("orderId", orderId);
        values.put("totalBillAmount", paymentDetails.getTotalBillAmount());
        values.put("balance", paymentDetails.getBalance());
        values.put("advance", paymentDetails.getAdvance());
        values.put("discount", paymentDetails.getDiscount());
        values.put("paidAmount", paymentDetails.getPaidAmount());
        values.put("status", paymentDetails.getStatus().name());
        return writableDatabase.insert("order_payment_details", null, values) != -1;
    }
}

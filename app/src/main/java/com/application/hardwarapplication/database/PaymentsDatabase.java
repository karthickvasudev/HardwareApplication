package com.application.hardwarapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.hardwarapplication.modals.Payment;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class PaymentsDatabase extends DatabaseHelper {

    private final SQLiteDatabase readableDatabase;
    private final SQLiteDatabase writableDatabase;
    private final DocumentNumberDatabase documentNumberDatabase;

    public PaymentsDatabase(Context context) {
        super(context);
        readableDatabase = super.getReadableDatabase();
        writableDatabase = super.getWritableDatabase();
        documentNumberDatabase = new DocumentNumberDatabase(context);
    }

    public List<Payment> findPaymentListByOrderId(String orderId){
        String query = QueryBuilder.builder().query(Query.ORDER_FIND_PAYMENTS_BY_ORDER_ID).setParameters(orderId).get();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        return ModalConverter.convertToPaymentList(cursor);
    }

    public boolean createPayment(String orderId, String customerId, List<Payment> payments) {
        List<Boolean> results = new ArrayList<>();
        for (Payment payment : payments) {
            ContentValues values = new ContentValues();
            values.put("orderId", orderId);
            values.put("customerId", customerId);
            values.put("id", documentNumberDatabase.getPaymentId());
            values.put("date", payment.getDate());
            values.put("amount", payment.getAmount());
            values.put("type", payment.getType().name());
            results.add(writableDatabase.insert("payments", null, values) != -1);
            documentNumberDatabase.incrementPaymentId();
        }
        return !results.contains(false);
    }
}

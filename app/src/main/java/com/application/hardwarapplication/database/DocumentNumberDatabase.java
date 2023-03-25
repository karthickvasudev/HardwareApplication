package com.application.hardwarapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;
import com.application.hardwarapplication.utils.Utils;

public class DocumentNumberDatabase extends DatabaseHelper {
    private static final String TABLE_NAME = "document_number";
    private static final String COL_TYPE = "type";
    private static final String COL_VALUE = "value";
    private final Context context;

    public DocumentNumberDatabase(Context context) {
        super(context);
        this.context = context;
    }

    public String getCustomerId() {
        SQLiteDatabase readableDatabase = super.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(Query.DOCUMENT_NUMBER_GET_CUSTOMER_ID, null);
        cursor.moveToFirst();
        String id = cursor.getString(1);
        cursor.close();
        readableDatabase.close();
        return id;
    }

    public void incrementCustomerId() {
        String customerId = getCustomerId();
        customerId = Utils.incrementAndGet(customerId);
        String query = QueryBuilder.builder()
                .query(Query.DOCUMENT_NUMBER_UPDATE_CUSTOMER_ID)
                .setParameters(customerId).get();
        this.getWritableDatabase().execSQL(query);
    }

    public String getOrderId() {
        SQLiteDatabase readableDatabase = super.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(Query.DOCUMENT_NUMBER_GET_ORDER_ID, null);
        cursor.moveToFirst();
        String id = cursor.getString(1);
        cursor.close();
        readableDatabase.close();
        return id;
    }

    public void incrementOrderId() {
        String orderId = getOrderId();
        orderId = Utils.incrementAndGet(orderId);
        String query = QueryBuilder.builder()
                .query(Query.DOCUMENT_NUMBER_UPDATE_ORDER_ID)
                .setParameters(orderId).get();
        this.getWritableDatabase().execSQL(query);
    }

    public String getPaymentId() {
        SQLiteDatabase readableDatabase = super.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(Query.DOCUMENT_NUMBER_GET_PAYMENT_ID, null);
        cursor.moveToFirst();
        String id = cursor.getString(1);
        cursor.close();
        readableDatabase.close();
        return id;
    }
    public void incrementPaymentId() {
        String paymentId = getPaymentId();
        paymentId = Utils.incrementAndGet(paymentId);
        String query = QueryBuilder.builder()
                .query(Query.DOCUMENT_NUMBER_UPDATE_PAYMENT_ID)
                .setParameters(paymentId).get();
        this.getWritableDatabase().execSQL(query);
    }
}

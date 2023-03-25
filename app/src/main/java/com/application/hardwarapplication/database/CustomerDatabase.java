package com.application.hardwarapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.application.hardwarapplication.modals.Customer;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CustomerDatabase extends DatabaseHelper {
    private static String TABLE_NAME = "customers";
    private static String COL_ID = "id";
    private static String COL_NAME = "name";
    private static String COL_PHONE_NUMBER = "phone_number";
    private static String COL_ADDRESS = "address";
    private static String COL_CREATED_ON = "created_on";
    private static String COL_UPDATED_ON = "updated_on";


    private final DocumentNumberDatabase documentNumberDatabase;
    private final Context context;
    private SQLiteDatabase readableDatabase;

    public CustomerDatabase(Context context) {
        super(context);
        this.context = context;
        documentNumberDatabase = new DocumentNumberDatabase(context);
    }

    public Optional<Customer> createCustomer(String name, String phoneNumber, String address) {
        SQLiteDatabase writableDatabase = super.getWritableDatabase();
        String customerId = documentNumberDatabase.getCustomerId();
        ContentValues values = new ContentValues();
        values.put(COL_ID, customerId);
        values.put(COL_NAME, name);
        values.put(COL_PHONE_NUMBER, phoneNumber);
        values.put(COL_ADDRESS, address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put(COL_CREATED_ON, LocalDateTime.now().toString());
        } else {
            values.put(COL_CREATED_ON, "Time library not supported");
        }
        writableDatabase.insert(TABLE_NAME, null, values);
        documentNumberDatabase.incrementCustomerId();
        return customerFindById(customerId);
    }

    public Optional<Customer> customerFindById(String id) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String query = QueryBuilder.builder()
                .query(Query.CUSTOMER_FIND_BY_ID)
                .setParameters(id).get();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        Optional<Customer> optional = ModalConverter.convertToCustomer(cursor);
        cursor.close();
        readableDatabase.close();
        return optional;
    }

    public List<Customer> findAll(){
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(Query.CUSTOMER_FIND_ALL, null);
        List<Customer> customers = ModalConverter.convertToCustomerList(cursor);
        cursor.close();
        readableDatabase.close();
        return customers;
    }

    public Optional<Customer> customerFindByPhoneNumber(String phoneNumber) {
        readableDatabase = super.getReadableDatabase();
        String query = QueryBuilder.builder().query(Query.CUSTOMER_FIND_BY_PHONE_NUMBER).setParameters(phoneNumber).get();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        return ModalConverter.convertToCustomer(cursor);
    }
}

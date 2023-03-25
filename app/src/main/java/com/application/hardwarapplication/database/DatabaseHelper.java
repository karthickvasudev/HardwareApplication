package com.application.hardwarapplication.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.hardwarapplication.utils.Query;

import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATA_BASE_NAME = "hardware_database.db";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> queries = Arrays.asList(Query.CREATE_DOCUMENT_TABLE,
                Query.CREATE_PRODUCT_TABLE_QUERY,
                Query.CREATE_CUSTOMER_TABLE_QUERY,
                Query.ORDER_CREATE_ORDER_TABLE,
                Query.ORDER_CREATE_ORDER_LINES_TABLE,
                Query.ORDER_CREATE_ORDER_PAYMENT_DETAILS_TABLE,
                Query.ORDER_CREATE_ORDER_PAYMENTS_TABLE);
        for (String query : queries) {
            sqLiteDatabase.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void beforeHook() {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.execSQL(Query.CREATE_DOCUMENT_NUMBER_INIT_VALUE);
        writableDatabase.execSQL(Query.INSERT_INITIAL_PRODUCT_STATIC_DATA);
    }


}

package com.application.hardwarapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.hardwarapplication.modals.Product;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;

import java.util.List;

public class ProductDatabase extends DatabaseHelper {
    private static final String TABLE_NAME = "products";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_PRICE = "price";

    public ProductDatabase(Context context) {
        super(context);
    }

    public List<Product> findAllProducts() {
        SQLiteDatabase readableDatabase = super.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(Query.PRODUCT_FIND_ALL, null);
        List<Product> productList = ModalConverter.covertToProductList(cursor);
        cursor.close();
        readableDatabase.close();
        return productList;
    }
}

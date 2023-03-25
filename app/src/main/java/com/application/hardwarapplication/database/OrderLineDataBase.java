package com.application.hardwarapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.hardwarapplication.modals.OrderLines;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class OrderLineDataBase extends DatabaseHelper {

    private final SQLiteDatabase readableDatabase;
    private final SQLiteDatabase writableDatabase;

    public OrderLineDataBase(Context context) {
        super(context);
        readableDatabase = super.getReadableDatabase();
        writableDatabase = super.getWritableDatabase();
    }

    public List<OrderLines> findOrderLinesByOrderId(String orderId){
        String query = QueryBuilder.builder().query(Query.ORDER_LINES_BY_ORDER_ID).setParameters(orderId).get();
        Cursor cursor = readableDatabase.rawQuery(query, null);
        return ModalConverter.convertToOrderLinesProductList(cursor);
    }

    public boolean createOrderLines(String orderId, List<OrderLines> orderLines) {
        List<Boolean> resultList = new ArrayList<>();
        for (OrderLines orderLine : orderLines) {
            ContentValues values = new ContentValues();
            values.put("orderId", orderId);
            values.put("id", orderLine.getId());
            values.put("name", orderLine.getName());
            values.put("quantity", orderLine.getQuantity());
            values.put("price", orderLine.getPrice());
            resultList.add(writableDatabase.insert("order_lines", null, values) != -1);
        }
        return !resultList.contains(false);
    }
}

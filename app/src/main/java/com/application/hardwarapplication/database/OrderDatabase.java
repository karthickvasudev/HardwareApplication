package com.application.hardwarapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.application.hardwarapplication.modals.Customer;
import com.application.hardwarapplication.modals.Order;
import com.application.hardwarapplication.modals.OrderLines;
import com.application.hardwarapplication.modals.Payment;
import com.application.hardwarapplication.utils.ModalConverter;
import com.application.hardwarapplication.utils.Query;
import com.application.hardwarapplication.utils.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class OrderDatabase extends DatabaseHelper {

    private final DocumentNumberDatabase documentNumberDatabase;
    private final Context context;
    private final SQLiteDatabase readableDatabase;
    private final CustomerDatabase customerDatabase;
    private final OrderLineDataBase orderLineDataBase;
    private final PaymentDetailsDatabase paymentDetailsDatabase;
    private final PaymentsDatabase paymentsDatabase;

    private SQLiteDatabase writableDatabase;

    public OrderDatabase(Context context) {
        super(context);
        this.context = context;
        writableDatabase = super.getWritableDatabase();
        readableDatabase = super.getReadableDatabase();
        documentNumberDatabase = new DocumentNumberDatabase(context);
        customerDatabase = new CustomerDatabase(context);
        orderLineDataBase = new OrderLineDataBase(context);
        paymentDetailsDatabase = new PaymentDetailsDatabase(context);
        paymentsDatabase = new PaymentsDatabase(context);
    }

    public Order createOrder(Order order) {
        String orderId = documentNumberDatabase.getOrderId();
        String customerId = order.getCustomerId();
        boolean result = createOrderTable(orderId, customerId, order.getOrderDate());
        if (result) {
            orderLineDataBase.createOrderLines(orderId, order.getOrderLines());
            paymentDetailsDatabase.createOrderPaymentDetails(orderId, order.getPaymentDetails());
            if (order.getOrderLines().size() > 0) {
                paymentsDatabase.createPayment(orderId, customerId, order.getPayments());
            }
        } else {
            Toast.makeText(context, "error in creating order", Toast.LENGTH_SHORT).show();
        }
        documentNumberDatabase.incrementOrderId();
        return findById(orderId);
    }

    public Order findById(String orderId) {
        Order order = getFromOrderById(orderId);
        Customer customer = customerDatabase.customerFindById(order.getCustomerId()).get();
        List<OrderLines> orderLinesByOrderId = orderLineDataBase.findOrderLinesByOrderId(orderId);
        List<Payment> paymentList = paymentsDatabase.findPaymentListByOrderId(orderId);
        order.setCustomer(customer);
        order.setOrderLines(orderLinesByOrderId);
        order.setPaymentDetails(paymentDetailsDatabase.findPaymentDetailsByOrderId(orderId));
        order.setPayments(paymentList);
        return order;
    }

    public Order getFromOrderById(String orderId) {
        String query = QueryBuilder.builder().query(Query.ORDER_FIND_BY_ID).setParameters(orderId).get();
        Cursor cursor_order = readableDatabase.rawQuery(query, null);
        Order order = ModalConverter.convertToOrder(cursor_order);
        cursor_order.close();
        return order;
    }

    private boolean createOrderTable(String orderId, String customerId, String orderDate) {
        ContentValues values = new ContentValues();
        values.put("id", orderId);
        values.put("customerId", customerId);
        values.put("order_date", orderDate);
        long orders = writableDatabase.insert("orders", null, values);
        return orders != -1;
    }

    public List<Order> findAll() {
        Cursor cursor = readableDatabase.rawQuery(Query.ORDER_FIND_ALL, null);
        List<Order> orderList=new ArrayList<>();
        for (String orderId : getListOfOrderIds(cursor)) {
            orderList.add(findById(orderId));
        }
        cursor.close();
        return orderList;
    }

    private List<String> getListOfOrderIds(Cursor cursor) {
        List<String> orderIds=new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                orderIds.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return orderIds;
    }
}

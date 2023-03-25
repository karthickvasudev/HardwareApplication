package com.application.hardwarapplication.utils;

public class Query {

    /* document number queries */
    public static final String CREATE_DOCUMENT_TABLE
            = "CREATE TABLE IF NOT EXISTS document_number (type TEXT PRIMARY KEY,value TEXT);";
    public static final String CREATE_DOCUMENT_NUMBER_INIT_VALUE = "INSERT OR IGNORE INTO document_number(type, value) VALUES " +
            "('customer','C-00001'),('product','PRD-00001'),('order','ORD-00001'),('payment','P-00001');";
    public static final String DOCUMENT_NUMBER_GET_CUSTOMER_ID = "SELECT * FROM document_number WHERE type='customer'";
    public static final String DOCUMENT_NUMBER_UPDATE_CUSTOMER_ID = "UPDATE document_number set value ='PARAM01' WHERE type='customer'";
    public static final String DOCUMENT_NUMBER_GET_ORDER_ID = "SELECT * FROM document_number WHERE type='order'";
    public static final String DOCUMENT_NUMBER_UPDATE_ORDER_ID = "UPDATE document_number set value ='PARAM01' WHERE type='order'";
    public static final String DOCUMENT_NUMBER_GET_PAYMENT_ID = "SELECT * FROM document_number WHERE type='payment'";
    public static final String DOCUMENT_NUMBER_UPDATE_PAYMENT_ID = "UPDATE document_number set value ='PARAM01' WHERE type='payment'";
    /* product queries */
    public static final String CREATE_PRODUCT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS products (id TEXT PRIMARY KEY, " +
            "name TEXT, quantity INTEGER, price INTEGER)";
    public static final String INSERT_INITIAL_PRODUCT_STATIC_DATA = "INSERT OR IGNORE INTO products" +
            "(id,name,quantity,price) VALUES " +
            "('P-00001','Glass',0,0)," +
            "('P-00002','Plywood',0,0)," +
            "('P-00003','Hardware',0,0)," +
            "('P-00004','PVC Door',0,0)," +
            "('P-00005','WPC Door',0,0)," +
            "('P-00006','Wooden Door',0,0)," +
            "('P-00007','Others',0,0);";
    public static final String PRODUCT_FIND_ALL = "SELECT * FROM products";


    /* customer queries */
    public static final String CREATE_CUSTOMER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS customers" +
            " (id TEXT PRIMARY KEY, name TEXT, phone_number TEXT, address TEXT, "
            + "created_on TEXT, updated_on TEXT);";
    public static final String CUSTOMER_FIND_ALL = "SELECT * FROM customers;";
    public static final String CUSTOMER_FIND_BY_ID = "SELECT * FROM customers WHERE id ='PARAM01';";
    public static final String CUSTOMER_FIND_BY_PHONE_NUMBER = "SELECT * FROM customers WHERE phone_number ='PARAM01';";

    /* order queries */
    public static final String ORDER_CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS orders" +
            " (id TEXT PRIMARY KEY, customerId TEXT, order_date TEXT);";
    public static final String ORDER_CREATE_ORDER_LINES_TABLE = "CREATE TABLE IF NOT EXISTS order_lines" +
            " (orderId TEXT, id TEXT, name TEXT, quantity TEXT, price TEXT);";
    public static final String ORDER_CREATE_ORDER_PAYMENT_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS order_payment_details" +
            " (orderId TEXT, totalBillAmount INTEGER, balance INTEGER, advance INTEGER,discount INTEGER, paidAmount INTEGER, status TEXT);";
    public static final String ORDER_CREATE_ORDER_PAYMENTS_TABLE = "CREATE TABLE IF NOT EXISTS payments" +
            " (orderId TEXT, customerId TEXT, id TEXT, date TEXT, amount TEXT, type TEXT);";
    public static final String ORDER_FIND_BY_ID = "SELECT * FROM orders where id='PARAM01';";
    public static final String ORDER_LINES_BY_ORDER_ID = "SELECT * FROM order_lines WHERE orderId='PARAM01'";
    public static final String ORDER_FIND_PAYMENT_DETAILS_BY_ORDER_ID = "SELECT * FROM order_payment_details WHERE orderId='PARAM01'";
    public static final String ORDER_FIND_PAYMENTS_BY_ORDER_ID = "SELECT * FROM payments WHERE orderId='PARAM01'";
    public static final String ORDER_FIND_ALL = "SELECT * FROM orders ORDER BY order_date desc";
}

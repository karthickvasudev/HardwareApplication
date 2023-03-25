package com.application.hardwarapplication.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.OrderDatabase;
import com.application.hardwarapplication.modals.Order;
import com.application.hardwarapplication.modals.OrderLines;
import com.application.hardwarapplication.utils.PAYMENT_STATUS;

import java.util.List;

public class ViewSaleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView orderId;
    private TextView paymentStatus;
    private TextView name;
    private TextView phoneNumber;
    private TextView itemCount;
    private TextView billAmount;
    private TextView orderDate;
    private TextView paidAmount;
    private TextView balance;
    private TextView discount;
    private OrderDatabase orderDatabase;
    private Order order;
    private Button addPaymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sale);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderDatabase = new OrderDatabase(this);
        order = orderDatabase.findById(getIntent().getStringExtra("orderId"));
        initElements();
        initMethods();
    }

    private void initElements() {
        recyclerView = findViewById(R.id.vs_recycler_view);
        orderId = findViewById(R.id.vs_order_id);
        paymentStatus = findViewById(R.id.vs_payment_status);
        name = findViewById(R.id.vs_customer_name);
        phoneNumber = findViewById(R.id.vs_phone_number);
        itemCount = findViewById(R.id.vs_item_count);
        billAmount = findViewById(R.id.vs_bill_amount);
        orderDate = findViewById(R.id.vs_order_date);
        paidAmount = findViewById(R.id.vs_paid_amount);
        balance = findViewById(R.id.vs_balance);
        discount = findViewById(R.id.vs_discount);
        addPaymentBtn = findViewById(R.id.vs_add_payment);
    }

    private void initMethods() {
        initValues();
        initOrderLinesRecyclerView();
        addPaymentButtonInit();
    }

    private void addPaymentButtonInit() {
        if (order.getPaymentDetails().getStatus().equals(PAYMENT_STATUS.NOT_PAID) ||
                order.getPaymentDetails().getStatus().equals(PAYMENT_STATUS.PARTIALLY_PAID)) {
            addPaymentBtn.setVisibility(View.VISIBLE);
            addPaymentBtn.setOnClickListener(view -> {
                Intent intent=new Intent(this,CustomerPaymentDetailsActivity.class);
                intent.putExtra("orderId",order.getOrderId());
                intent.putExtra("addPayment",true);
                startActivity(intent);
            });
        }
    }

    private void initValues() {
        orderId.setText(order.getOrderId());
        paymentStatus.setText(order.getPaymentDetails().getStatus().name().replace("_", " "));
        name.setText(order.getCustomer().getName());
        phoneNumber.setText(order.getCustomer().getPhoneNumber());
        itemCount.setText(String.valueOf(order.getOrderLines().size()));
        billAmount.setText("₹ " + order.getPaymentDetails().getTotalBillAmount());
        orderDate.setText(order.getOrderDate());
        paidAmount.setText("₹ " + order.getPaymentDetails().getPaidAmount());
        balance.setText("₹ " + order.getPaymentDetails().getBalance());
        discount.setText("₹ " + order.getPaymentDetails().getDiscount());
    }

    private void initOrderLinesRecyclerView() {
        List<OrderLines> orderLines = order.getOrderLines();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        OrderLinesAdaptor orderLinesAdaptor = new OrderLinesAdaptor(orderLines);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderLinesAdaptor);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
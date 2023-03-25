package com.application.hardwarapplication.sales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.OrderDatabase;
import com.application.hardwarapplication.modals.Order;

import java.util.ArrayList;
import java.util.List;

public class SalesHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderDatabase orderDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderDatabase = new OrderDatabase(this);
        initElements();
        initMethods();
    }

    private void initElements() {
        recyclerView = findViewById(R.id.sales_history_list_recycler);
    }

    private void initMethods() {
        showOrdersRecyclerView();
    }

    private void showOrdersRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        OrderListViewAdaptor adaptor = new OrderListViewAdaptor(orderDatabase.findAll());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptor);
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
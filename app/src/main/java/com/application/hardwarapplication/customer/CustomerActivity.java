package com.application.hardwarapplication.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.CustomerDatabase;

import java.util.Arrays;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    MenuItem createMenuItem;
    private RecyclerView recyclerView;
    private CustomerDatabase customerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        recyclerView = findViewById(R.id.customer_list_recycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customerDatabase = new CustomerDatabase(this);
        initListData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        createMenuItem = menu.findItem(R.id.create_menu_item);
        menuClickListener();
        return super.onCreateOptionsMenu(menu);
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

    private void menuClickListener() {
        createMenuItem.setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, CreateCustomerActivity.class);
            startActivity(intent);
            return true;
        });
    }


    private void initListData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        CustomerListAdapter adapter = new CustomerListAdapter(customerDatabase.findAll());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

}
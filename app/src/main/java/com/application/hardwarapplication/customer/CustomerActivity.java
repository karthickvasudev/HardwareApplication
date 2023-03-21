package com.application.hardwarapplication.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.application.hardwarapplication.R;

import java.util.Arrays;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    MenuItem createMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initListData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        createMenuItem = menu.findItem(R.id.create_menu_item);
        menuClickListener();
        return super.onCreateOptionsMenu(menu);
    }

    private void menuClickListener() {
        createMenuItem.setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, CreateCustomerActivity.class);
            startActivity(intent);
            return true;
        });
    }


    private void initListData() {
        List<String> customerData = Arrays.asList("karthick|999999", "pradee|88888");

    }

}
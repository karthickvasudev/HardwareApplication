package com.application.hardwarapplication.customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.CustomerDatabase;
import com.application.hardwarapplication.modals.Customer;

import java.util.Optional;

public class ViewCustomerActivity extends AppCompatActivity {

    private CustomerDatabase customerDatabase;
    private Optional<Customer> optionalCustomer;
    private TextView id;
    private TextView name;
    private TextView address;
    private TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
        initMethods();
    }

    private void initComponents() {
        id = findViewById(R.id.vc_id);
        name = findViewById(R.id.vc_name);
        address = findViewById(R.id.vc_address);
        phoneNumber = findViewById(R.id.vc_phone_number);
    }

    public void initMethods(){
        getCustomerDetails();
        loadCustomerCustomerDetails();
    }
    private void loadCustomerCustomerDetails() {
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            id.setText(customer.getId());
            name.setText(customer.getName());
            phoneNumber.setText(customer.getPhoneNumber());
            address.setText(customer.getAddress());
        }else
            Toast.makeText(this, "Error in customer loading", Toast.LENGTH_SHORT).show();
    }


    private void getCustomerDetails() {
        String id = getIntent().getStringExtra("id");
        customerDatabase = new CustomerDatabase(this);
        optionalCustomer = customerDatabase.customerFindById(id);
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
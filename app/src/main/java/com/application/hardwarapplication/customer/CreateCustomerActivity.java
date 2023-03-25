package com.application.hardwarapplication.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.CustomerDatabase;
import com.application.hardwarapplication.modals.Customer;

import java.util.Objects;
import java.util.Optional;

public class CreateCustomerActivity extends AppCompatActivity {
    EditText name, phoneNumber, address;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.cc_name);
        phoneNumber = findViewById(R.id.cc_phone_number);
        address = findViewById(R.id.cc_address);
        createBtn = findViewById(R.id.cc_create_button);
        buttonActionListener();
    }

    private void buttonActionListener() {
        createBtn.setOnClickListener(view -> {
            CustomerDatabase database = new CustomerDatabase(view.getContext());
            Optional<Customer> optionalCustomer = database.createCustomer(name.getText().toString(),
                    phoneNumber.getText().toString(),
                    address.getText().toString());
            if (optionalCustomer.isPresent()) {
                Toast.makeText(this, "Customer created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ViewCustomerActivity.class);
                intent.putExtra("id", optionalCustomer.get().getId());
                view.getContext().startActivity(intent);
            } else
                Toast.makeText(this, "Error in customer creation", Toast.LENGTH_SHORT).show();
        });
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
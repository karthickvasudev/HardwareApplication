package com.application.hardwarapplication.sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.ProductDatabase;
import com.application.hardwarapplication.modals.Product;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewSalesActivity extends AppCompatActivity {

    private LinearLayout productStack;
    private ProductDatabase productDatabase;
    private TextView allTotalQuantity;
    private TextView allTotalPrice;
    private Button payment_btn;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sales);
        productDatabase = new ProductDatabase(this);
        initElements();
        initMethods();
    }

    private void initElements() {
        productStack = findViewById(R.id.product_stack);
        allTotalQuantity = findViewById(R.id.sale_totalQuantity);
        allTotalPrice = findViewById(R.id.sale_totalPrice);
        payment_btn = findViewById(R.id.sale_payment_btn);
    }

    private void initMethods() {
        loadAllProducts();
        navigateToPaymentDetails();
    }

    private void navigateToPaymentDetails() {
        payment_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CustomerPaymentDetailsActivity.class);
            intent.putExtra("orderDetails", (ArrayList<Product>) productList.stream()
                    .filter(p -> p.getQuantity() != 0 && p.getPrice() != 0)
                    .collect(Collectors.toList()));
            view.getContext().startActivity(intent);
        });
    }

    private void loadAllProducts() {
        productList = (ArrayList<Product>) productDatabase.findAllProducts();
        for (int i = 0; i < productList.size(); i++) {
            final int index = i;
            Product product = productList.get(i);
            final Double[] quantity = {0.0};
            final Double[] price = {0.0};
            final Double[] total = {0.0};

            View view = getLayoutInflater().inflate(R.layout.productlist_viewholder, null);

            TextView name = view.findViewById(R.id.plvh_product_name);
            name.setText(product.getName());

            EditText qty = view.findViewById(R.id.plvh_qty);
            EditText pricePer = view.findViewById(R.id.plvh_price_per);
            TextView totalPrice = view.findViewById(R.id.plvh_total_price);
            qty.addTextChangedListener(new TextWatcher() {
                private void sameCalculation() {
                    total[0] = price[0] * quantity[0];
                    totalPrice.setText("₹ " + total[0]);
                    productList.get(index).setQuantity(quantity[0]);
                    productList.get(index).setPrice(total[0]);
                    double sumQ = productList.stream().map(Product::getQuantity)
                            .mapToDouble(Double::doubleValue).sum();
                    allTotalQuantity.setText(sumQ + "");
                    double sumP = productList.stream().map(Product::getPrice)
                            .mapToDouble(Double::doubleValue).sum();
                    allTotalPrice.setText(sumP + "");

                    long countQ = productList.stream().filter(p -> p.getQuantity() != 0).count();
                    long countP = productList.stream().filter(p -> p.getPrice() != 0).count();
                    if ((countQ == countP) && sumP > 0 && sumQ > 0) {
                        payment_btn.setEnabled(true);
                    } else {
                        payment_btn.setEnabled(false);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (Objects.isNull(charSequence) || charSequence.toString().length() == 0) {
                        quantity[0] = 0.0;
                        sameCalculation();
                    } else {
                        quantity[0] = Double.parseDouble(charSequence.toString());
                        sameCalculation();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            pricePer.addTextChangedListener(new TextWatcher() {
                private void sameCalculation() {
                    total[0] = price[0] * quantity[0];
                    totalPrice.setText("₹ " + total[0]);
                    productList.get(index).setQuantity(quantity[0]);
                    productList.get(index).setPrice(total[0]);
                    double sumQ = productList.stream().map(Product::getQuantity)
                            .mapToDouble(Double::doubleValue).sum();
                    allTotalQuantity.setText(sumQ + "");
                    double sumP = productList.stream().map(p -> p.getPrice())
                            .mapToDouble(Double::doubleValue).sum();
                    allTotalPrice.setText(sumP + "");
                    long countQ = productList.stream().filter(p -> p.getQuantity() != 0).count();
                    long countP = productList.stream().filter(p -> p.getPrice() != 0).count();
                    if ((countQ == countP) && sumP > 0 && sumQ > 0) {
                        payment_btn.setEnabled(true);
                    } else {
                        payment_btn.setEnabled(false);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (Objects.isNull(charSequence) || charSequence.toString().length() == 0) {
                        price[0] = 0.0;
                        sameCalculation();
                    } else {
                        price[0] = Double.parseDouble(charSequence.toString());
                        sameCalculation();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            productStack.addView(view);
        }

    }
}
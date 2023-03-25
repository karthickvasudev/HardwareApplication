package com.application.hardwarapplication.sales;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.database.CustomerDatabase;
import com.application.hardwarapplication.database.OrderDatabase;
import com.application.hardwarapplication.modals.Customer;
import com.application.hardwarapplication.modals.Order;
import com.application.hardwarapplication.modals.OrderLines;
import com.application.hardwarapplication.modals.Payment;
import com.application.hardwarapplication.modals.Product;
import com.application.hardwarapplication.utils.PAYMENT_STATUS;
import com.application.hardwarapplication.utils.PAYMENT_TYPE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerPaymentDetailsActivity extends AppCompatActivity {
    Order.OrderPaymentDetails orderPaymentDetails = Order.OrderPaymentDetails.builder().build();
    private ArrayList<OrderLines> orderLines;
    private TextView totalHeader;
    private RadioGroup payingRadioGroup;
    private RadioButton payingNowRadio;
    private RadioButton payingLaterRadio;
    private LinearLayout paymentDetailsLayout;
    private Button completeOrderBtn;
    private EditText balance;
    private EditText paymentAmount;
    private EditText advance;
    private EditText discount;
    private CheckBox partialPay;
    private AutoCompleteTextView phoneNumber;
    private CustomerDatabase customerDatabase;
    private EditText name;
    private RadioGroup paymentTypeRadioGroup;
    private RadioButton paymentTypeCash;
    private RadioButton paymentTypeUPI;
    private OrderDatabase orderDatabase;
    private Boolean addPayment = false;
    private Order order;
    private LinearLayout alreadyPaidLayout;
    private TextView alreadyPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customerDatabase = new CustomerDatabase(this);
        orderDatabase = new OrderDatabase(this);
        addPayment = getIntent().getBooleanExtra("addPayment", false);
        initElements();
        initialValueSet();
        initMethods();
    }

    private void initElements() {
        totalHeader = findViewById(R.id.payment_totalBillHeader);
        payingRadioGroup = findViewById(R.id.payment_paying_radio_group);
        payingNowRadio = findViewById(R.id.payment_paying_now_radio);
        payingLaterRadio = findViewById(R.id.payment_paying_later_raido);
        paymentDetailsLayout = findViewById(R.id.payment_details_layout);
        completeOrderBtn = findViewById(R.id.payment_complete_order_btn);
        balance = findViewById(R.id.payment_balance);
        paymentAmount = findViewById(R.id.payment_payment_amount);
        advance = findViewById(R.id.payment_advance);
        partialPay = findViewById(R.id.payment_partial_pay);
        discount = findViewById(R.id.payment_discount);
        phoneNumber = findViewById(R.id.payment_phone_number);
        name = findViewById(R.id.payment_name);
        paymentTypeRadioGroup = findViewById(R.id.payment_type);
        paymentTypeCash = findViewById(R.id.payment_type_cash);
        paymentTypeUPI = findViewById(R.id.payment_type_upi);
        alreadyPaidLayout = findViewById(R.id.payment_already_paid_layout);
        alreadyPaid = findViewById(R.id.payment_already_paid);
    }

    private void initialValueSet() {
        if (addPayment) {
            order = orderDatabase.findById(getIntent().getStringExtra("orderId"));
            orderLines = (ArrayList<OrderLines>) order.getOrderLines();
            orderPaymentDetails = order.getPaymentDetails();
            balance.setText(String.valueOf(orderPaymentDetails.getBalance()));
            name.setText(order.getCustomer().getName());
            name.setEnabled(false);
            phoneNumber.setText(order.getCustomer().getPhoneNumber());
            phoneNumber.setEnabled(false);
            totalHeader.setText("₹ " + orderPaymentDetails.getTotalBillAmount());
            payingNowRadio.setChecked(true);
            payingLaterRadio.setEnabled(false);
            alreadyPaidLayout.setVisibility(View.VISIBLE);
            alreadyPaid.setText(String.valueOf(order.getPaymentDetails().getPaidAmount()));
        } else {
            orderLines = (ArrayList<OrderLines>) ((ArrayList<Product>) getIntent().getSerializableExtra("orderDetails"))
                    .stream().map(product ->
                            OrderLines.builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .quantity(product.getQuantity())
                                    .price(product.getPrice())
                                    .build()
                    ).collect(Collectors.toList());
            Double totalBill = orderLines.stream().map(OrderLines::getPrice).mapToDouble(Double::doubleValue).sum();
            totalHeader.setText("₹ " + totalBill);
            orderPaymentDetails.setTotalBillAmount(totalBill);
            orderPaymentDetails.setBalance(totalBill);
            orderPaymentDetails.setAdvance(0.0);
            orderPaymentDetails.setPaidAmount(0.0);
            orderPaymentDetails.setDiscount(0.0);
            orderPaymentDetails.setStatus(PAYMENT_STATUS.NOT_PAID);

            balance.setText(String.valueOf(orderPaymentDetails.getBalance()));
        }
    }


    private void initMethods() {
        initRadioButtonListener();
        phoneNumberNameChangeListener();
        paymentAmountChangeListener();
        partialPayCheckListener();
        discountListener();
        customerSelectPhoneSearch();
        completeOrderAction();
        paymentTypeRadioListener();
    }

    private void paymentTypeRadioListener() {
        paymentTypeRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            setEnableCreateButton();
        });
    }

    private void phoneNumberNameChangeListener() {
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setEnableCreateButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setEnableCreateButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void customerSelectPhoneSearch() {
        List<String> customers = customerDatabase.findAll().stream().map(customer -> customer.getPhoneNumber() + "-" + customer.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, customers);
        phoneNumber.setAdapter(adapter);
        phoneNumber.setOnItemClickListener((adapterView, view, i, l) -> {
            String selected = (String) adapterView.getItemAtPosition(i);
            String[] split = selected.split("-");
            phoneNumber.setText(split[0]);
            name.setText(split[1]);
        });
    }

    private void discountListener() {
        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.length() != 0) {
                        double _discount = Double.parseDouble(charSequence.toString());
                        calculation(orderPaymentDetails.getPaidAmount(), _discount);
                    } else {
                        calculation(orderPaymentDetails.getPaidAmount(), 0.0);
                    }
                    setEnableCreateButton();
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void paymentAmountChangeListener() {
        paymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.length() != 0) {
                        double _paymentAmount = Double.parseDouble(charSequence.toString());
                        calculation(_paymentAmount, orderPaymentDetails.getDiscount());
                    } else {
                        calculation(0.0, orderPaymentDetails.getDiscount());
                    }
                    setEnableCreateButton();
                } catch (Exception e) {
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void partialPayCheckListener() {
        partialPay.setOnCheckedChangeListener((compoundButton, b) -> {
            setEnableCreateButton();
        });
    }

    private void setEnableCreateButton() {
        if (
                (name.getText().length() > 2 && phoneNumber.getText().length() == 10) &&
                        (orderPaymentDetails.getBalance() == 0.0) ||
                        (orderPaymentDetails.getBalance() != 0.0 && partialPay.isChecked() &&
                                orderPaymentDetails.getPaidAmount() != 0) &&
                                (paymentTypeCash.isChecked() || paymentTypeUPI.isChecked())
        ) {
            completeOrderBtn.setEnabled(true);
        } else {
            completeOrderBtn.setEnabled(false);
        }
    }

    private void initRadioButtonListener() {
        payingRadioGroup.setOnCheckedChangeListener((compoundButton, b) -> {
            if (payingNowRadio.isChecked()) {
                paymentDetailsLayout.setVisibility(View.VISIBLE);
                completeOrderBtn.setEnabled(false);
            } else {
                paymentDetailsLayout.setVisibility(View.GONE);
                completeOrderBtn.setEnabled(true);
            }
        });
    }

    public void setPartialPayCheckBoxEnable(boolean flag) {
        partialPay.setEnabled(flag);
    }

    private void calculation(double paymentAmount, double _discount) {
        if (_discount > Math.abs(orderPaymentDetails.getTotalBillAmount() - paymentAmount)) {
            _discount = orderPaymentDetails.getTotalBillAmount() - paymentAmount;
            orderPaymentDetails.setDiscount(_discount);
            discount.setText(String.valueOf(_discount));
        }

        double _balance = orderPaymentDetails.getTotalBillAmount() - paymentAmount - _discount;
        if (_balance > 0) {
            balance.setText(String.valueOf(_balance));
            orderPaymentDetails.setBalance(_balance);
            orderPaymentDetails.setPaidAmount(paymentAmount);
            if (paymentAmount != 0 && _balance != orderPaymentDetails.getTotalBillAmount())
                setPartialPayCheckBoxEnable(true);
            else
                setPartialPayCheckBoxEnable(false);
        } else {
            balance.setText("0");
            orderPaymentDetails.setBalance(0.0);
            orderPaymentDetails.setPaidAmount(paymentAmount);
            setPartialPayCheckBoxEnable(false);
        }

        if ((paymentAmount + _discount) > orderPaymentDetails.getTotalBillAmount()) {
            double _advance = paymentAmount + _discount - orderPaymentDetails.getTotalBillAmount();
            orderPaymentDetails.setAdvance(_advance);
            advance.setText(String.valueOf(_advance));
        } else {
            orderPaymentDetails.setAdvance(0.0);
            advance.setText("0");
        }

    }

    private void completeOrderAction() {
        completeOrderBtn.setOnClickListener(view -> {
            Customer customer = buildCustomerData();

            Order order = Order.builder()
                    .customerId(customer.getId())
                    .customer(customer)
                    .orderLines(orderLines)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                order.setOrderDate(LocalDateTime.now().toString());
            else
                order.setOrderDate("date not supported");

            if (payingNowRadio.isChecked()) {
                if (partialPay.isChecked()) {
                    orderPaymentDetails.setStatus(PAYMENT_STATUS.PARTIALLY_PAID);
                } else {
                    orderPaymentDetails.setStatus(PAYMENT_STATUS.PAID);
                }
                order.setPaymentDetails(orderPaymentDetails);

                Payment payment = Payment.builder()
                        .type(paymentTypeCash.isChecked() ? PAYMENT_TYPE.CASH : PAYMENT_TYPE.UPI)
                        .amount(String.valueOf(orderPaymentDetails.getPaidAmount()))
                        .build();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    payment.setDate(LocalDateTime.now().toString());
                else
                    payment.setDate("date not supported");
                order.setPayments(Collections.singletonList(payment));
            } else {
                orderPaymentDetails.setStatus(PAYMENT_STATUS.NOT_PAID);
                order.setPayments(new ArrayList<>());
                order.setPaymentDetails(orderPaymentDetails);
            }
            orderDatabase.createOrder(order);
        });
    }

    private Customer buildCustomerData() {
        Optional<Customer> optionalCustomer =
                customerDatabase.customerFindByPhoneNumber(phoneNumber.getText().toString());
        if (optionalCustomer.isPresent()) {
            System.out.println("using existing customer");
            return optionalCustomer.get();
        } else {
            System.out.println("creating customer");
            return customerDatabase.createCustomer(name.getText().toString(),
                    phoneNumber.getText().toString(), "").get();
        }
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
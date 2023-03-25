package com.application.hardwarapplication.dashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.customer.CustomerActivity;
import com.application.hardwarapplication.database.DatabaseHelper;
import com.application.hardwarapplication.outstanding.OutStandingActivity;
import com.application.hardwarapplication.report.ReportActivity;
import com.application.hardwarapplication.sales.NewSalesActivity;
import com.application.hardwarapplication.sales.SalesHistoryActivity;
import com.application.hardwarapplication.smstrack.SmsTrackActivity;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout customer;
    LinearLayout newSales;
    LinearLayout salesHistory;
    LinearLayout outStanding;
    LinearLayout report;
    LinearLayout smsTrack;

    Map<LinearLayout, Class<?>> layoutListMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customer = findViewById(R.id.customer_icon);
        newSales = findViewById(R.id.new_sales_icon);
        salesHistory = findViewById(R.id.sales_history_icon);
        outStanding = findViewById(R.id.out_standing_icon);
        report = findViewById(R.id.report_icon);
        smsTrack = findViewById(R.id.sms_track_icon);
        prepareObjectsForClickActions();
        clickActions();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.beforeHook();
    }

    private void prepareObjectsForClickActions() {
        layoutListMap.put(customer, CustomerActivity.class);
        layoutListMap.put(newSales, NewSalesActivity.class);
        layoutListMap.put(salesHistory, SalesHistoryActivity.class);
        layoutListMap.put(outStanding, OutStandingActivity.class);
        layoutListMap.put(report, ReportActivity.class);
        layoutListMap.put(smsTrack, SmsTrackActivity.class);
    }

    private void clickActions() {
        layoutListMap.forEach((layout, cls) -> {
            layout.setOnClickListener(view -> {
                Intent intent = new Intent(this, cls);
                startActivity(intent);
            });
        });
    }
}
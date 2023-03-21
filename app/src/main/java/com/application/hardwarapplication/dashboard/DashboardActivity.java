package com.application.hardwarapplication.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.customer.CustomerActivity;
import com.application.hardwarapplication.outstanding.OutStandingActivity;
import com.application.hardwarapplication.report.ReportActivity;
import com.application.hardwarapplication.sales.SalesActivity;
import com.application.hardwarapplication.smstrack.SmsTrackActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout customer;
    LinearLayout sales;
    LinearLayout outStanding;
    LinearLayout report;
    LinearLayout smsTrack;

    Map<LinearLayout, Class<?>> layoutListMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customer = findViewById(R.id.customer_icon);
        sales = findViewById(R.id.sales_icon);
        outStanding = findViewById(R.id.out_standing_icon);
        report = findViewById(R.id.report_icon);
        smsTrack = findViewById(R.id.sms_track_icon);
        prepareObjectsForClickActions();
        clickActions();
    }

    private void prepareObjectsForClickActions() {
        layoutListMap.put(customer, CustomerActivity.class);
        layoutListMap.put(sales, SalesActivity.class);
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
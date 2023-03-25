package com.application.hardwarapplication.sales;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.modals.Order;

import java.util.List;

public class OrderListViewAdaptor extends RecyclerView.Adapter<OrderListViewAdaptor.ViewHolder> {

    private final List<Order> orderList;

    public OrderListViewAdaptor(List<Order> orderList) {
        this.orderList = orderList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView orderId;
        private final TextView paymentStatus;
        private final TextView name;
        private final TextView phoneNumber;
        private final TextView itemCount;
        private final TextView billAmount;
        private final TextView orderDate;
        private final TextView paidAmount;
        private final TextView balance;
        private final TextView discount;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.shvh_layout);
            orderId = itemView.findViewById(R.id.shvh_order_id);
            paymentStatus = itemView.findViewById(R.id.shvh_payment_status);
            name = itemView.findViewById(R.id.shvh_customer_name);
            phoneNumber = itemView.findViewById(R.id.shvh_phone_number);
            itemCount = itemView.findViewById(R.id.shvh_item_count);
            billAmount = itemView.findViewById(R.id.shvh_bill_amount);
            orderDate = itemView.findViewById(R.id.shvh_order_date);
            paidAmount = itemView.findViewById(R.id.shvh_paid_amount);
            balance = itemView.findViewById(R.id.shvh_balance);
            discount = itemView.findViewById(R.id.shvh_discount);

        }
    }

    @NonNull
    @Override
    public OrderListViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sales_history_viewholder, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderListViewAdaptor.ViewHolder viewHolder, int i) {
        Order order = orderList.get(i);
        viewHolder.orderId.setText(order.getOrderId());
        viewHolder.paymentStatus.setText(order.getPaymentDetails().getStatus().name().replace("_", " "));
        viewHolder.name.setText(order.getCustomer().getName());
        viewHolder.phoneNumber.setText(order.getCustomer().getPhoneNumber());
        viewHolder.itemCount.setText(String.valueOf(order.getOrderLines().size()));
        viewHolder.billAmount.setText("₹ " + order.getPaymentDetails().getTotalBillAmount());
        viewHolder.orderDate.setText(order.getOrderDate());
        viewHolder.paidAmount.setText("₹ " + order.getPaymentDetails().getPaidAmount());
        viewHolder.balance.setText("₹ " + order.getPaymentDetails().getBalance());
        viewHolder.discount.setText("₹ " + order.getPaymentDetails().getDiscount());
        viewHolder.linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ViewSaleActivity.class);
            intent.putExtra("orderId", order.getOrderId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

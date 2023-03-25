package com.application.hardwarapplication.sales;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.modals.OrderLines;

import java.util.List;

public class OrderLinesAdaptor extends RecyclerView.Adapter<OrderLinesAdaptor.ViewHolder> {

    private final List<OrderLines> orderLines;

    public OrderLinesAdaptor(List<OrderLines> orderLines) {
        this.orderLines = orderLines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView amount;
        private final TextView itemCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.olvh_product_name);
            amount = itemView.findViewById(R.id.olvh_amount);
            itemCount = itemView.findViewById(R.id.olvh_item_count);
        }
    }

    @NonNull
    @Override
    public OrderLinesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_lines_viewholder, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderLinesAdaptor.ViewHolder viewHolder, int i) {
        OrderLines orderLine = orderLines.get(i);
        viewHolder.name.setText(orderLine.getName());
        viewHolder.amount.setText("₹ " + orderLine.getPrice());
        viewHolder.itemCount.setText("₹ " + orderLine.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderLines.size();
    }
}

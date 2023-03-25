package com.application.hardwarapplication.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.hardwarapplication.R;
import com.application.hardwarapplication.modals.Customer;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private List<Customer> data;

    public CustomerListAdapter(List<Customer> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView name;
        public TextView phoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.cl_adaptor_layout);
            name = itemView.findViewById(R.id.cl_name);
            phoneNumber = itemView.findViewById(R.id.cl_phone_number);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_list_viewholder, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Customer customer = data.get(i);
        viewHolder.name.setText(customer.getName());
        viewHolder.phoneNumber.setText(customer.getPhoneNumber());
        viewHolder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ViewCustomerActivity.class);
            intent.putExtra("id", customer.getId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

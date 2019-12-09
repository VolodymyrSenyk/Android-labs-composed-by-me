package com.senyk.volodymyr.lab10.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab10.R;
import com.senyk.volodymyr.lab10.models.pojo.CurrencyPojo;

import java.util.List;

public class ExchangeRatesAdapter extends RecyclerView.Adapter {
    private List<CurrencyPojo> items;

    public ExchangeRatesAdapter(List<CurrencyPojo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exchange_rate, parent, false);
        return new ContactViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ContactViewHolder viewHolder = (ContactViewHolder) holder;
        final CurrencyPojo item = this.items.get(position);

        viewHolder.contactNameView.setText(item.getName());
        viewHolder.contactPhoneNumberView.setText(String.valueOf(item.getRate()));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    private static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView contactNameView;
        private TextView contactPhoneNumberView;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.contactNameView = itemView.findViewById(R.id.currency_name);
            this.contactPhoneNumberView = itemView.findViewById(R.id.exchange_rate);
        }
    }
}

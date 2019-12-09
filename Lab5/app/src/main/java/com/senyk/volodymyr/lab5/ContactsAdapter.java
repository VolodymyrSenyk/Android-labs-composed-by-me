package com.senyk.volodymyr.lab5;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter {
    private List<ContactPojo> items;

    public ContactsAdapter(List<ContactPojo> items) {
        Collections.sort(items);
        List<ContactPojo> filteredList = new ArrayList<>();
        for (ContactPojo item : items) {
            if (!item.getName().equals("") && !item.getPhoneNumber().equals("")) {
                filteredList.add(item);
            }
        }
        this.items = filteredList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ContactViewHolder viewHolder = (ContactViewHolder) holder;
        final ContactPojo item = this.items.get(position);

        viewHolder.contactNameView.setText(item.getName());
        viewHolder.contactPhoneNumberView.setText(item.getPhoneNumber());

        final String phoneNumber = "tel:" + item.getPhoneNumber();
        viewHolder.contactPhoneNumberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                viewHolder.itemView.getContext().startActivity(callIntent);
            }
        });
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
            this.contactNameView = itemView.findViewById(R.id.contact_name);
            this.contactPhoneNumberView = itemView.findViewById(R.id.contact_phone_number);
        }
    }
}

package com.senyk.volodymyr.lab7.adapters;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab7.R;
import com.senyk.volodymyr.lab7.models.NotePojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter {
    private OnItemClickedListener clickListener;
    private List<NotePojo> items = new ArrayList<>();

    public NotesListAdapter(OnItemClickedListener navigation) {
        this.clickListener = navigation;
    }

    public void setItems(List<NotePojo> items) {
        Collections.sort(items);
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_note, parent, false);
        return new NoteViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NoteViewHolder viewHolder = (NoteViewHolder) holder;
        final NotePojo item = this.items.get(position);

        viewHolder.noteTextView.setText(item.getText());
        viewHolder.noteDateOfLastEditingView.setText(DateUtils.formatDateTime(
                viewHolder.itemView.getContext(),
                item.getDateOfLastEditing(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(item.getDateOfLastEditing());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    private static class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTextView;
        private TextView noteDateOfLastEditingView;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.noteTextView = itemView.findViewById(R.id.note_text);
            this.noteDateOfLastEditingView = itemView.findViewById(R.id.note_date_of_last_editing);
        }
    }
}

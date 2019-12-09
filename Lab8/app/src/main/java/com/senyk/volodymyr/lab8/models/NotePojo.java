package com.senyk.volodymyr.lab8.models;

import android.os.Build;

import androidx.annotation.NonNull;

public class NotePojo implements Comparable<NotePojo> {
    private String text;
    private long dateOfLastEditing;

    public NotePojo(String text, long dateOfLastEditing) {
        this.text = text;
        this.dateOfLastEditing = dateOfLastEditing;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDateOfLastEditing() {
        return this.dateOfLastEditing;
    }

    public void setDateOfLastEditing(long dateOfLastEditing) {
        this.dateOfLastEditing = dateOfLastEditing;
    }

    @Override
    public int compareTo(@NonNull NotePojo notePojo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Long.compare(notePojo.dateOfLastEditing, this.dateOfLastEditing);
        } else {
            return Long.valueOf(notePojo.dateOfLastEditing).compareTo(this.dateOfLastEditing);
        }
    }
}

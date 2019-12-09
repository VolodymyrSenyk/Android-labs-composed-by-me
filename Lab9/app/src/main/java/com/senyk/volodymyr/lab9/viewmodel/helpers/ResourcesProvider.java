package com.senyk.volodymyr.lab9.viewmodel.helpers;

import android.content.res.Resources;

import com.senyk.volodymyr.lab9.R;

public class ResourcesProvider {
    private final Resources resources;

    public ResourcesProvider(Resources resources) {
        this.resources = resources;
    }

    public String getNoteSuccessfullyChangedMessage() {
        return this.resources.getString(R.string.note_successfully_changed_message);
    }
}

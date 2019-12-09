package com.senyk.volodymyr.lab6.activities;

public interface Navigation {
    void addNoteButtonClicked();

    void onNoteClicked(long noteId);

    void onNoteSaved();
}

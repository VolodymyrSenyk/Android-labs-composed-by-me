package com.senyk.volodymyr.lab8.repository;

import com.senyk.volodymyr.lab8.models.NotePojo;

import java.util.List;

public interface Repository {
    List<NotePojo> loadNotes();

    NotePojo loadNoteData(long dateOfLastEditing);

    boolean addNote(NotePojo note);

    boolean changeNote(long noteId, NotePojo note);
}

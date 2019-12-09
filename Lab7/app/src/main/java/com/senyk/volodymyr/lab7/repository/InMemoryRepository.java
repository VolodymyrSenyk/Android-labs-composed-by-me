package com.senyk.volodymyr.lab7.repository;

import androidx.annotation.Nullable;

import com.senyk.volodymyr.lab7.models.NotePojo;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements Repository {
    private static InMemoryRepository repository;

    private InMemoryRepository() {
    }

    public static InMemoryRepository getInstance() {
        if (repository == null) repository = new InMemoryRepository();
        return repository;
    }

    private List<NotePojo> notes = new ArrayList<>();

    @Override
    public List<NotePojo> loadNotes() {
        return this.notes;
    }

    @Nullable
    @Override
    public NotePojo loadNoteData(long dateOfLastEditing) {
        for (NotePojo item : this.notes) {
            if (item.getDateOfLastEditing() == dateOfLastEditing) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean addNote(NotePojo note) {
        note.setText(note.getText().trim());
        if (note.getText().equals("")) {
            return false;
        }
        return this.notes.add(note);
    }

    @Override
    public boolean changeNote(long noteId, NotePojo note) {
        for (NotePojo item : this.notes) {
            if (item.getDateOfLastEditing() == noteId) {
                this.notes.remove(item);
                return addNote(note);
            }
        }
        return false;
    }
}

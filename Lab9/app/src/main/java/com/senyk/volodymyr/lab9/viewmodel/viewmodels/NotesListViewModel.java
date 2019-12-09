package com.senyk.volodymyr.lab9.viewmodel.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senyk.volodymyr.lab9.models.NotePojo;
import com.senyk.volodymyr.lab9.repository.Repository;

import java.util.List;

public class NotesListViewModel extends BaseViewModel {
    private final Repository repository;

    private MutableLiveData<List<NotePojo>> notesList = new MutableLiveData<>();

    public NotesListViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<NotePojo>> getNotesList() {
        return this.notesList;
    }

    public void loadNotesList() {
        this.notesList.setValue(this.repository.loadNotes());
    }
}

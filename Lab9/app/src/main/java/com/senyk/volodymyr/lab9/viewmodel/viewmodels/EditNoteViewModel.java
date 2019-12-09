package com.senyk.volodymyr.lab9.viewmodel.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senyk.volodymyr.lab9.models.NotePojo;
import com.senyk.volodymyr.lab9.repository.Repository;
import com.senyk.volodymyr.lab9.viewmodel.helpers.ResourcesProvider;

public class EditNoteViewModel extends BaseViewModel {
    private final Repository repository;
    private final ResourcesProvider resourcesProvider;
    private boolean isEditMode;
    private long oldNoteId;

    public void setEditMode(long oldNoteId) {
        this.isEditMode = true;
        this.oldNoteId = oldNoteId;
        loadNoteData();
    }

    private MutableLiveData<NotePojo> noteData = new MutableLiveData<>();

    public EditNoteViewModel(Repository repository, ResourcesProvider resourcesProvider) {
        this.repository = repository;
        this.resourcesProvider = resourcesProvider;
    }

    public LiveData<NotePojo> getNoteData() {
        return this.noteData;
    }

    private void loadNoteData() {
        this.noteData.setValue(this.repository.loadNoteData(this.oldNoteId));
    }

    public void savingFinished(NotePojo note) {
        boolean isSuccessful;
        if (this.isEditMode) {
            isSuccessful = this.repository.changeNote(this.oldNoteId, note);
        } else {
            isSuccessful = this.repository.addNote(note);
        }
        if (isSuccessful) {
            this.message.setValue(this.resourcesProvider.getNoteSuccessfullyChangedMessage());
        }
    }
}

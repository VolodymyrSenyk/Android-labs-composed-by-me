package com.senyk.volodymyr.lab9.viewmodel.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.lab9.repository.SQLiteRepository;
import com.senyk.volodymyr.lab9.viewmodel.helpers.ResourcesProvider;
import com.senyk.volodymyr.lab9.viewmodel.viewmodels.EditNoteViewModel;
import com.senyk.volodymyr.lab9.viewmodel.viewmodels.NotesListViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == EditNoteViewModel.class) {
            return (T) new EditNoteViewModel(
                    SQLiteRepository.getInstance(this.context),
                    new ResourcesProvider(this.context.getResources()));
        }
        if (modelClass == NotesListViewModel.class) {
            return (T) new NotesListViewModel(SQLiteRepository.getInstance(this.context));
        }
        return null;
    }
}

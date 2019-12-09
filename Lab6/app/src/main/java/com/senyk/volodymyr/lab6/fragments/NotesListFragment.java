package com.senyk.volodymyr.lab6.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab6.R;
import com.senyk.volodymyr.lab6.activities.Navigation;
import com.senyk.volodymyr.lab6.adapters.NotesListAdapter;
import com.senyk.volodymyr.lab6.repository.InMemoryRepository;
import com.senyk.volodymyr.lab6.repository.Repository;

public class NotesListFragment extends Fragment {
    private static Navigation navigation;
    private Repository repository;
    private NotesListAdapter adapter;

    public static NotesListFragment newInstance(Navigation navigation) {
        NotesListFragment.navigation = navigation;
        return new NotesListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new NotesListAdapter(navigation);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        notesList.setLayoutManager(layoutManager);
        notesList.setAdapter(this.adapter);

        view.findViewById(R.id.add_new_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.addNoteButtonClicked();
            }
        });

        this.repository = InMemoryRepository.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.setItems(this.repository.loadNotes());
    }
}

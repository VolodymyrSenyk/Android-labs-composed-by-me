package com.senyk.volodymyr.lab8.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab8.R;
import com.senyk.volodymyr.lab8.adapters.NotesListAdapter;
import com.senyk.volodymyr.lab8.adapters.OnItemClickedListener;
import com.senyk.volodymyr.lab8.repository.InMemoryRepository;
import com.senyk.volodymyr.lab8.repository.Repository;
import com.senyk.volodymyr.lab8.repository.SQLiteRepository;

public class NotesListFragment extends Fragment implements OnItemClickedListener {
    private Repository repository;
    private NotesListAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new NotesListAdapter(this);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        notesList.setLayoutManager(layoutManager);
        notesList.setAdapter(this.adapter);

        view.findViewById(R.id.add_new_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NotesListFragment.this)
                        .navigate(NotesListFragmentDirections
                                .actionNotesListFragmentToEditNoteFragment(EditNoteFragment.DEFAULT_ID));
            }
        });

        this.repository = SQLiteRepository.getInstance(requireContext());
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

    @Override
    public void onItemClicked(long itemId) {
        NavHostFragment.findNavController(NotesListFragment.this)
                .navigate(NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment(itemId));
    }
}

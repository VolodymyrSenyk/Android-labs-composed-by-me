package com.senyk.volodymyr.lab9.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab9.R;
import com.senyk.volodymyr.lab9.adapters.NotesListAdapter;
import com.senyk.volodymyr.lab9.adapters.OnItemClickedListener;
import com.senyk.volodymyr.lab9.models.NotePojo;
import com.senyk.volodymyr.lab9.viewmodel.factories.ViewModelFactory;
import com.senyk.volodymyr.lab9.viewmodel.viewmodels.NotesListViewModel;

import java.util.List;

public class NotesListFragment extends Fragment implements OnItemClickedListener {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NotesListViewModel viewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(NotesListViewModel.class);

        final NotesListAdapter adapter = new NotesListAdapter(this);
        final RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        notesList.setLayoutManager(layoutManager);
        notesList.setAdapter(adapter);

        view.findViewById(R.id.add_new_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NotesListFragment.this)
                        .navigate(NotesListFragmentDirections
                                .actionNotesListFragmentToEditNoteFragment(EditNoteFragment.DEFAULT_ID));
            }
        });

        viewModel.getNotesList().observe(this.getViewLifecycleOwner(), new Observer<List<NotePojo>>() {
            @Override
            public void onChanged(List<NotePojo> notes) {
                adapter.setItems(notes);
            }
        });

        viewModel.loadNotesList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onItemClicked(long itemId) {
        NavHostFragment.findNavController(NotesListFragment.this)
                .navigate(NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment(itemId));
    }
}

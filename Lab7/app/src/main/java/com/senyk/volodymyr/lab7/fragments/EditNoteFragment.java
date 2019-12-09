package com.senyk.volodymyr.lab7.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.senyk.volodymyr.lab7.R;
import com.senyk.volodymyr.lab7.models.NotePojo;
import com.senyk.volodymyr.lab7.repository.InMemoryRepository;
import com.senyk.volodymyr.lab7.repository.Repository;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {
    public static final int DEFAULT_ID = -1;

    private Repository repository;
    private EditNoteFragmentArgs args;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (this.getArguments() != null) {
            this.args = EditNoteFragmentArgs.fromBundle(this.getArguments());
        }

        this.repository = InMemoryRepository.getInstance();

        final EditText noteTextView = view.findViewById(R.id.note_text_input_view);
        if (args.getNoteId() != DEFAULT_ID) {
            NotePojo note = repository.loadNoteData(args.getNoteId());
            noteTextView.setText(note.getText());
        }

        view.findViewById(R.id.save_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotePojo newNoteData = new NotePojo(
                        noteTextView.getText().toString(),
                        Calendar.getInstance().getTimeInMillis()
                );
                boolean isSuccessful;
                if (args.getNoteId() != DEFAULT_ID) {
                    isSuccessful = repository.changeNote(args.getNoteId(), newNoteData);
                } else {
                    isSuccessful = repository.addNote(newNoteData);
                }
                if (isSuccessful) {
                    Toast.makeText(requireContext(), getString(R.string.note_successfully_changed_message), Toast.LENGTH_SHORT).show();
                }
                NavHostFragment.findNavController(EditNoteFragment.this).popBackStack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_edit_note, container, false);
    }
}

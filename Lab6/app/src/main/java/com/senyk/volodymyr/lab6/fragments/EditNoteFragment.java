package com.senyk.volodymyr.lab6.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.senyk.volodymyr.lab6.R;
import com.senyk.volodymyr.lab6.activities.Navigation;
import com.senyk.volodymyr.lab6.models.NotePojo;
import com.senyk.volodymyr.lab6.repository.InMemoryRepository;
import com.senyk.volodymyr.lab6.repository.Repository;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {
    private static final String NOTE_ID_BUNDLE_KEY = "note id";
    private static final int DEFAULT_ID = -1;

    private static Navigation navigation;
    private Repository repository;

    public static EditNoteFragment newInstance(Navigation navigation) {
        EditNoteFragment.navigation = navigation;
        return new EditNoteFragment();
    }

    public static EditNoteFragment newInstance(long noteId, Navigation navigation) {
        EditNoteFragment.navigation = navigation;
        EditNoteFragment newFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putLong(NOTE_ID_BUNDLE_KEY, noteId);
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isEdit = false;
        long noteId = DEFAULT_ID;
        Bundle args = getArguments();
        if (args != null) {
            noteId = args.getLong(NOTE_ID_BUNDLE_KEY, DEFAULT_ID);
            if (noteId != DEFAULT_ID) {
                isEdit = true;
            }
        }

        this.repository = InMemoryRepository.getInstance();

        final EditText noteTextView = view.findViewById(R.id.note_text_input_view);
        if (isEdit) {
            NotePojo note = repository.loadNoteData(noteId);
            noteTextView.setText(note.getText());
        }

        final boolean isEdited = isEdit;
        final long changedNoteId = noteId;
        view.findViewById(R.id.save_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotePojo newNoteData = new NotePojo(
                        noteTextView.getText().toString(),
                        Calendar.getInstance().getTimeInMillis()
                );
                boolean isSuccessful;
                if (isEdited) {
                    isSuccessful = repository.changeNote(changedNoteId, newNoteData);
                } else {
                    isSuccessful = repository.addNote(newNoteData);
                }
                if (isSuccessful) {
                    Toast.makeText(requireContext(), getString(R.string.note_successfully_changed_message), Toast.LENGTH_SHORT).show();
                }
                navigation.onNoteSaved();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_edit_note, container, false);
    }
}

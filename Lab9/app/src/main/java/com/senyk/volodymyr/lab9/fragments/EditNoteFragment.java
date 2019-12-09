package com.senyk.volodymyr.lab9.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.senyk.volodymyr.lab9.R;
import com.senyk.volodymyr.lab9.models.NotePojo;
import com.senyk.volodymyr.lab9.viewmodel.factories.ViewModelFactory;
import com.senyk.volodymyr.lab9.viewmodel.viewmodels.EditNoteViewModel;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {
    public static final int DEFAULT_ID = -1;

    private EditNoteFragmentArgs args;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditNoteViewModel viewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(EditNoteViewModel.class);

        if (this.getArguments() != null) {
            this.args = EditNoteFragmentArgs.fromBundle(this.getArguments());
        }

        final EditText noteTextView = view.findViewById(R.id.note_text_input_view);
        if (args.getNoteId() != DEFAULT_ID) {
            viewModel.setEditMode(args.getNoteId());

            viewModel.getNoteData().observe(this.getViewLifecycleOwner(), new Observer<NotePojo>() {
                @Override
                public void onChanged(NotePojo note) {
                    noteTextView.setText(note.getText());
                }
            });
        }

        view.findViewById(R.id.save_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.savingFinished(new NotePojo(
                        noteTextView.getText().toString(),
                        Calendar.getInstance().getTimeInMillis()
                ));
                NavHostFragment.findNavController(EditNoteFragment.this).popBackStack();
            }
        });

        viewModel.getMessage().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_edit_note, container, false);
    }
}

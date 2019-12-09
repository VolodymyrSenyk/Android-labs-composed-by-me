package com.senyk.volodymyr.lab6.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.senyk.volodymyr.lab6.R;
import com.senyk.volodymyr.lab6.fragments.EditNoteFragment;
import com.senyk.volodymyr.lab6.fragments.NotesListFragment;

public class NotesAppActivity extends AppCompatActivity implements Navigation {
    private static final String NOTES_LIST_FRAGMENT_TAG = "NOTES LIST";
    private static final String EDIT_NOTE_FRAGMENT_TAG = "EDIT NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_app);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentByTag(EDIT_NOTE_FRAGMENT_TAG);
        if (currentFragment == null) {
            addNotesListFragment();
        }
    }

    private void addNotesListFragment() {
        Fragment fragment = NotesListFragment.newInstance(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, NOTES_LIST_FRAGMENT_TAG);
        transaction.commit();
    }

    private void addNewNoteCreationFragment() {
        Fragment fragment = EditNoteFragment.newInstance(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, EDIT_NOTE_FRAGMENT_TAG);
        transaction.addToBackStack(EDIT_NOTE_FRAGMENT_TAG);
        transaction.commit();
    }

    private void addEditNoteFragment(long noteId) {
        Fragment fragment = EditNoteFragment.newInstance(noteId, this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, EDIT_NOTE_FRAGMENT_TAG);
        transaction.addToBackStack(EDIT_NOTE_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void addNoteButtonClicked() {
        addNewNoteCreationFragment();
    }

    @Override
    public void onNoteClicked(long noteId) {
        addEditNoteFragment(noteId);
    }

    @Override
    public void onNoteSaved() {
        onBackPressed();
    }
}

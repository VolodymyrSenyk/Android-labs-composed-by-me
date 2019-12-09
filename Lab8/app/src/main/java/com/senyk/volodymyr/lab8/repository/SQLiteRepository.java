package com.senyk.volodymyr.lab8.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.senyk.volodymyr.lab8.models.NotePojo;

import java.util.ArrayList;
import java.util.List;

public class SQLiteRepository extends SQLiteOpenHelper implements Repository {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesApp";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATE_OF_LAST_EDITION = "date";

    private static SQLiteRepository repository;

    private SQLiteRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteRepository getInstance(Context context) {
        if (repository == null) repository = new SQLiteRepository(context);
        return repository;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT,"
                + KEY_DATE_OF_LAST_EDITION + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    @Override
    public List<NotePojo> loadNotes() {
        List<NotePojo> notesList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                NotePojo note = new NotePojo(
                        cursor.getString(1),
                        Long.parseLong(cursor.getString(2))
                );
                notesList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notesList;
    }

    @Nullable
    @Override
    public NotePojo loadNoteData(long dateOfLastEditing) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{KEY_ID,
                        KEY_TEXT, KEY_DATE_OF_LAST_EDITION}, KEY_ID + "=?",
                new String[]{String.valueOf(dateOfLastEditing)}, null, null, null, null);
        NotePojo note = null;
        if (cursor != null) {
            cursor.moveToFirst();
            note = new NotePojo(
                    cursor.getString(1),
                    Long.parseLong(cursor.getString(2))
            );
            cursor.close();
        }
        return note;
    }

    @Override
    public boolean addNote(NotePojo note) {
        note.setText(note.getText().trim());
        if (note.getText().equals("")) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getDateOfLastEditing());
        values.put(KEY_TEXT, note.getText());
        values.put(KEY_DATE_OF_LAST_EDITION, note.getDateOfLastEditing());
        db.insert(TABLE_NOTES, null, values);
        db.close();
        return true;
    }

    @Override
    public boolean changeNote(long noteId, NotePojo note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return addNote(note);
    }
}

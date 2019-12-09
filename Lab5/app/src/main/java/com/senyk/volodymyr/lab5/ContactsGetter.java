package com.senyk.volodymyr.lab5;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactsGetter {
    private static final Uri CONTENT_URI;
    private static final String _ID;
    private static final String DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER;

    private static final Uri Phone_CONTENT_URI;
    private static final String Phone_CONTACT_ID;
    private static final String NUMBER;

    static {
        CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        _ID = ContactsContract.Contacts._ID;
        DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Phone_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    }

    private final ContentResolver resolver;

    public ContactsGetter(ContentResolver contentResolver) {
        this.resolver = contentResolver;
    }

    private ContactPojo getSimplifiedContact(final String contact_id) {
        Cursor cursor = this.resolver.query(CONTENT_URI, null, _ID + " = ?", new String[]{contact_id}, null);

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String contact_name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                    Cursor phoneCursor = this.resolver.query(Phone_CONTENT_URI, null,
                            Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    List<String> contact_phones = new ArrayList<>();
                    while (phoneCursor != null && phoneCursor.moveToNext()) {
                        if (!contact_phones.contains(phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)))) {
                            contact_phones.add(phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)));
                        }
                    }
                    if (phoneCursor != null) {
                        phoneCursor.close();
                    }

                    cursor.close();
                    return new ContactPojo(contact_name, contact_phones.get(0));
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return new ContactPojo("", "");
    }

    public List<ContactPojo> getAllContacts() {
        List<ContactPojo> contacts = new ArrayList<>();
        Cursor cursor = resolver.query(CONTENT_URI, null, null, null, DISPLAY_NAME + " ASC");
        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
                contacts.add(getSimplifiedContact(cursor.getString(cursor.getColumnIndex(_ID))));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return contacts;
    }
}

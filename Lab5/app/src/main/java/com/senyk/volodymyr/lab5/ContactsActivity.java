package com.senyk.volodymyr.lab5;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class ContactsActivity extends AppCompatActivity {
    private static final int READ_CONTACTS_PERMISSION_REQUEST = 1;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView contactsList = findViewById(R.id.contacts_list);
        contactsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        contactsList.setLayoutManager(layoutManager);
        contactsList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        this.list = contactsList;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), getString(R.string.contacts_read_permission)) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{getString(R.string.contacts_read_permission)}, READ_CONTACTS_PERMISSION_REQUEST);
        } else {
            this.list.setAdapter(new ContactsAdapter(new ContactsGetter(getContentResolver()).getAllContacts()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACTS_PERMISSION_REQUEST) {
            myPermissionsAnalise(permissions, grantResults);
        }
    }

    private void myPermissionsAnalise(String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.list.setAdapter(new ContactsAdapter(new ContactsGetter(getContentResolver()).getAllContacts()));
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
            View.OnClickListener toSettings = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                }
            };
            Snackbar.make(findViewById(R.id.contacts_list), getString(R.string.permission_not_granted), Snackbar.LENGTH_LONG).setAction(R.string.to_settings, toSettings).show();
        } else {
            Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show();
        }
    }
}

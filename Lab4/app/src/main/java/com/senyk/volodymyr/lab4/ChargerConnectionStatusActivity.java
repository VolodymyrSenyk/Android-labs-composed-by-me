package com.senyk.volodymyr.lab4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChargerConnectionStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent notificationIntent = new Intent(getApplicationContext(), ChargerConnectionStatusService.class);
        notificationIntent.setAction(ChargerConnectionStatusService.ACTION_START_FOREGROUND);
        startService(notificationIntent);
        finish();
    }
}

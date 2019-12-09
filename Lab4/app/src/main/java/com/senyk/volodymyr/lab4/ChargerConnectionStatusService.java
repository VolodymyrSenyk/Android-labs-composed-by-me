package com.senyk.volodymyr.lab4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ChargerConnectionStatusService extends Service {
    public static final String ACTION_START_FOREGROUND = "start";
    public static final String ACTION_STOP_FOREGROUND = "stop";
    public static final String ACTION_CHARGER_CONNECTED = "connected";
    public static final String ACTION_CHARGER_DISCONNECTED = "disconnected";

    private static final String CHANNEL_FOREGROUND_NAME = "com.senyk.volodymyr.lab4";
    private static final String CHANNEL_NAME = "charger connections status";
    private static final int ID = 1;
    private static final int REQUEST_CODE = 0;
    private static final int FLAGS = 0;

    private BroadcastReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_START_FOREGROUND)) {
                startService();
            } else if (intent.getAction().equals(ACTION_STOP_FOREGROUND)) {
                stopService();
            } else if (intent.getAction().equals(ACTION_CHARGER_CONNECTED)) {
                updateNotification(true);
            } else if (intent.getAction().equals(ACTION_CHARGER_DISCONNECTED)) {
                updateNotification(false);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startService() {
        createNotificationChannel(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), ChargerConnectionStatusService.class);
        intent.setAction(ACTION_STOP_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), REQUEST_CODE, intent, FLAGS);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_FOREGROUND_NAME)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.name_foreground))
                .addAction(R.drawable.ic_launcher_foreground, getString(R.string.stop_service), pendingIntent)
                .build();

        this.receiver = new ChargerConnectionStatusReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(this.receiver, filter);

        startForeground(ID, notification);
    }

    private void stopService() {
        unregisterReceiver(this.receiver);
        stopForeground(true);
        stopSelf();
    }

    private void createNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            int priority = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_FOREGROUND_NAME, CHANNEL_NAME, priority);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotification(boolean isCharging) {
        Intent intent = new Intent(getApplicationContext(), ChargerConnectionStatusService.class);
        intent.setAction(ACTION_STOP_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), REQUEST_CODE, intent, FLAGS);

        String text;
        if (isCharging) {
            text = getApplicationContext().getString(R.string.is_charging);
        } else {
            text = getApplicationContext().getString(R.string.is_not_charging);
        }
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_FOREGROUND_NAME)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.name_foreground))
                .setContentText(text)
                .addAction(R.drawable.ic_launcher_foreground, getString(R.string.stop_service), pendingIntent)
                .build();

        startForeground(ID, notification);
    }
}

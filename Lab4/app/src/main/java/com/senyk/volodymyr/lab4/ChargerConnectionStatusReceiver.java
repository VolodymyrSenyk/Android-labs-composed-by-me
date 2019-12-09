package com.senyk.volodymyr.lab4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class ChargerConnectionStatusReceiver extends BroadcastReceiver {
    // the system periodically sends message about the charger connection status,
    // so you need to prevent the notification updating if the status has not changed
    private static boolean IS_CHARGING = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, ChargerConnectionStatusService.class);
        int intentExtraStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (intentExtraStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
                intentExtraStatus == BatteryManager.BATTERY_STATUS_FULL);
        if (isCharging == IS_CHARGING) {
            return;
        }
        IS_CHARGING = isCharging;
        if (isCharging) {
            notificationIntent.setAction(ChargerConnectionStatusService.ACTION_CHARGER_CONNECTED);
        } else {
            notificationIntent.setAction(ChargerConnectionStatusService.ACTION_CHARGER_DISCONNECTED);
        }
        context.startService(notificationIntent);
    }
}

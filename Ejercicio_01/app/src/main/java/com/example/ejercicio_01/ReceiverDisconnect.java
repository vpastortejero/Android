package com.example.ejercicio_01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverDisconnect extends BroadcastReceiver {

    Response responseBattery;

    @Override
    public void onReceive(Context context, Intent intent) {
        responseBattery = (ReceiverDisconnect.Response) context;
        responseBattery.onResponseBatteryDisconnect(false);
    }

    public interface Response {
        public void onResponseBatteryDisconnect(boolean battery);
    }
}

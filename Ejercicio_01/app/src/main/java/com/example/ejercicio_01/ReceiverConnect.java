package com.example.ejercicio_01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverConnect extends BroadcastReceiver {

    Response responseBattery;

    @Override
    public void onReceive(Context context, Intent intent) {
        responseBattery = (Response) context;
        //Toast.makeText(context, "CARGANDO...", Toast.LENGTH_SHORT).show();
        responseBattery.onResponseBatteryConnect(true);
    }

    public interface Response {
        public void onResponseBatteryConnect(boolean battery);
    }
}

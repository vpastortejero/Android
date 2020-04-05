package com.example.ejercicio_01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverConnect extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "CARGANDO...", Toast.LENGTH_SHORT).show();
    }
}

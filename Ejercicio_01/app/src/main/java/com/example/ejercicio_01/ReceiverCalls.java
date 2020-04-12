package com.example.ejercicio_01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverCalls extends BroadcastReceiver {

    Response responseCalls;

    @Override
    public void onReceive(Context context, Intent intent) {
        responseCalls = (Response) context;
        responseCalls.onResponseCalls("LLamando");
    }

    public interface Response {
        public void onResponseCalls(String string);
    }
}

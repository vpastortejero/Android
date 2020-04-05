package com.example.ejercicio_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog.Response {

    DialogFragment dialogFragment = new Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** COMPRUEBA LA CONEXION DEL CARGADOR */
        ReceiverConnect receiverConnect = new ReceiverConnect();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        this.registerReceiver(receiverConnect, intentFilter);
        /** COMPRUEBA LA DESCONEXION DEL CARGADOR */
        ReceiverDisconnect receiverDisconnect = new ReceiverDisconnect();
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        this.registerReceiver(receiverDisconnect, intentFilter);

    }

    @Override
    /** Esta clase se lanza una vez para crear el menú superior de la derecha */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Indicamos nuestro recurso menú anteriormente creado
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /** No hará fata llamar a setOnClickListener, este método hará el trabajo por nosotros */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Se ha pulsado el item1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                dialogFragment.show(getSupportFragmentManager(), "Dialog");
                break;
            case R.id.item3:
                Toast.makeText(this, "Se ha pulsado el item3", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    /** REALIZA LAS ACCIONES FIJADAS POR EL DIALOG */
    public void onResponse(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        LinearLayout linearLayout = findViewById(R.id.linearLayaout);
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(string);
        linearLayout.addView(radioButton);
    }
}

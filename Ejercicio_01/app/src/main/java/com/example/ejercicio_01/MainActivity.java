package com.example.ejercicio_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Dialog.Response, MyAdapter.ItemClickListener, ReceiverConnect.Response, ReceiverDisconnect.Response, ReceiverCalls.Response {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    DialogFragment dialogFragment = new Dialog();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> myDataSet = new ArrayList<>();
    boolean batteryConnect;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermisos();
        checkBattery();
        checkCalls();

        /** RECYCLERVIEW PARA GESTIONAR LA LISTA PRINCIPAL */
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(myDataSet, batteryConnect);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

        /** CONECTAR CON BBDD */
        sqLiteDatabase = openOrCreateDatabase("MiDB", Context.MODE_PRIVATE, null);
        /** CREAR TABLA SINO EXISTE */
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS myTable(description VARCHAR, image BLOB)");

    }

    public void checkBattery(){
        /** COMPRUEBA LA CONEXION DEL CARGADOR */
        ReceiverConnect receiverConnect = new ReceiverConnect();
        IntentFilter intentFilterConnect = new IntentFilter();
        intentFilterConnect.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        this.registerReceiver(receiverConnect, intentFilterConnect);
        /** COMPRUEBA LA DESCONEXION DEL CARGADOR */
        ReceiverDisconnect receiverDisconnect = new ReceiverDisconnect();
        IntentFilter intentFilterDisconnect = new IntentFilter();
        intentFilterDisconnect.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        this.registerReceiver(receiverDisconnect, intentFilterDisconnect);
    }

    public void checkCalls(){
        /** COMPROBAR LLAMADAS ENTRANTES */
        ReceiverCalls receiverCalls = new ReceiverCalls();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        this.registerReceiver(receiverCalls, intentFilter);
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    /** REALIZA LAS ACCIONES FIJADAS POR EL DIALOG */
    public void onResponse(String string) {
        //Comprobamos el estado de la batería
        if (batteryConnect){
            myDataSet.add(string);
            mAdapter.notifyDataSetChanged();
        }else{
            myDataSet.add(string);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * NO ESTA FUNCIONANDO YA QUE NO HAY NINGUN BOTON
     */
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked \" + mAdapter.getItem(position) + \" on row number \" + position", Toast.LENGTH_SHORT).show();
        //Log.d("prueba", "You clicked " + mAdapter.getItem(position) + " on row number " + position);
    }

    /**
     * NO ESTA FUNCIONANDO YA QUE NO HAY NINGUN BOTON
     */
    // Para añadir nuevos elementos consultar: https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data/48959184#48959184
    public void boton(View v) {
        myDataSet.add("Hola soy Vicente");
        mAdapter.notifyDataSetChanged();
    }

    private void pedirPermisos() {
        // Here, thisActivity is the current activity
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED)) {

            // Permission is not granted
            // Should we show an explanation?
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) || (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG))) {
                Toast.makeText(this, "Segunda petición permiso", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Toast.makeText(this, "Todos los permisos concedidos con anterioridad", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    /** RECIBE EL ESTADO CONECTADO DE LA BATERÍA */
    public void onResponseBatteryConnect(boolean battery) {
        this.batteryConnect = true;
        Toast.makeText(this, "Cargando batería ...", Toast.LENGTH_SHORT).show();
        mAdapter = new MyAdapter(myDataSet, batteryConnect);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    /** RECIBE EL ESTADO DESCONECTADO DE LA BATERÍA */
    public void onResponseBatteryDisconnect(boolean battery) {
        this.batteryConnect = false;
        Toast.makeText(this, "Cargador desconectado ...", Toast.LENGTH_SHORT).show();
        mAdapter = new MyAdapter(myDataSet, batteryConnect);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResponseCalls(String string) {

    }
}

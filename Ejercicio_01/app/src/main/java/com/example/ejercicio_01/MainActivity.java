package com.example.ejercicio_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.Response, MyAdapter.ItemClickListener {

    DialogFragment dialogFragment = new Dialog();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private String[] myDataSet = {"Prueba1", "Prueba2", "Prueba3"};
    private ArrayList<String> myDataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** RECYCLERVIEW PARA GESTIONAR LA LISTA PRINCIPAL */
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //myDataSet.add("Prueba1");
        //myDataSet.add("Prueba2");
        //myDataSet.add("Prueba3");
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataSet);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

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
        /*
        LinearLayout linearLayout = findViewById(R.id.linearLayaout);
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(string);
        linearLayout.addView(radioButton);
         */
        myDataSet.add(string);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked \" + mAdapter.getItem(position) + \" on row number \" + position", Toast.LENGTH_SHORT).show();
        //Log.d("prueba", "You clicked " + mAdapter.getItem(position) + " on row number " + position);
    }

    // Para añadir nuevos elementos consultar: https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data/48959184#48959184
    public void boton(View v) {
        myDataSet.add("Hola soy Vicente");
        mAdapter.notifyDataSetChanged();
    }
}

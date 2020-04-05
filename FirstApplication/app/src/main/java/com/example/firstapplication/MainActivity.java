package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        //Hago referencia al elemento que quiero capturar
        EditText campoTexto = findViewById(R.id.TextView);
        //Cojo el texto
        String textoUsuario = campoTexto.getText().toString();
        //Y lo muestro en una tostada
        Toast.makeText(this, textoUsuario, Toast.LENGTH_LONG).show();

    }

    public void forma2(View v) {
        //Tostada para comprobar que funciona
        Toast.makeText(this, "Forma 2 de poner un botón", Toast.LENGTH_SHORT).show();
    }
}

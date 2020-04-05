package com.example.ejercicio_01;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dialog extends DialogFragment {

    Response response;
    boolean save;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog, null);

        // Crea el diálogo normal no personalizado
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        RadioButton radioButtonSave = v.findViewById(R.id.radioButtonSave);
                        Switch switchDate = v.findViewById(R.id.switchDate);
                        if (radioButtonSave.isChecked()) {
                            if (switchDate.isChecked()){
                                //Toast.makeText(getContext(), "EL BOTON GUARDAR ESTA ACTIVADO", Toast.LENGTH_SHORT).show();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.FRANCE);
                                String dateTime = dateFormat.format(new Date());
                                //Toast.makeText(getContext(), dateTime.toString(), Toast.LENGTH_SHORT).show();
                                response.onResponse(dateTime);
                            }
                        }else{
                            dialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        response = (Response) context;
    }

    public interface Response{
        public void onResponse(String string);
    }
}

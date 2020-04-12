package com.example.ejercicio_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> mDataSet;
    private boolean image;
    private ItemClickListener mClickListener;

    /**
     * Constructor al que le pasamos como parámetro los datos
     * @param mDataSet
     */
    public MyAdapter(ArrayList<String> mDataSet, boolean image) {
        this.mDataSet = mDataSet;
        this.image = image;
    }

    /**
     * El layout manager llama a este método
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    /**
     * Este método reemplaza en contenido de las vistas
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mDataSet.get(position) + " " + image);
        if(image){
            holder.imageView.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
        }else{
            holder.imageView.setImageResource(R.drawable.ic_remove_circle_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // convenience method for getting data at click position
    public String getItem(int pos) {
        return mDataSet.get(pos);
    }

    /**
     * Aquí utilizamos toas las referencias a las vistas a mostrar en cada fila
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            // Debemos tener acceso a las vistas a través de la vista que nos llega (LinearLayout)
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}

package com.example.inncoffee.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.R;

public class ObjetosViewHolderPlatosTapas extends RecyclerView.ViewHolder {
    public ImageView Imagen;
    public TextView titulo;
    public TextView Precio;
    public TextView tv7;
    public Button btPlato;
    public Button btTapa;
    public TextView Cantidad;
    public ImageView Suma;
    public ImageView Resta;
    public Button anadir;

    public ObjetosViewHolderPlatosTapas(View itemView) {
        super(itemView);
        Imagen = itemView.findViewById(R.id.imageViewImagen);
        titulo = itemView.findViewById(R.id.textViewTittle);
        Precio = itemView.findViewById(R.id.textViewPrice);
        Cantidad = itemView.findViewById(R.id.textViewCantidadPT);
        Suma = itemView.findViewById(R.id.imageViewSumaPT);
        Resta = itemView.findViewById(R.id.imageViewRestaPT);
        btTapa = itemView.findViewById(R.id.buttonTapa);
        btPlato = itemView.findViewById(R.id.buttonPlato);
        anadir = itemView.findViewById(R.id.buttonAnadirPT);

    }
}

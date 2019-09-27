package com.example.inncoffee.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.R;


public class ObjetosViewHolderPanes extends RecyclerView.ViewHolder {
    public ImageView Imagen;
    public TextView titulo;
    public TextView Precio;
    public TextView tv7;
    public Button btSelec;

    public ObjetosViewHolderPanes(View itemView) {
        super(itemView);

        Imagen = itemView.findViewById(R.id.imageView11);
        titulo = itemView.findViewById(R.id.textView5);
        Precio = itemView.findViewById(R.id.textView6);
        // tv7= (TextView) itemView.findViewById(R.id.textView8);

        btSelec = itemView.findViewById(R.id.buttonseleccion);
    }


}

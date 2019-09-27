package com.example.inncoffee.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.R;


public class ObjetosViewHolder extends RecyclerView.ViewHolder {
    public ImageView Imagen;
    public TextView titulo;
    public TextView Precio;
    public TextView tv7;
    public Button btSelec;
    public Button btMedia;
    public Button btEntera;
    public TextView Cantidad;
    public ImageView Suma;
    public ImageView Resta;
    public Button anadir;
    public TextView alergiaLista;

    public ObjetosViewHolder(View itemView) {
        super(itemView);

        Imagen = itemView.findViewById(R.id.imageView11);
        titulo = itemView.findViewById(R.id.textView5);
        Precio = itemView.findViewById(R.id.textView6);
        // tv7= (TextView) itemView.findViewById(R.id.textView8);
        Cantidad = itemView.findViewById(R.id.textViewCantidad);
        Suma = itemView.findViewById(R.id.imageViewSuma);
        Resta = itemView.findViewById(R.id.imageViewResta);
        btSelec = itemView.findViewById(R.id.buttonSelec);
        btMedia = itemView.findViewById(R.id.buttonMedia);
        btEntera = itemView.findViewById(R.id.buttonEntera);
        anadir = itemView.findViewById(R.id.buttonAnadir);
        alergiaLista = itemView.findViewById(R.id.textViewListaA);

    }


}

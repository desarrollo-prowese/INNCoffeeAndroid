package com.example.inncoffee.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.R;

public class ObjetosViewHolderPedidos extends RecyclerView.ViewHolder {

    public TextView precioPedido;
    public TextView fechaPedido;
    public TextView Nombre;
    public TextView Precio;

    public ObjetosViewHolderPedidos(View itemView) {
        super(itemView);
        precioPedido = itemView.findViewById(R.id.textViewPrecioPedido);
        fechaPedido = itemView.findViewById(R.id.textViewFecha);
        Nombre = itemView.findViewById(R.id.textViewNombreO);
        Precio = itemView.findViewById(R.id.textViewPrecioO);
    }
}

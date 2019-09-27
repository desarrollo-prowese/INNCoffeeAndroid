package com.example.inncoffee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.inncoffee.R;

import java.util.ArrayList;

public class AdapterItemPedidos extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titulo;
    private final ArrayList<String> precio;

    public AdapterItemPedidos(Activity context, ArrayList<String> titulo, ArrayList<String> precio) {
        super(context, R.layout.vista_pedidos_clientes, titulo);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.titulo = titulo;
        this.precio = precio;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.vista_pedidos_clientes, null, true);

        TextView titleText = rowView.findViewById(R.id.textViewFecha);
        TextView subtitleText = rowView.findViewById(R.id.textViewPrecioPedido);
        System.out.println(titulo.toString());
        System.out.println(precio.toString());
        titleText.setText(titulo.get(position));

        subtitleText.setText(String.valueOf(precio.get(position)));

        return rowView;

    }

}

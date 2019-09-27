package com.example.inncoffee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.inncoffee.R;

import java.util.ArrayList;


public class AdapterItem extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titulo;
    private final ArrayList<String> precio;

    public AdapterItem(Activity context, ArrayList<String> titulo, ArrayList<String> precio) {
        super(context, R.layout.vista_objetos_lista, titulo);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.titulo = titulo;
        this.precio = precio;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.vista_objetos_lista, null, true);

        TextView titleText = rowView.findViewById(R.id.TextViewTitulo);
        TextView subtitleText = rowView.findViewById(R.id.TextViewPrecio);
        System.out.println(titulo.toString());
        System.out.println(precio.toString());
        titleText.setText(titulo.get(position));

        subtitleText.setText(String.valueOf(precio.get(position)));

        return rowView;

    }

}
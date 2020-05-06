package com.example.inncoffee.ui.mispuntos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;

public class AdapterPuntos extends RecyclerView.Adapter<AdapterPuntos.ViewHolder> {

    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<PuntosClass> mensajeslist;


    public AdapterPuntos (Context mContext, ArrayList<PuntosClass> mensajeslist, ArrayList<String> keys, int resource){
        this.mContext = mContext;
        this.mensajeslist = mensajeslist;
        this.resource = resource;
        this.keys = keys;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final PuntosClass mensajes = mensajeslist.get(position);
        holder.textViewmensaje.setText(mensajes.getTexto());
        holder.fecha.setText(mensajes.getFecha());
        holder.comanda.setText(mensajes.getComanda());
        holder.layor.setImageResource(R.drawable.trazado_52_ek10);
        holder.Fondo.setImageResource(R.drawable.rect_ngulo_1491_shape);

    }



    @Override
    public int getItemCount() {
            return mensajeslist.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewmensaje,fecha,comanda;
        private ImageView layor;
        private ImageView Fondo;


        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.layor = (ImageView) view.findViewById(R.id.imageView2);
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.Fondo = (ImageView) view.findViewById(R.id.fondo);
            this.fecha = (TextView) view.findViewById(R.id.fecha);
            this.comanda = (TextView) view.findViewById(R.id.comanda);


        }
    }



}



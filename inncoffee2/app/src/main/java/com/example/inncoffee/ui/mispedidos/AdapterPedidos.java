package com.example.inncoffee.ui.mispedidos;


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

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolder> {

    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<MisPedidosClass> mensajeslist;


    public AdapterPedidos(Context mContext, ArrayList<MisPedidosClass> mensajeslist, ArrayList<String> keys, int resource){
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

        final MisPedidosClass mensajes = mensajeslist.get(position);
        holder.textViewmensaje.setText(mensajes.getTexto());
        holder.precio.setText(mensajes.getPrecio());

    }


    @Override
    public int getItemCount() {
            return mensajeslist.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewmensaje;
        private TextView precio;


        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.precio = (TextView) view.findViewById(R.id.precioTotal);


        }
    }



}



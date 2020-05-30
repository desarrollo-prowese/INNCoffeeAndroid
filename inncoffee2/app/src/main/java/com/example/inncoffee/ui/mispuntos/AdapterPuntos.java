package com.example.inncoffee.ui.mispuntos;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final PuntosClass mensajes = mensajeslist.get(position);
        holder.textViewmensaje.setText(mensajes.getTexto());
        holder.fecha.setText(mensajes.getFecha());
        holder.comanda.setText(mensajes.getComanda());
        holder.precio.setText(mensajes.getPrecio());
        holder.layor.setImageResource(R.drawable.trazado_52_ek10);
        holder.Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("String:" , holder.lol.getLayoutParams().height+"//"+ holder.layor.getLayoutParams().height+"//"+ holder.re.getLayoutParams().height);
                if (holder.lol.getLayoutParams().height == 240){
                    holder.layor.getLayoutParams().height = 540;
                    holder.re.getLayoutParams().height = 300;
                    holder.lol.getLayoutParams().height = 540;
                    holder.lol.requestLayout();
                }
                else if (holder.lol.getLayoutParams().height == 540){
                         holder.layor.getLayoutParams().height = 240;
                         holder.lol.getLayoutParams().height = 240;
                         holder.re.getLayoutParams().height = 60;
                         holder.lol.requestLayout();
                }

            }
        });

    }




    @Override
    public int getItemCount() {
            return mensajeslist.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewmensaje,fecha,comanda,precio;
        private ImageView layor;
        private Button Test;
        private ConstraintLayout lol;
        private RecyclerView re;


        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.layor = (ImageView) view.findViewById(R.id.imageView2);
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.fecha = (TextView) view.findViewById(R.id.fecha);
            this.comanda = (TextView) view.findViewById(R.id.comanda);
            this.precio = (TextView) view.findViewById(R.id.precio);
            this.Test = (Button) view.findViewById(R.id.tests);
            this.lol = (ConstraintLayout) view.findViewById(R.id.containerpedido);
            this.re = (RecyclerView) view.findViewById(R.id.re);



        }
    }



}



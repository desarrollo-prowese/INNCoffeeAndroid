package com.example.inncoffee.ui.mensajes;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.inncoffee.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
;



import java.util.ArrayList;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterMensaje  extends RecyclerView.Adapter<AdapterMensaje.ViewHolder> {

    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<MensajesClass> mensajeslist;
    private OnItemClickListener mListener;

    public AdapterMensaje(Context mContext,ArrayList<MensajesClass> mensajeslist,ArrayList<String> keys, int resource){
        this.mContext = mContext;
        this.mensajeslist = mensajeslist;
        this.resource = resource;
        this.keys = keys;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view, mListener);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final MensajesClass mensajes = mensajeslist.get(position);
        holder.textViewmensaje.setText(mensajes.getTexto());
        holder.layor.setImageResource(R.drawable.trazado_52_ek10);
        holder.Fondo.setImageResource(R.drawable.rect_ngulo_1491_shape);

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
            return mensajeslist.size();
    }

    public void deleteItem(int position) {

        String key = keys.get(position);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Mensajes");
        ref.child(key).removeValue();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewmensaje;
        private ImageView layor;
        private ImageView Fondo;
        private ImageView borrar;


        public View view;

        public ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            this.view = view;
            this.layor = (ImageView) view.findViewById(R.id.imageView2);
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.Fondo = (ImageView) view.findViewById(R.id.fondo);
            this.borrar = (ImageView) view.findViewById(R.id.BasuraMensaje);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });


        }
    }



}



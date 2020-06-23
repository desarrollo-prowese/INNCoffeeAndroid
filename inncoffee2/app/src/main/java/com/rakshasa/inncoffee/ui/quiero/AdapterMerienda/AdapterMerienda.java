package com.rakshasa.inncoffee.ui.quiero.AdapterMerienda;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

;

public class AdapterMerienda extends RecyclerView.Adapter<AdapterMerienda.ViewHolder> {

    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<MisPedidosClass> mensajeslist;
    private OnItemClickListener mListener;
    private String ID,precios ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;


    public AdapterMerienda (Context mContext, ArrayList<MisPedidosClass> mensajeslist, ArrayList<String> keys, int resource){
        this.mContext = mContext;
        this.mensajeslist = mensajeslist;
        this.resource = resource;
        this.keys = keys;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ViewHolder(view,mListener);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final MisPedidosClass mensajes = mensajeslist.get(position);
        holder.textViewmensaje.setText(mensajes.getTexto());
        holder.enteraprecio.setText(mensajes.getPrecio());
        holder.cambiomenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mDatabase = FirebaseDatabase.getInstance();
                mTosta = mDatabase.getReference();
                mAuth = FirebaseAuth.getInstance();
                ID = mAuth.getUid();
                final String key3 = mTosta.push().getKey();
                mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        String texto = mensajes.getTexto();
                        String precio = mensajes.getPrecio();
                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                        mTosta.child("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID).child(key3).setValue(user2);
                        mTosta.child("MisPedidos").child("PedidosFinalizadosMerienda").child(ID).child(key3).setValue(user2);

                    }


                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {
                        Log.w("TAG", "Failed to read value.", databaseError.toException());
                    }
                });


            }
        });


    }

    public interface OnItemClickListener{
        void onItemClick (int position);
    }

    public void setOnItemClickListener(AdapterMerienda.OnItemClickListener listener) {
        mListener = listener;
    }


    public void deleteItem(int position) {

        String key = keys.get(position);





    }




    @Override
    public int getItemCount() {
            return mensajeslist.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewmensaje;

        public View view;
        private ConstraintLayout botonera,conteiner;
        private Spinner selecionarpan;
        private TextView enteraprecio,mediaprecio,desc;
        private ImageView cambiomenu2;

        public ViewHolder(View view,final AdapterMerienda.OnItemClickListener listener) {
            super(view);
            this.view = view;
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.botonera = (ConstraintLayout) view.findViewById(R.id.botonera1);
            this.conteiner = (ConstraintLayout) view.findViewById(R.id.conteinermnesaje);
            this.enteraprecio= (TextView) view.findViewById(R.id.precio);
            this.cambiomenu2 = (ImageView) view.findViewById(R.id.Añadirl);
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

        }
    }



}



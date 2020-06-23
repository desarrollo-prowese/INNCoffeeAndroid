package com.rakshasa.inncoffee.ui.mispuntos;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.mispedidos.AdapterPedidos;
import com.rakshasa.inncoffee.ui.mispedidos.AdapterPedidos1;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class AdapterPuntos extends RecyclerView.Adapter<AdapterPuntos.ViewHolder> {
    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<PuntosClass> mensajeslist;
    private ArrayList<MisPedidosClass> mMensajesDentro = new ArrayList<>();
    private ArrayList<String> mMensajesDentro1 = new ArrayList<>();
    private String ID,ComandaID,ComandaID2 ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    private FirebaseDatabase mDatabase;
    private AdapterPedidos1 mAdapter;


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
        holder.lugar.setText(mensajes.getLugar());
        holder.layor.setImageResource(R.drawable.trazado_52_ek10);
        holder.re.setLayoutManager(new LinearLayoutManager(null));
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        ID = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mUsuario = mDatabase.getReference("Comanda");
        mUsuario.child(ID).child("Imprimir").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    mMensajesDentro1.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                       ComandaID = ds.child("NumeroComanda").getValue().toString();
                       mMensajesDentro1.add(ComandaID);

                        mUsuario.child(ID).child("ImprimirLista").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    mMensajesDentro.clear();
                                    keys.clear();
                                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                                        String id = dss.getKey();
                                        assert id != null;
                                        for (DataSnapshot ids : dataSnapshot.child(id).getChildren()){
                                                  String numcos = ids.child("precio").getValue().toString();
                                                  String textos = ids.child("texto").getValue().toString();
                                                  mMensajesDentro.add(new MisPedidosClass(textos,numcos));
                                                  keys.add(ids.toString());
                                                  Log.v("QUEPASA AKI", "QUE PASAAQUI: "+ keys.toString());

                                               //


                                        }

                                    }
                                    mAdapter = new AdapterPedidos1(mContext, keys, mMensajesDentro, R.layout.contenido_mispedidos1);
                                    holder.re.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






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
        private TextView textViewmensaje,fecha,comanda,precio,lugar;
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
            this.lugar = (TextView) view.findViewById(R.id.lugar);



        }
    }



}



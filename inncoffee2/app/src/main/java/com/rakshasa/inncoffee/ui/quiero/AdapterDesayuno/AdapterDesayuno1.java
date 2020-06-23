package com.rakshasa.inncoffee.ui.quiero.AdapterDesayuno;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class AdapterDesayuno1 extends RecyclerView.Adapter<AdapterDesayuno1.ViewHolder> {

    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<MisPedidosClass> mensajeslist;
    private OnItemClickListener mListener;
    private String ID,precio ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;


    public AdapterDesayuno1 (Context mContext, ArrayList<MisPedidosClass> mensajeslist, ArrayList<String> keys, int resource){
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
        holder.mediaprecio.setText(mensajes.getPrecio2());
        //final String[] items = new String[] {"SELECIONAR PAN", "Chapata", "Mollete","Integral","Semilla","Pan Negro","Prieto", "Sin Gluten","Croissant"};
        mDatabase = FirebaseDatabase.getInstance();
        mTosta = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        ID = mAuth.getUid();
        mTosta.child("TostadasClasicas").child("TipoPanes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> PAN = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    PAN.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("tipo").getValue().toString();
                        PAN.add(precio);

                    }


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_spinner_item, PAN);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.selecionarpan.setAdapter(adapter);
                holder.selecionarpan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        mTosta.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                            dataSnapshot.child("TostadasClasicas").child("TipoPanes").child(String.valueOf(holder.selecionarpan.getSelectedItemPosition())).child("Alergia").getValue()))
                                    {
                                        holder.añadir1.setVisibility(View.INVISIBLE);
                                        holder.añadir2.setVisibility(View.INVISIBLE);
                                    }
                                    else{
                                        holder.añadir1.setVisibility(View.VISIBLE);
                                        holder.añadir2.setVisibility(View.VISIBLE);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mTosta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                            dataSnapshot.child("TostadasOriginales").child("Entera").child(String.valueOf(position)).child("Alergia").getValue()))
                    {
                        holder.añadir1.setVisibility(View.INVISIBLE);
                        holder.añadir2.setVisibility(View.INVISIBLE);
                        holder.conteiner.setVisibility(View.INVISIBLE);
                    }
                    else{
                        holder.añadir1.setVisibility(View.VISIBLE);
                        holder.añadir2.setVisibility(View.VISIBLE);
                        holder.conteiner.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });



        holder.conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("STORNMG: " , String.valueOf(position));
                if (holder.botonera.getLayoutParams().height == 1){
                    holder.botonera.getLayoutParams().height = 450;
                    holder.cambiomenu2.setBackgroundResource(R.drawable.menornegros);
                    holder.botonera.requestLayout();

                }
                else if (holder.botonera.getLayoutParams().height == 450){
                    holder.botonera.getLayoutParams().height = 1;
                    holder.cambiomenu2.setBackgroundResource(R.drawable.plusnegros);
                    holder.botonera.requestLayout();



                }
            }
        });

                holder.añadir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(holder.selecionarpan.getSelectedItem().toString().equals("SELECIONAR PAN")){
                    Toast.makeText(mContext, "Selecione el pan", Toast.LENGTH_SHORT).show();

                }else {
                mAuth = FirebaseAuth.getInstance();
                ID = mAuth.getUid();
                final String key3 = mTosta.push().getKey();
                mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        String texto = mensajes.getTexto() + " / " + " Media " + " / " + holder.selecionarpan.getSelectedItem().toString() ;
                        String precio = mensajes.getPrecio2();
                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                        mTosta.child("MisPedidos").child("PedidosSinFinalizar").child(ID).child(key3).setValue(user2);
                        mTosta.child("MisPedidos").child("PedidosFinalizados").child(ID).child(key3).setValue(user2);

                    }


                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {
                        Log.w("TAG", "Failed to read value.", databaseError.toException());
                    }
                });
                }
               // Toast.makeText(mContext, "AÑADIR: " + mensajes.getTexto() + " Media " , Toast.LENGTH_SHORT).show();
            }
        });
        holder.añadir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
               if(holder.selecionarpan.getSelectedItem().toString().equals("SELECIONAR PAN")){
                   Toast.makeText(mContext, "Selecione el pan", Toast.LENGTH_SHORT).show();

               }else {
                   mAuth = FirebaseAuth.getInstance();
                   ID = mAuth.getUid();
                   final String key3 = mTosta.push().getKey();
                   mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                           String texto = mensajes.getTexto() + " / " + " Entera " + " / " + holder.selecionarpan.getSelectedItem().toString();
                           String precio = mensajes.getPrecio();
                           MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                           mTosta.child("MisPedidos").child("PedidosSinFinalizar").child(ID).child(key3).setValue(user2);
                           mTosta.child("MisPedidos").child("PedidosFinalizados").child(ID).child(key3).setValue(user2);


                       }


                       @Override
                       public void onCancelled (@NonNull DatabaseError databaseError) {
                           Log.w("TAG", "Failed to read value.", databaseError.toException());
                       }
                   });
               }
               // Toast.makeText(mContext, "AÑADIR: " + mensajes.getTexto() + " Entera", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public interface OnItemClickListener{
        void onItemClick (int position);
    }

    public void setOnItemClickListener(AdapterDesayuno1.OnItemClickListener listener) {
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
        private TextView enteraprecio,mediaprecio;
        private ImageView cambiomenu2,añadir1,añadir2;

        public ViewHolder(View view,final AdapterDesayuno1.OnItemClickListener listener) {
            super(view);
            this.view = view;
            this.textViewmensaje = (TextView) view.findViewById(R.id.textomensaje);
            this.botonera = (ConstraintLayout) view.findViewById(R.id.botonera1);
            this.conteiner = (ConstraintLayout) view.findViewById(R.id.conteinermnesaje);
            this.selecionarpan = (Spinner) view.findViewById(R.id.selecionarpan);
            this.enteraprecio= (TextView) view.findViewById(R.id.enteraprecio);
            this.mediaprecio= (TextView) view.findViewById(R.id.mediaprecio);
            this.cambiomenu2 = (ImageView) view.findViewById(R.id.cambiomenu2);
            this.añadir1 = (ImageView) view.findViewById(R.id.añadir1);
            this.añadir2 = (ImageView) view.findViewById(R.id.añadir2);
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



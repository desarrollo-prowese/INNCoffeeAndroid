package com.example.inncoffee.ui.mispuntos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.HomeFragment1;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MisPuntosFragment extends Fragment {

    private AdapterPuntos mAdapter;
    private RecyclerView mRecycle;
    private ArrayList<PuntosClass> mMensaje = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private DatabaseReference mDatabase;
    private String ID ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private double puntos;
    private String processeds = "";
    private double puntoss;
    private String processedss = "";
    private TextView Total,Sin,queson;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puntos, container, false);
        MainActivity.mensajeToolbar.setText("CoINNs");
        HomeFragment1.num = 1;
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        assert ID != null;
        Sin = (TextView) root.findViewById(R.id.puntossin);
        Total = (TextView) root.findViewById(R.id.PuntosTotal);
        queson = (TextView) root.findViewById(R.id.queson);
        ID = mAuth.getUid();
        mRecycle = (RecyclerView) root.findViewById(R.id.listamensajes);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getMensajesFromFirebase();

        PuntosAcumulado();
        PuntosSinAcumular();
        queson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setMessage("Son monedas digitales de recompensa\n" +
                        "Por el consumo realizado en las cafeterías INN COFFEE.\n" +
                        "Estás generan un saldo a favor que podrás descontar de cualquier pedido");
                dialogo1.setCancelable(false);
                dialogo1.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

        return root;
    }

    private void PuntosAcumulado(){

        ID = mAuth.getUid();
        assert ID != null;
        mDatabase.child("Users").child(ID).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("DineroAcumulados").exists()){
                    ID = mAuth.getUid();
                    assert ID != null;
                    puntoss = Double.parseDouble(dataSnapshot.child("DineroAcumulados").getValue(String.class).replaceAll("[,.€]", ""));
                    NumberFormat formatter = new DecimalFormat("0,00€");
                    processedss = formatter.format(puntoss);
                    Total.setText(processedss);


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void PuntosSinAcumular(){

        ID = mAuth.getUid();
        assert ID != null;
        mDatabase.child("Users").child(ID).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("DineroAcumulado").exists()){
                    for (DataSnapshot ds : dataSnapshot.child("DineroAcumulado").getChildren()) {
                        ID = mAuth.getUid();
                        assert ID != null;
                        puntos = Double.parseDouble(ds.getValue(String.class).replaceAll("[,.€]", ""));
                        NumberFormat formatter = new DecimalFormat("0,00€");
                        processeds = formatter.format(puntos);
                        Sin.setText(processeds);
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getMensajesFromFirebase() {
        mDatabase.child("Users").child(ID).child("Puntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMensaje.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("Precio").getValue().toString();
                        String numco = ds.child("NumeroComanda").getValue().toString();
                        String texto = ds.child("PuntosAcumulado").getValue().toString();
                        String fecha = ds.child("Fecha").getValue().toString();
                        mMensaje.add(new PuntosClass(texto,numco,fecha,precio));
                        keys.add(ds.getKey());

                    }

                    mAdapter = new AdapterPuntos(getContext(), mMensaje, keys, R.layout.contenido_puntos);
                    mRecycle.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

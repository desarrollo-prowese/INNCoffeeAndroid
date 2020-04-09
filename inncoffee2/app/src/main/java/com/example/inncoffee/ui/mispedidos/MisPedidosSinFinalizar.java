package com.example.inncoffee.ui.mispedidos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.quiero.Bebidas;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.quiero.Tostadas;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisPedidosSinFinalizar extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private ArrayList<MisPedidosClass> mMensaje = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private AdapterPedidos mAdapter;
    private RecyclerView mPedidos;
    private TextView sumatotal;
    private String ID ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    private static final String USERS = "MisPedidos";

    private TextView tostadas,bebidas;

    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getActivity(), QuieroFragment.class);
                    startActivity(intent);
                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {


                    Intent intent = new Intent(getActivity(), QuieroFragment.class);
                    startActivity(intent);


                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.mispedidossinfinalizar, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");
        mPedidos = (RecyclerView) root.findViewById(R.id.mispedidossinfinalizar);

        mPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        sumatotal = (TextView) root.findViewById(R.id.total2) ;

        tostadas = (TextView) root.findViewById(R.id.tostadas);
        bebidas = (TextView) root.findViewById(R.id.bebidas);
        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        tostadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tostadas fragment = new Tostadas();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });

        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bebidas fragment = new Bebidas();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });
        getMensajesFromFirebase();
        inicialize();
     return root;
    }

    private void getMensajesFromFirebase() {

        ID = mAuth.getUid();
        mUsuario.child("PedidosSinFinalizar").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double total = 0;
                String processed = "";
                if (dataSnapshot.exists()) {


                    mMensaje.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        double number = Double.valueOf(ds.child("precio").getValue(String.class).replaceAll("[,.€]", ""));
                        total = total + number;

                        Log.d("TAG",  "total= " + total);

                        NumberFormat formatter = new DecimalFormat("###,##");

                        processed = formatter.format(total);

                        sumatotal.setText(processed);

                        String texto = ds.child("texto").getValue().toString();
                        String precio = ds.child("precio").getValue().toString();

                        mMensaje.add(new MisPedidosClass(texto,precio));
                        keys.add(ds.getKey());

                    }


                    mAdapter = new AdapterPedidos(getContext(), mMensaje, keys, R.layout.contenido_mispedidos);
                    mPedidos.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

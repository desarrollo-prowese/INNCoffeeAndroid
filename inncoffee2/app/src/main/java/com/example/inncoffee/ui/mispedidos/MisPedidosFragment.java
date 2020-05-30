package com.example.inncoffee.ui.mispedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mispuntos.AdapterPuntos;
import com.example.inncoffee.ui.mispuntos.PuntosClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisPedidosFragment extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private ArrayList<PuntosClass> mMensaje = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private AdapterPuntos mAdapter;
    private RecyclerView mPedidos;
    private String ID ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    private static final String USERS = "Comanda";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);
        MainActivity.mensajeToolbar.setText("Mis Pedidos");
        mPedidos = (RecyclerView) root.findViewById(R.id.Pedidopago);
        mPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        ID = mAuth.getUid();
        getMensajesFromFirebase();

        return root;

    }


    private void getMensajesFromFirebase() {
        mUsuario.child(ID).child("Imprimir").addValueEventListener(new ValueEventListener() {
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
                    mAdapter = new AdapterPuntos(getContext(), mMensaje, keys, R.layout.contenido_comada);
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

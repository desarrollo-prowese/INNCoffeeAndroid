package com.example.inncoffee.ui.mispedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.comidas.Platos;
import com.example.inncoffee.ui.comidas.Postres;
import com.example.inncoffee.ui.comidas.Sandwiches;
import com.example.inncoffee.ui.quiero.Bebidas;
import com.example.inncoffee.ui.quiero.BebidasComi;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisPedidosSinFinalizarComidas extends Fragment {

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
    private String texto,precio;

    private TextView combos,sandwiches,platosytapas,postres,bebidas;

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

        View root = inflater.inflate(R.layout.mispedidossinfinalizarcomidas, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");
        mPedidos = (RecyclerView) root.findViewById(R.id.mispedidossinfinalizar);

        mPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        sumatotal = (TextView) root.findViewById(R.id.total2) ;


        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        combos = (TextView) root.findViewById(R.id.combos);
        sandwiches = (TextView) root.findViewById(R.id.sandwiches);
        platosytapas = (TextView) root.findViewById(R.id.platosytapas);
        postres = (TextView) root.findViewById(R.id.postres);
        bebidas = (TextView) root.findViewById(R.id.bebidas);

        combos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Proximamente",Toast.LENGTH_SHORT).show();
            }
        });
        sandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sandwiches fragment = new Sandwiches();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });
        platosytapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platos fragment = new Platos();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });
        postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postres fragment = new Postres();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BebidasComi fragment = new BebidasComi();
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


    public void removeItem(int position) {
        mAdapter.deleteItemComidas(position);
        MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
        ftEs.replace(R.id.nav_host_fragment, fragment);
        ftEs.addToBackStack(null);
        ftEs.commit();


    }

    private void getMensajesFromFirebase() {

        ID = mAuth.getUid();
        mUsuario.child("PedidosSinFinalizarComidas").child(ID).addValueEventListener(new ValueEventListener() {
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

                        texto = ds.child("texto").getValue().toString();
                        precio = ds.child("precio").getValue().toString();

                        mMensaje.add(new MisPedidosClass(texto,precio));
                        keys.add(ds.getKey());

                    }


                    mAdapter = new AdapterPedidos(getContext(), mMensaje, keys, R.layout.contenido_mispedidos);
                    mPedidos.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new AdapterPedidos.OnItemClickListener() {

                        @Override
                        public void onItemClick(final int position) {

                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext(), position);
                            dialogo1.setMessage("Desear Borrar esta Linea");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    removeItem(position);
                                }
                            });
                            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    dialogo1.cancel();
                                }
                            });
                            dialogo1.show();

                        }

                    });
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

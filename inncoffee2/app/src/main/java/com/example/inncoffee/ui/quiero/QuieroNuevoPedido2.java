package com.example.inncoffee.ui.quiero;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizar;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizarComidas;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizarMerienda;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroNuevoPedido2 extends Fragment {


    private Button alojenos,cartacomidas,cartadesayunos,cartamerienda;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String ID ;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsu;
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

        View root = inflater.inflate(R.layout.quieronuevopedido2_fragment, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");

        alojenos = (Button) root.findViewById(R.id.alojenos);
        cartacomidas = (Button) root.findViewById(R.id.cartacomidas);
        cartadesayunos = (Button) root.findViewById(R.id.cartadesayuno);
        cartamerienda = (Button) root.findViewById(R.id.cartamerienda);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUsu = mDatabase.getReference("Users");

        alojenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuieroAlojenos fragment = new QuieroAlojenos();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });

        if (QuieroAlojenos.TengoComandaMerienda){
            cartamerienda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizarMerienda fragment = new MisPedidosSinFinalizarMerienda();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }else {
            cartamerienda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaMerienda fragment = new CartaMerienda();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }




        if (QuieroAlojenos.TengoComandaDesayuno){
            cartadesayunos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizar fragment = new MisPedidosSinFinalizar();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }
        else{

            cartadesayunos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaDesayunos fragment = new CartaDesayunos();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });
        }
        if (QuieroAlojenos.TengoComandaComidas){
            cartacomidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });


        }
        else {

            cartacomidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaComidas fragment = new CartaComidas();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
            });
        }



        Desayunos();
        Comidas();
        Merienda();





        inicialize();
        return root;
    }

    private void Desayunos() {
        ID = mAuth.getUid();
        Log.v("QUE es Desayuno :  ", String.valueOf(QuieroAlojenos.TengoComandaDesayuno));
        mDatabase.getReference("MisPedidos").child("PedidosSinFinalizar").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                QuieroAlojenos.TengoComandaDesayuno = dataSnapshot.exists();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void Merienda() {
        ID = mAuth.getUid();
        Log.v("QUE es Desayuno :  ", String.valueOf(QuieroAlojenos.TengoComandaDesayuno));
        mDatabase.getReference("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                QuieroAlojenos.TengoComandaMerienda = dataSnapshot.exists();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void Comidas() {
        ID = mAuth.getUid();
        Log.v("QUE es Comidas :  ", String.valueOf(QuieroAlojenos.TengoComandaComidas));
        mDatabase.getReference("MisPedidos").child("PedidosSinFinalizarComidas").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                QuieroAlojenos.TengoComandaComidas = dataSnapshot.exists();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

package com.example.inncoffee.ui.combos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Combos extends Fragment {

    private ConstraintLayout tapa,mediomenu,menucompleto;
    private TextView preciomenu1,preciomenu2,preciomenu3;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mPrecio;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static int menua = 0;
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.combos, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mPrecio = mDatabase.getReference("CombosPrecios");
        mediomenu = (ConstraintLayout) root.findViewById(R.id.mediomenu);
        menucompleto = (ConstraintLayout) root.findViewById(R.id.menucompleto);
        preciomenu1 = (TextView) root.findViewById(R.id.preciomenu1);
        preciomenu2 = (TextView) root.findViewById(R.id.preciomenu2);
        menua = 1;
        MenuCompleto.completo = false;
        MenuMedio.medio = false;
        Log.v("Que Menus COMPLETO ", String.valueOf(MenuCompleto.completo));
        Log.v("Que Menus MEDIO ", String.valueOf(MenuMedio.medio));



        mediomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuMedio fragment = new MenuMedio();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });

        menucompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuCompleto fragment = new MenuCompleto();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });


        mPrecio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String precio = dataSnapshot.child("mediomenu").getValue().toString();
                    String precio2 = dataSnapshot.child("menucompleto").getValue().toString();
                    preciomenu1.setText(precio2);
                    preciomenu2.setText(precio);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        inicialize();
        return root;
    }
}

package com.example.inncoffee.ui.quiero;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.bebidasComidas.bebidas.BebidasComidas;
import com.example.inncoffee.ui.bebidasComidas.bebidas.CafesComidas;
import com.example.inncoffee.ui.bebidasComidas.bebidas.TesComidas;
import com.example.inncoffee.ui.bebidasComidas.bebidas.ZumosComidas;
import com.example.inncoffee.ui.combos.BebidasCombo;
import com.example.inncoffee.ui.combos.CafesCombo;
import com.example.inncoffee.ui.combos.TesCombo;
import com.example.inncoffee.ui.combos.ZumosCombo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BebidasCombos extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView Cafe,te,bebidas,zumos;


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

        View root = inflater.inflate(R.layout.bebidas, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");
        Cafe = (TextView) root.findViewById(R.id.cafes);
        te = (TextView) root.findViewById(R.id.teeinfusiones);
        bebidas = (TextView) root.findViewById(R.id.bebidas);
        zumos = (TextView) root.findViewById(R.id.zumos);

        te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TesCombo fragment = new  TesCombo();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });

        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BebidasCombo fragment = new BebidasCombo();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });


        zumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZumosCombo fragment = new ZumosCombo();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });


        Cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CafesCombo fragment = new CafesCombo();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });




        inicialize();
        return root;
    }
}

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
import com.example.inncoffee.ui.combos.Combos;
import com.example.inncoffee.ui.comidas.Hamburgesa;
import com.example.inncoffee.ui.comidas.Platos;
import com.example.inncoffee.ui.comidas.PlatosElejir;
import com.example.inncoffee.ui.comidas.Sandwiches;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CartaComidas extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView combos,sandwiches,platosytapas,hamburgesa,bebidas;

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

       View root = inflater.inflate(R.layout.cartacomidas, container, false);
       MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
       QuieroAlojenos.ComidaoDesayuno = 1;
       Combos.menua = 0;
       combos = (TextView) root.findViewById(R.id.menus);
       sandwiches = (TextView) root.findViewById(R.id.sandwiches);
       platosytapas = (TextView) root.findViewById(R.id.platosytapas);
       hamburgesa = (TextView) root.findViewById(R.id.hamburgesa);
       bebidas = (TextView) root.findViewById(R.id.bebidas);

       combos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Combos fragment = new Combos();
               FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
               ftEs.replace(R.id.nav_host_fragment, fragment);
               ftEs.addToBackStack(null);
               ftEs.commit();
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
                PlatosElejir fragment = new PlatosElejir();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });
        hamburgesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hamburgesa fragment = new Hamburgesa();
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



        inicialize();
        return root;
    }
}

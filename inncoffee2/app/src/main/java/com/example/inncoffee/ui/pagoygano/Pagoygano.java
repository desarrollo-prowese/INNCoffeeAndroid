package com.example.inncoffee.ui.pagoygano;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.HomeFragment1;
import com.example.inncoffee.ui.mispedidos.MisPedidosFragment;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Pagoygano extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Button coins,pedidos;
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

        View root = inflater.inflate(R.layout.pagoygano, container, false);
        MainActivity.mensajeToolbar.setText("PAGO Y GANO");
        HomeFragment1.num = 1;
        inicialize();
        coins = (Button) root.findViewById(R.id.button2);
        pedidos = (Button) root.findViewById(R.id.button3);

        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                MisPuntosFragment fragmentss = new MisPuntosFragment();
                FragmentTransaction ftEsss = getParentFragmentManager().beginTransaction();
                ftEsss.replace(R.id.nav_host_fragment, fragmentss);
                ftEsss.addToBackStack(null);
                ftEsss.commit();
            }
        });

        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                MisPedidosFragment fragmentss = new MisPedidosFragment();
                FragmentTransaction ftEsss = getParentFragmentManager().beginTransaction();
                ftEsss.replace(R.id.nav_host_fragment, fragmentss);
                ftEsss.addToBackStack(null);
                ftEsss.commit();
            }
        });

        return root;
    }
}

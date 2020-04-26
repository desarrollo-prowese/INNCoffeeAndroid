package com.example.inncoffee.ui.comidas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.tostadas.TostadasClasicas;
import com.example.inncoffee.ui.tostadas.TostadasOriginales;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PlatosElejir extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView Clasico,Original,Estraordinario;


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

        View root = inflater.inflate(R.layout.tostadas, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");


        Clasico = (TextView) root.findViewById(R.id.tostadasclasicas);
        Original= (TextView) root.findViewById(R.id.tostadasoriginal);
        Clasico.setText("Platos y Tapas");
        Original.setText("Ensaladas");

        Clasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platos fragment = new Platos();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });


        Original.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ensaladas fragment = new Ensaladas();
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

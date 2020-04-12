package com.example.inncoffee.ui.quiero;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.HomeFragment1;
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

public class QuieroFragment extends Fragment {

    private Button Nuevopedido;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String ID ;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    public static boolean TengoPedidoSinFinalizar = false;
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

        View root = inflater.inflate(R.layout.quierofragment, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO");
        Nuevopedido = (Button) root.findViewById(R.id.buttonNuevoPedido);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        if (TengoPedidoSinFinalizar){
            Nuevopedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FinalizarPedido fragment = new FinalizarPedido();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });


        }
        else {

            Nuevopedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(),"Proximamente", Toast.LENGTH_SHORT).show();
                    QuieroNuevoPedido fragment = new QuieroNuevoPedido();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
            });
        }


        HomeFragment1.num = 1;
        Pedidos();
        inicialize();
        return root;
    }

    private void Pedidos() {
        ID = mAuth.getUid();
        Log.v("QUE es Pedido :  ", String.valueOf(TengoPedidoSinFinalizar));
        mDatabase.getReference("MisPedidos").child("PedidosFinalizados").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TengoPedidoSinFinalizar = dataSnapshot.exists();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

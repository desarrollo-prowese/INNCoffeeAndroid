package com.example.inncoffee.ui.combos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.bebidas.BebidasDB;
import com.example.inncoffee.ui.mispedidos.MisPedidosClass;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizarComidas;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.tostadas.TostadasDB;
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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BebidasCombo extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private long id = 0;
    BebidasDB bebidasDB;
    private Button boton, next;
    private ImageView Imagen;
    private int contador2 = 1;
    private String ID;
    private DatabaseReference mUsuario;
    private static final String USERS = "Combos";
    private TextView contador;
    private ImageView menos, plus;
    private TextView nombreArticulo, precio, descarticulo, añadir;
    private ArrayList<TostadasDB> mtos = new ArrayList<>();
    private String nombre, imagen, precios;


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

        View root = inflater.inflate(R.layout.bebidascombo, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTosta = mDatabase.getReference("Bebidas");
        bebidasDB = new BebidasDB();
        Imagen = (ImageView) root.findViewById(R.id.imagencafes);
        nombreArticulo = (TextView) root.findViewById(R.id.nombrearticulo);
        precio = (TextView) root.findViewById(R.id.precio);
        añadir = (TextView) root.findViewById(R.id.añadir);
        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();


        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = mAuth.getUid();
                final String key3 = mUsuario.push().getKey();
                mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String texto = nombre;
                        mUsuario.child("Bebida").child(ID).child("Texto").setValue(texto);

                        if (MenuCompleto.completo){
                            MenuCompleto fragment = new MenuCompleto();
                            FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                            ftEs.replace(R.id.nav_host_fragment, fragment);
                            ftEs.addToBackStack(null);
                            ftEs.commit();
                        }
                        else  if (MenuMedio.medio){
                            MenuMedio fragment = new MenuMedio ();
                            FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                            ftEs.replace(R.id.nav_host_fragment, fragment);
                            ftEs.addToBackStack(null);
                            ftEs.commit();
                        }
                        else if (TapaBebida.tapas){
                            TapaBebida fragment = new TapaBebida ();
                            FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                            ftEs.replace(R.id.nav_host_fragment, fragment);
                            ftEs.addToBackStack(null);
                            ftEs.commit();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("TAG", "Failed to read value.", databaseError.toException());
                    }
                });


            }

        });


        mTosta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    id = dataSnapshot.getChildrenCount();
                    id = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    id = 1;
                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                    nombreArticulo.setText(nombre);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        next = (Button) root.findViewById(R.id.test);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            id++;
                            nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                            imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                            nombreArticulo.setText(nombre);

                            if (id == 6) {
                                id = 1;
                                nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                nombreArticulo.setText(nombre);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        boton = (Button) root.findViewById(R.id.button5);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTosta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            id--;
                            nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                            imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                            nombreArticulo.setText(nombre);

                            if (id == 0) {
                                id = 5;
                                nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                nombreArticulo.setText(nombre);
                            }
                            Log.v("MI ID ", String.valueOf(id));
                            Log.v("NONBRE ARTICULO ", nombre);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        inicialize();
        return root;
    }
}

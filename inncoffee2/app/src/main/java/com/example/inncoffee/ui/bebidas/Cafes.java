package com.example.inncoffee.ui.bebidas;

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
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.tostadas.TostadasDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Cafes extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private long id= 0;
    BebidasDB bebidasDB;
    private Button boton,next;
    private ImageView Imagen;
    private TextView nombreArticulo,precio,descarticulo;
    private ArrayList<TostadasDB> mtos = new ArrayList<>();
    private String nombre,nombrer,nombrepan,nombrerpan,imagen,imagenpan,imagenpans,imagens,precios,precioss, barra;



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

        View root = inflater.inflate(R.layout.cafes, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTosta = mDatabase.getReference("Cafes");
        bebidasDB = new BebidasDB();
        Imagen = (ImageView) root.findViewById(R.id.imagencafes);
        nombreArticulo = (TextView) root.findViewById(R.id.nombrearticulo);
        precio = (TextView) root.findViewById(R.id.precio);
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
                           precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                           imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                           Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                           nombreArticulo.setText(nombre);
                           precio.setText(precios);

                           Log.v("NONBRE ARTICULO ", nombre);
                           Log.v("MI ID ", String.valueOf(id));

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
                          precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                          imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                          Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                          nombreArticulo.setText(nombre);
                          precio.setText(precios);

                          if (id == 9) {
                              id = 1;
                              nombrer = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                              precioss = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                              imagens = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                              Glide.with(Objects.requireNonNull(getContext())).load(imagens).into(Imagen);
                              nombreArticulo.setText(nombrer);
                              precio.setText(precioss);
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
                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                        nombreArticulo.setText(nombre);
                        precio.setText(precios);

                        if (id == 0) {
                            id = 8;
                            nombrer = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                            precioss = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                            imagens = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagens).into(Imagen);
                            nombreArticulo.setText(nombrer);
                            precio.setText(precioss);
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

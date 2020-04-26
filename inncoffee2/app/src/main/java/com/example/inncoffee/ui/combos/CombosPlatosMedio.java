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
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.tostadas.TostadasDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CombosPlatosMedio extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;
    private DatabaseReference mBarra;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private long id= 0;
    TostadasDB tostadasdb;
    private Button boton,next;
    private ImageView Imagen;
    private TextView contador;
    private String ID ;
    private DatabaseReference mUsuario;
    private static final String USERS = "Combos";
    private ImageView menos,plus;
    private DatabaseReference mCompare;
    private TextView nombreArticulo,precio,desca,añadir,titulo;
    private String nombre,imagen,precios,desc;



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

        View root = inflater.inflate(R.layout.platoscombos, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        titulo = root.findViewById(R.id.Titulo);
        titulo.setText("PRIMER PLATO");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTosta = mDatabase.getReference("Comidas");
        tostadasdb = new TostadasDB();
        mCompare = mDatabase.getReference();
        Imagen = (ImageView) root.findViewById(R.id.imagentostada);
        nombreArticulo = (TextView) root.findViewById(R.id.nombrearticulo);
        precio = (TextView) root.findViewById(R.id.precio);
        contador = (TextView) root.findViewById(R.id.textView5);
        menos = (ImageView)root.findViewById(R.id.imagecontador2);
        desca = (TextView) root.findViewById(R.id.descripcionarticulo);
        plus = (ImageView)root.findViewById(R.id.imagecontador1);
        añadir = (TextView)root.findViewById(R.id.añadir);
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
                            mUsuario.child("MedioMenu").child(ID).child("Texto").setValue(texto);

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
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }

        });


               mTosta.child("MenuMedio").addValueEventListener(new ValueEventListener() {
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
               mTosta.child("MenuMedio").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()) {
                           id = 1;
                           nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                           imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                           Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                           nombreArticulo.setText(nombre);

                           Log.v("NONBRE ARTICULO ", nombre);
                           Log.v("MI ID ", String.valueOf(id));

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
        ID = mAuth.getUid();
        mCompare.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                Log.v("Lo que sea" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Comidas").child("Ensaladas").child(String.valueOf(id)).child("Alergia").getValue() );
                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Dioxido").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Soja").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moluscos").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Cangrejo").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }

                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                            dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                        añadir.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    añadir.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });



         next = (Button) root.findViewById(R.id.test);

         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                         mTosta.child("MenuMedio").addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (dataSnapshot.exists()) {
                                     id++;
                                     nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                     imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                     Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                     nombreArticulo.setText(nombre);

                                     if (id == 12) {
                                         id = 1;
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
                 ID = mAuth.getUid();
                 mCompare.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                         Log.v("Lo que sea" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Comidas").child("Ensaladas").child(String.valueOf(id)).child("Alergia").getValue() );
                         if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Dioxido").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Soja").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moluscos").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Cangrejo").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }

                         if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                     dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                 añadir.setVisibility(View.INVISIBLE);
                             }
                         }
                         else{
                             añadir.setVisibility(View.VISIBLE);
                         }
                     }

                     @Override
                     public void onCancelled (@NonNull DatabaseError databaseError) {

                     }
                 });
                     }



         });


        boton = (Button) root.findViewById(R.id.button5);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        mTosta.child("MenuMedio").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    id--;
                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                    nombreArticulo.setText(nombre);

                                    if (id == 0) {
                                        id = 11;
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
                ID = mAuth.getUid();
                mCompare.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        Log.v("Lo que sea" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Comidas").child("Ensaladas").child(String.valueOf(id)).child("Alergia").getValue() );
                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Dioxido").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Dioxido").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Soja").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Soja").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moluscos").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moluscos").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Cangrejo").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Cangrejo").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }

                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                    dataSnapshot.child("Comidas").child("MenuMedio").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                añadir.setVisibility(View.INVISIBLE);
                            }
                        }
                        else{
                            añadir.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {

                    }
                });

            }

        });




        inicialize();
        return root;
    }
}

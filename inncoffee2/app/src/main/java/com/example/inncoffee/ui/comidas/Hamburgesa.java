package com.example.inncoffee.ui.comidas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
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

public class Hamburgesa extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static long id= 0;
    private long idpanes = 0;
    TostadasDB tostadasdb;
    private Button boton,next, media, entera, selecionarpan, selecionarpan1,cambiador;
    private boolean MediaoEntera = true;
    private boolean SelecionaPan = true;
    private boolean Cambiar = true;
    private ImageView Imagen;
    private int contador2 = 1;
    private TextView contador;
    private String ID ;
    private DatabaseReference mUsuario;
    private DatabaseReference mCompare;
    private static final String USERS = "MisPedidos";
    private ImageView menos,plus;
    public static TextView nombreArticulo,precio,descarticulo,añadir;
    private ArrayList<TostadasDB> mtos = new ArrayList<>();
    private String nombre,nombrepan,imagen,imagenpan,precios,barra;


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

        View root = inflater.inflate(R.layout.hamburgersa, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTosta = mDatabase.getReference("Hamburgesas");
        mCompare = mDatabase.getReference();
        tostadasdb = new TostadasDB();
        entera = (Button) root.findViewById(R.id.Buey);
        cambiador = (Button) root.findViewById(R.id.Cambiador);
        Imagen = (ImageView) root.findViewById(R.id.imagentostada);
        nombreArticulo = (TextView) root.findViewById(R.id.nombrearticulo);
        precio = (TextView) root.findViewById(R.id.precio);
        selecionarpan = (Button) root.findViewById(R.id.selecionarpan);
        selecionarpan1 = (Button) root.findViewById(R.id.selecionarpan1);
        contador = (TextView) root.findViewById(R.id.textView5);
        menos = (ImageView) root.findViewById(R.id.imagecontador2);
        plus = (ImageView) root.findViewById(R.id.imagecontador1);
        añadir = (TextView) root.findViewById(R.id.añadir);
        boton = (Button) root.findViewById(R.id.button5);
        next = (Button) root.findViewById(R.id.test);

        mUsuario = mDatabase.getReference(USERS);
        media = (Button) root.findViewById(R.id.Vegetariana);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        contador.setText(String.valueOf(contador2));

        if (contador2 == 1) {
            menos.setVisibility(View.INVISIBLE);
        }
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2--;
                contador.setText(String.valueOf(contador2));
                if (contador2 == 1) {
                    menos.setVisibility(View.INVISIBLE);
                } else if (contador2 < 99) {

                    plus.setVisibility(View.VISIBLE);
                }

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2++;
                contador.setText(String.valueOf(contador2));
                if (contador2 == 99) {
                    plus.setVisibility(View.INVISIBLE);
                    menos.setVisibility(View.VISIBLE);
                } else if (contador2 > 1) {
                    menos.setVisibility(View.VISIBLE);

                }

            }
        });
        cambiador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                if(Cambiar == true){
                    Cambiar= false;
                    cambia();

                }else if(Cambiar == false) {
                    Cambiar= true;
                    cambia();
                }
            }
        });



        Añadir();
        cambia();
        Pan();
        Media();
        Entera();
        Adelante();
        Atras();


        inicialize();
        return root;
    }
    private void Añadir(){

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                    ID = mAuth.getUid();
                    final String key3 = mUsuario.push().getKey();
                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            double total = 0;
                            String processed = "";

                            if(Cambiar == true){
                                if (MediaoEntera == false) {
                                    if (contador2 == 1) {
                                        String texto = contador2 + " /" + " Vegetariana " + "/" + nombre;
                                        String precio = precios;
                                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                        mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                        mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                                    } else if (contador2 > 1) {
                                        String texto = contador2 + " /" + " Vegetariana " + "/" + nombre;
                                        double number = Double.valueOf(precios.replaceAll("[,.€]", ""));
                                        total = total + number * contador2;
                                        NumberFormat formatter = new DecimalFormat("###,##€");

                                        processed = formatter.format(total);

                                        String precio = processed;
                                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                        mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                        mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);
                                    }

                                } else if (MediaoEntera == true) {


                                    if (contador2 == 1) {
                                        String texto = contador2 + " /" + " Buey " + "/" + nombre;
                                        String precio = precios;
                                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                        mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                        mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                                    } else if (contador2 > 1) {
                                        String texto = contador2 + " /" + " Buey " + "/" + nombre;
                                        double number = Double.valueOf(precios.replaceAll("[,.€]", ""));
                                        total = total + number * contador2;
                                        NumberFormat formatter = new DecimalFormat("###,##€");

                                        processed = formatter.format(total);

                                        String precio = processed;
                                        MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                        mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                        mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);
                                    }

                                }
                                MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
                                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                                ftEs.replace(R.id.nav_host_fragment, fragment);
                                ftEs.addToBackStack(null);
                                ftEs.commit();
                            }else if (Cambiar == false) {
                                    if (TextUtils.isEmpty(nombrepan)) {
                                        Toast.makeText(getContext(), "Selecione Alguna Salsa", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        if (contador2 == 1) {
                                            String texto = contador2 + " /" + "/" + nombre + "/" + nombrepan;
                                            String precio = precios;
                                            MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                            mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                            mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                                        } else if (contador2 > 1) {
                                            String texto = contador2 + " /" + "/" + nombre + "/" + nombrepan;
                                            double number = Double.valueOf(precios.replaceAll("[,.€]", ""));
                                            total = total + number * contador2;
                                            NumberFormat formatter = new DecimalFormat("###,##€");

                                            processed = formatter.format(total);

                                            String precio = processed;
                                            MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                            mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                            mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);
                                        }

                                        MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
                                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                                        ftEs.replace(R.id.nav_host_fragment, fragment);
                                        ftEs.addToBackStack(null);
                                        ftEs.commit();

                                    }

                                }

                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }


        });


    }


    private void cambia(){
        if (Cambiar ==  false){
            Log.v("QUE PASAS AQUI", String.valueOf(Cambiar));
            cambiador.setText("Cambiar Hamburguesa");
            selecionarpan.setVisibility(View.VISIBLE);
            media.setVisibility(View.INVISIBLE);
            entera.setVisibility(View.INVISIBLE);
            mTosta.child("CIA").addValueEventListener(new ValueEventListener() {
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
            mTosta.child("CIA").addListenerForSingleValueEvent(new ValueEventListener() {
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



        }
        else if (Cambiar == true){
            selecionarpan.setVisibility(View.INVISIBLE);
            Log.v("QUE PASAS AQUI", String.valueOf(Cambiar));
            cambiador.setText("Cambiar CIA");
            media.setVisibility(View.VISIBLE);
            entera.setVisibility(View.VISIBLE);
            if (SelecionaPan == true) {
                if (MediaoEntera == false) {

                    mTosta.child("Burger").addValueEventListener(new ValueEventListener() {
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
                    mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
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

                    ID = mAuth.getUid();
                    mCompare.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue() );
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                            dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else{
                                    añadir.setVisibility(View.VISIBLE);
                                }
                                if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                            dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else{
                                    añadir.setVisibility(View.VISIBLE);
                                }
                                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                            dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else{
                                    añadir.setVisibility(View.VISIBLE);
                                }
                                if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                            dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                }
                                else{
                                    añadir.setVisibility(View.VISIBLE);
                                }
                            }

                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if (MediaoEntera == true) {

                    mTosta.child("Burger").addValueEventListener(new ValueEventListener() {
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
                    mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    ID = mAuth.getUid();
                    mCompare.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue() );
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                        dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                    añadir.setVisibility(View.INVISIBLE);
                                }
                            }
                            else{
                                añadir.setVisibility(View.VISIBLE);
                            }
                            if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                        dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                    añadir.setVisibility(View.INVISIBLE);
                                }
                            }
                            else{
                                añadir.setVisibility(View.VISIBLE);
                            }
                            if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                        dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                    añadir.setVisibility(View.INVISIBLE);
                                }
                            }
                            else{
                                añadir.setVisibility(View.VISIBLE);
                            }
                            if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                        dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                    añadir.setVisibility(View.INVISIBLE);
                                }
                            }
                            else{
                                añadir.setVisibility(View.VISIBLE);
                            }
                            if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                        dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())){

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
            }
            else if (SelecionaPan == false) {
                mTosta.child("TipoSalsas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            idpanes = dataSnapshot.getChildrenCount();
                            idpanes = 1;

                        }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {

                    }
                });
                mTosta.child("TipoSalsas").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            idpanes = 1;
                            nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                            barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                            imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                            nombreArticulo.setText(nombrepan);

                            Log.v("NONBRE ARTICULO ", nombrepan);
                            Log.v("MI ID ", String.valueOf(idpanes));

                        }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {

                    }
                });
                ID = mAuth.getUid();
                mCompare.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        Log.v("Es Trigo", dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("TipoPanes").child(String.valueOf(idpanes)).child("Alergia").getValue());
                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                    dataSnapshot.child("SandwichesyBocadillos").child("TipoPanes").child(String.valueOf(idpanes)).child("Alergia").getValue())) {
                                selecionarpan1.setVisibility(View.INVISIBLE);
                                añadir.setVisibility(View.INVISIBLE);
                            } else
                                selecionarpan1.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }
    }

    private void Pan(){

        selecionarpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.setVisibility(View.INVISIBLE);
                entera.setVisibility(View.INVISIBLE);
                precio.setVisibility(View.INVISIBLE);
                cambiador.setVisibility(View.INVISIBLE);
                selecionarpan.setVisibility(View.INVISIBLE);
                selecionarpan1.setVisibility(View.VISIBLE);
                SelecionaPan = false;
                añadir.setVisibility(View.INVISIBLE);
                Log.v("QUE PASAS AQUI SAOSA", String.valueOf(Cambiar));
                mTosta.child("TipoSalsas").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            idpanes = 1;
                            nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                            barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                            imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                            nombreArticulo.setText(nombrepan);

                            Log.v("NONBRE ARTICULO ", nombrepan);
                            Log.v("MI ID ", String.valueOf(idpanes));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        selecionarpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precio.setVisibility(View.VISIBLE);
                cambiador.setVisibility(View.VISIBLE);
                selecionarpan.setVisibility(View.VISIBLE);
                selecionarpan1.setVisibility(View.INVISIBLE);
                selecionarpan.setText(barra);
                SelecionaPan = true;
                añadir.setVisibility(View.VISIBLE);
                Log.v("QUE PASAS AQUI SAOSA", String.valueOf(Cambiar));
                    if (SelecionaPan == true) {
                        if (Cambiar == true) {
                            media.setVisibility(View.VISIBLE);
                            entera.setVisibility(View.VISIBLE);

                            if (MediaoEntera == false) {
                                mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
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
                                    public void onCancelled (@NonNull DatabaseError databaseError) {

                                    }
                                });

                                ID = mAuth.getUid();
                                mCompare.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                        Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue());
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                        dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                    añadir.setVisibility(View.INVISIBLE);
                                                }
                                            } else {
                                                añadir.setVisibility(View.VISIBLE);
                                            }
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                        dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                    añadir.setVisibility(View.INVISIBLE);
                                                }
                                            } else {
                                                añadir.setVisibility(View.VISIBLE);
                                            }
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                        dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                    añadir.setVisibility(View.INVISIBLE);
                                                }
                                            } else {
                                                añadir.setVisibility(View.VISIBLE);
                                            }
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                        dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                    añadir.setVisibility(View.INVISIBLE);
                                                }
                                            } else {
                                                añadir.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled (@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else if (MediaoEntera == true) {


                                mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
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
                                    public void onCancelled (@NonNull DatabaseError databaseError) {

                                    }
                                });
                                ID = mAuth.getUid();
                                mCompare.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                        Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue());

                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()) {
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            añadir.setVisibility(View.VISIBLE);
                                        }

                                    }

                                    @Override
                                    public void onCancelled (@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        } else if (Cambiar == false) {
                            media.setVisibility(View.INVISIBLE);
                            entera.setVisibility(View.INVISIBLE);
                            mTosta.child("CIA").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
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
                                public void onCancelled (@NonNull DatabaseError databaseError) {

                                }
                            });
                            ID = mAuth.getUid();
                            mCompare.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                    Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue());

                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()) {
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        añadir.setVisibility(View.VISIBLE);
                                    }

                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }


            }
        });
    }
    private void Media(){

                    media.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            media.setBackgroundResource(R.drawable.vegetariana);
                            entera.setBackgroundResource(R.drawable.entera);
                            MediaoEntera = false;
                            mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
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
                            ID = mAuth.getUid();
                            mCompare.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                    Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue() );
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        else{
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        else{
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        else{
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                        if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                    dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                                añadir.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        else{
                                            añadir.setVisibility(View.VISIBLE);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                }
    private void Entera(){

                    entera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            media.setBackgroundResource(R.drawable.entera);
                            entera.setBackgroundResource(R.drawable.buey);
                            MediaoEntera = true;
                            mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                        nombreArticulo.setText(nombre);
                                        precio.setText(precios);
                                        Log.v("NONBRE ARTICULO ", nombre);
                                        Log.v("MI ID " , String.valueOf(id));

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
                                    Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue() );

                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if( dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())){

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())){

                                            añadir.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                    if(dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()){
                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                                dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())){

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

                }
    private void Adelante(){

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (SelecionaPan == true) {
                                if (Cambiar == true) {
                                    if (MediaoEntera == false) {
                                        mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id++;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 6) {
                                                        id = 1;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue());
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    } else if (MediaoEntera == true) {

                                        mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id++;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 6) {
                                                        id = 1;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue());

                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }else if (Cambiar == false) {
                                        mTosta.child("CIA").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id++;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 3) {
                                                        id = 1;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue());
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                } else if (SelecionaPan == false) {

                                    mTosta.child("TipoSalsas").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                idpanes++;
                                                nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                                barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                                imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                                Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                                nombreArticulo.setText(nombrepan);

                                                if (idpanes == 8) {
                                                    idpanes = 1;
                                                    nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                                    barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                                    imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                                    nombreArticulo.setText(nombrepan);
                                                }
                                                Log.v("MI ID ", String.valueOf(idpanes));
                                                Log.v("NONBRE ARTICULO ", nombrepan);

                                            }
                                        }

                                        @Override
                                        public void onCancelled (@NonNull DatabaseError databaseError) {

                                        }
                                    });
                            }
                        }
                    });

                }
    private void Atras(){
                    boton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (SelecionaPan == true) {
                                if (Cambiar == true) {
                                    if (MediaoEntera == false) {
                                        mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id--;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 0) {
                                                        id = 5;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue());
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    } else if (MediaoEntera == true) {

                                        mTosta.child("Burger").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id--;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 0) {
                                                        id = 5;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").getValue());

                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Pescado").getValue().equals(
                                                            dataSnapshot.child("SandwichesyBocadillos").child("Bocadillos").child(String.valueOf(id)).child("Alergia").child("Pescado").getValue())) {

                                                        añadir.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    añadir.setVisibility(View.VISIBLE);
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }else if (Cambiar == false) {


                                        mTosta.child("CIA").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    id--;
                                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                    nombreArticulo.setText(nombre);
                                                    precio.setText(precios);

                                                    if (id == 0) {
                                                        id = 2;
                                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                                        nombreArticulo.setText(nombre);
                                                        precio.setText(precios);
                                                    }
                                                    Log.v("MI ID ", String.valueOf(id));
                                                    Log.v("NONBRE ARTICULO ", nombre);

                                                }
                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        ID = mAuth.getUid();
                                        mCompare.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                                Log.v("Es Lacteos", dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() + " // " + dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").getValue());
                                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Lacteos").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Trigo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Huevo").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Huevo").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").exists()) {
                                                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Moztaza").getValue().equals(
                                                                dataSnapshot.child("SandwichesyBocadillos").child("Sandwiches").child(String.valueOf(id)).child("Alergia").child("Moztaza").getValue())) {

                                                            añadir.setVisibility(View.INVISIBLE);
                                                        }
                                                    } else {
                                                        añadir.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled (@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                } else if (SelecionaPan == false) {

                                    mTosta.child("TipoSalsas").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                idpanes--;
                                                nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                                barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                                imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                                Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                                nombreArticulo.setText(nombrepan);

                                                if (idpanes == 0) {
                                                    idpanes = 7;
                                                    nombrepan = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                                    barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                                    imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                                    Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                                    nombreArticulo.setText(nombrepan);
                                                }
                                                Log.v("MI ID ", String.valueOf(idpanes));
                                                Log.v("NONBRE ARTICULO ", nombrepan);

                                            }
                                        }

                                        @Override
                                        public void onCancelled (@NonNull DatabaseError databaseError) {

                                        }
                                    });

                            }
                        }
                    });

                }


}

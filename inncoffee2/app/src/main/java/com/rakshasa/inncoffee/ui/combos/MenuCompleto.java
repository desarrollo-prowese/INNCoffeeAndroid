package com.rakshasa.inncoffee.ui.combos;

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

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.bebidas.Bebidas1;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosClass;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosSinFinalizarComidas;
import com.rakshasa.inncoffee.ui.quiero.Bebidas;
import com.rakshasa.inncoffee.ui.quiero.QuieroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MenuCompleto extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mPrecio;
    private DatabaseReference mBarra;
    private DatabaseReference mUsuario;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private int contador2 = 1;
    private String ID ;
    private ImageView menos,plus;
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
    private Button primero,segundo,bebida,postre;
    private TextView preciomenucompleto,numerocontador,añadir;
    private static final String USERS = "MisPedidos";
    private String precio,primer,segund,beb,post;
    public static boolean completo = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.menucompleto, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mPrecio = mDatabase.getReference("CombosPrecios");
        mBarra = mDatabase.getReference("Combos");
        Combos.menua = 1;
        mUsuario = mDatabase.getReference(USERS);
        menos = (ImageView)root.findViewById(R.id.menos);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        plus = (ImageView)root.findViewById(R.id.mas);
        primero = (Button) root.findViewById(R.id.primeroplato);
        segundo = (Button) root.findViewById(R.id.segundoplato);
        bebida = (Button) root.findViewById(R.id.elijebebida) ;
        postre = (Button) root.findViewById(R.id.elijepostre);
        numerocontador = (TextView) root.findViewById(R.id.numerocontador);
        preciomenucompleto = (TextView) root.findViewById(R.id.precio);
        numerocontador.setText(String.valueOf(contador2));
        añadir = (TextView) root.findViewById(R.id.añadir) ;
        primer = "";
        segund = "";
        beb = "";
        post = "";
        completo = true;
        MenuMedio.medio = false;
        Log.v("Que Menus COMPLETO  ", String.valueOf(completo));
        Log.v("Que Menus MEDIO  ", String.valueOf(MenuMedio.medio));


            añadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (TextUtils.isEmpty(primer) || TextUtils.isEmpty(segund) || TextUtils.isEmpty(beb) || TextUtils.isEmpty(post)) {
                        Toast.makeText(getContext(), "Selecione Todos Los Productos", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                    ID = mAuth.getUid();
                    final String key3 = mUsuario.push().getKey();
                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            double total = 0;
                            String processed = "";
                            if (contador2 == 1) {
                                String texto = contador2 + " /" + primero.getText() + "/" + segundo.getText() + "/" + bebida.getText() + "/" + postre.getText();
                                String precios = precio;
                                MisPedidosClass user2 = new MisPedidosClass(texto, precios);
                                mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                            } else if (contador2 > 1) {
                                String texto = contador2 + " /" + primero.getText() + "/" + segundo.getText() + "/" + bebida.getText() + "/" + postre.getText();
                                double number = Double.valueOf(precio.replaceAll("[,.€]", ""));
                                total = total + number * contador2;
                                NumberFormat formatter = new DecimalFormat("##,##€");

                                processed = formatter.format(total);

                                String precio = processed;
                                MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                mUsuario.child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                                mUsuario.child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);


                            }

                            ElimirBarra();

                            MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
                            FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                            ftEs.replace(R.id.nav_host_fragment, fragment);
                            ftEs.addToBackStack(null);
                            ftEs.commit();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });

                }
                }

            });


        SeleccionProductos();

        Contador();
        PrecioModificable();
        inicialize();
        Cambiodebarra();
        return root;
    }

    private void SeleccionProductos() {
         primero.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 CombosPlatos fragment = new CombosPlatos();
                 FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                 ftEs.replace(R.id.nav_host_fragment, fragment);
                 ftEs.addToBackStack(null);
                 ftEs.commit();
             }
         });

        segundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombosPlatos2 fragment = new CombosPlatos2();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });
        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bebidas fragment = new Bebidas();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });
        postre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombosPostre fragment = new CombosPostre();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });

    }
    private void ElimirBarra(){

        ID = mAuth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Combos").child("Primero").child(ID);
        ref.child("Texto").removeValue();
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Combos").child("Segundo").child(ID);
        ref1.child("Texto").removeValue();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Combos").child("Bebida").child(ID);
        ref2.child("Texto").removeValue();
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Combos").child("Postre").child(ID);
        ref3.child("Texto").removeValue();

    }
    private void Cambiodebarra(){
        ID = mAuth.getUid();
        mBarra.child("Primero").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                        String barra = dataSnapshot.child("Texto").getValue().toString();
                        primero.setText(barra);
                        primer = barra;

                }else{
                    primero.setText("Elije Primer Plato");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mBarra.child("Segundo").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String barra2 = dataSnapshot.child("Texto").getValue().toString();
                    segundo.setText(barra2);
                    segund = barra2;

                }else{

                    segundo.setText("Elije Segundo Plato");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mBarra.child("Bebida").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String barra3 = dataSnapshot.child("Texto").getValue().toString();
                    bebida.setText(barra3);
                    beb = barra3;

                }else{

                    bebida.setText("Elije Bebida");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mBarra.child("Postre").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String barra4 = dataSnapshot.child("Texto").getValue().toString();
                    postre.setText(barra4);
                    post = barra4;

                }else{

                    postre.setText("Elije Postre");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Contador(){

        if (contador2 == 1){
            menos.setVisibility(View.INVISIBLE);
        }
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2--;
                numerocontador.setText(String.valueOf(contador2));
                if (contador2 == 1){
                    menos.setVisibility(View.INVISIBLE);
                }
                else if (contador2 < 99 ){

                    plus.setVisibility(View.VISIBLE);
                }

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2++;
                numerocontador.setText(String.valueOf(contador2));
                if (contador2 == 99){
                    plus.setVisibility(View.INVISIBLE);
                    menos.setVisibility(View.VISIBLE);
                }
                else if (contador2 > 1){
                    menos.setVisibility(View.VISIBLE);

                }

            }
        });

    }
    private void PrecioModificable(){

        mPrecio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    precio = dataSnapshot.child("menucompleto").getValue().toString();
                    preciomenucompleto.setText(precio);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

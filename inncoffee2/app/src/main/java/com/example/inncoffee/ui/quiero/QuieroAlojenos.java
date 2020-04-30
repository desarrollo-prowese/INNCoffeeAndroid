package com.example.inncoffee.ui.quiero;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mispedidos.AdapterPedidos;
import com.example.inncoffee.ui.mispedidos.MisPedidosClass;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizar;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizarComidas;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizarMerienda;
import com.example.inncoffee.ui.tostadas.TostadasClasicas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroAlojenos extends Fragment {

    private ImageView trigo,cangrejo,huevo,pescado,cacahuete
            ,apio,soja,lacteos,frutosecos,
            dioxido,mostaza,semillas,moluscos,altramuces;

    private Button cartacomida, cartadesayuno,cartamerienda;



    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    public static boolean TengoComandaComidas = false;
    public static boolean TengoComandaDesayuno = false;
    public static boolean TengoComandaMerienda = false;
    private String ID ;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public  String alojeno;
    private DatabaseReference mUsuario;
    private DatabaseReference mUsu;
    public static int ComidaoDesayuno = 0;

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

        View root = inflater.inflate(R.layout.quieroalojenos, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        cartacomida = (Button) root.findViewById(R.id.alojenoscomidas);
        cartamerienda = (Button) root.findViewById(R.id.alojenomerienda);
        cartadesayuno = (Button) root.findViewById(R.id.alojernosdesayuno);
        trigo = (ImageView) root.findViewById(R.id.trigo) ;
        cangrejo = (ImageView) root.findViewById(R.id.cangrejo) ;
        huevo = (ImageView) root.findViewById(R.id.huevo) ;
        pescado = (ImageView) root.findViewById(R.id.pescado) ;
        cacahuete = (ImageView) root.findViewById(R.id.cacahuete) ;
        apio = (ImageView) root.findViewById(R.id.apio) ;
        soja = (ImageView) root.findViewById(R.id.soja) ;
        lacteos = (ImageView) root.findViewById(R.id.lacteos) ;
        frutosecos = (ImageView) root.findViewById(R.id.frutosecos) ;
        dioxido = (ImageView) root.findViewById(R.id.dioxidoysulfito) ;
        mostaza = (ImageView) root.findViewById(R.id.mostaza) ;
        semillas = (ImageView) root.findViewById(R.id.semillas) ;
        moluscos = (ImageView) root.findViewById(R.id.moluscos) ;
        altramuces = (ImageView) root.findViewById(R.id.altramuzes);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUsu = mDatabase.getReference("Users");
        Trigo();
        Cangrejo();
        Huevo();
        Pescado();
        Cacahuete();
        Apio();
        Soja();
        Lacteos();
        FrutosSecos();
        Dioxido();
        Moztaza();
        Semillas();
        Moluscos();
        Altramuces();
        if (TengoComandaMerienda){
            cartamerienda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizarMerienda fragment = new MisPedidosSinFinalizarMerienda();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }else {
            cartamerienda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaMerienda fragment = new CartaMerienda();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }




        if (TengoComandaDesayuno){
            cartadesayuno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizar fragment = new MisPedidosSinFinalizar();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });

        }
         else{

            cartadesayuno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaDesayunos fragment = new CartaDesayunos();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });
        }
        if (TengoComandaComidas){
            cartacomida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MisPedidosSinFinalizarComidas fragment = new MisPedidosSinFinalizarComidas();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();

                }
            });


        }
        else {

            cartacomida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartaComidas fragment = new CartaComidas();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
            });
        }



        Desayunos();
        Comidas();
        Merienda();
        inicialize();
        return root;
    }
    private void Desayunos() {
            ID = mAuth.getUid();
            Log.v("QUE es Desayuno :  ", String.valueOf(TengoComandaDesayuno));
            mDatabase.getReference("MisPedidos").child("PedidosSinFinalizar").child(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    TengoComandaDesayuno = dataSnapshot.exists();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    private void Merienda() {
        ID = mAuth.getUid();
        Log.v("QUE es Desayuno :  ", String.valueOf(TengoComandaDesayuno));
        mDatabase.getReference("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TengoComandaMerienda = dataSnapshot.exists();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void Comidas() {
            ID = mAuth.getUid();
            Log.v("QUE es Comidas :  ", String.valueOf(TengoComandaComidas));
            mDatabase.getReference("MisPedidos").child("PedidosSinFinalizarComidas").child(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    TengoComandaComidas = dataSnapshot.exists();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    private void Trigo (){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Trigo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    trigo.setTag("trigox");
                    trigo.setBackgroundResource(R.drawable.a04_);
                }else{
                    trigo.setTag("trigooriginal");
                    trigo.setBackgroundResource(R.drawable.a0_04);

                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });


        trigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (trigo.getTag().equals("trigooriginal")) {
                    ID = mAuth.getUid();
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Trigo").setValue("Trigo");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    trigo.setTag("trigox");
                    trigo.setBackgroundResource(R.drawable.a04_);


                }
                else if (trigo.getTag().equals("trigox")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Trigo").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    trigo.setTag("trigooriginal");
                    trigo.setBackgroundResource(R.drawable.a0_04);


                }

            }
        });

    }
    private void Cangrejo (){
    ID = mAuth.getUid();
    mUsu.child(ID).child("Alergias").child("Cangrejo").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                cangrejo.setTag("cangrejox");
                cangrejo.setBackgroundResource(R.drawable.a01_);
            }else{
                cangrejo.setTag("cangrejooriginal");
                cangrejo.setBackgroundResource(R.drawable.a0_01);

            }
        }

        @Override
        public void onCancelled (@NonNull DatabaseError databaseError) {

        }
    });
        cangrejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cangrejo.getTag().equals("cangrejooriginal")) {

                    ID = mAuth.getUid();
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Cangrejo").setValue("Cangrejo");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    cangrejo.setTag("cangrejox");
                    cangrejo.setBackgroundResource(R.drawable.a01_);
                }
                else if (cangrejo.getTag().equals("cangrejox")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Cangrejo").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    cangrejo.setTag("cangrejooriginal");
                    cangrejo.setBackgroundResource(R.drawable.a0_01);
                }

            }
        });


}
    private void Huevo(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Huevo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    huevo.setTag("huevox");
                    huevo.setBackgroundResource(R.drawable.a05_);
                }else{
                    huevo.setTag("huevooriginal");
                    huevo.setBackgroundResource(R.drawable.a0_05);

                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        huevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (huevo.getTag().equals("huevooriginal")) {
                    ID = mAuth.getUid();
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Huevo").setValue("Huevo");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    huevo.setTag("huevox");
                    huevo.setBackgroundResource(R.drawable.a05_);
                }
                else if (huevo.getTag().equals("huevox")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Huevo").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    huevo.setTag("huevooriginal");
                    huevo.setBackgroundResource(R.drawable.a0_05);
                }

            }
        });

    }
    private void Pescado(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Pescado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    pescado.setTag("pescadox");
                    pescado.setBackgroundResource(R.drawable.a06_);
                }else{
                    pescado.setTag("pescadooriginal");
                    pescado.setBackgroundResource(R.drawable.a0_06);

                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });

        pescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pescado.getTag().equals("pescadooriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Pescado").setValue("Pescado");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    pescado.setTag("pescadox");
                    pescado.setBackgroundResource(R.drawable.a06_);
                }
                else if (pescado.getTag().equals("pescadox")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Pescado").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    pescado.setTag("pescadooriginal");
                    pescado.setBackgroundResource(R.drawable.a0_06);
                }

            }
        });


    }
    private void Cacahuete(){
            ID = mAuth.getUid();
            mUsu.child(ID).child("Alergias").child("Cacahuete").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        cacahuete.setTag("cacahuetex");
                        cacahuete.setBackgroundResource(R.drawable.a10_);
                    }else{
                        cacahuete.setTag("cacahueteoriginal");
                        cacahuete.setBackgroundResource(R.drawable.a0_10);

                    }
                }

                @Override
                public void onCancelled (@NonNull DatabaseError databaseError) {

                }
            });
            cacahuete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (cacahuete.getTag().equals("cacahueteoriginal")) {

                            mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        mUsu.child(ID).child("Alergias").child("Cacahuete").setValue("Cacahuete");

                                    }
                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError databaseError) {

                                }
                            });

                        cacahuete.setTag("cacahuetex");
                        cacahuete.setBackgroundResource(R.drawable.a10_);
                    }
                    else if (cacahuete.getTag().equals("cacahuetex")) {

                        mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    mUsu.child(ID).child("Alergias").child("Cacahuete").removeValue();

                                }
                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError databaseError) {

                            }
                        });
                        cacahuete.setTag("cacahueteoriginal");
                        cacahuete.setBackgroundResource(R.drawable.a0_10);
                    }

                }
            });
        }
    private void Apio(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Apio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    apio.setTag("apiox");
                    apio.setBackgroundResource(R.drawable.a09_);
                }else{
                    apio.setTag("apiooriginal");
                    apio.setBackgroundResource(R.drawable.a0_09);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });

        apio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (apio.getTag().equals("apiooriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Apio").setValue("Apio");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    apio.setTag("apiox");
                    apio.setBackgroundResource(R.drawable.a09_);
                }
                else if (apio.getTag().equals("apiox")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Apio").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    apio.setTag("apiooriginal");
                    apio.setBackgroundResource(R.drawable.a0_09);
                }

            }
        });
    }
    private void Soja(){

        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Soja").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    soja.setTag("sojax");
                    soja.setBackgroundResource(R.drawable.a13_);
                }else{
                    soja.setTag("sojaoriginal");
                    soja.setBackgroundResource(R.drawable.a0_13);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        soja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (soja.getTag().equals("sojaoriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Soja").setValue("Soja");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });


                    soja.setTag("sojax");
                    soja.setBackgroundResource(R.drawable.a13_);
                }
                else if (soja.getTag().equals("sojax")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Soja").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    soja.setTag("sojaoriginal");
                    soja.setBackgroundResource(R.drawable.a0_13);
                }

            }
        });
    }
    private void Lacteos(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Lacteos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    lacteos.setTag("lacteosx");
                    lacteos.setBackgroundResource(R.drawable.a11_);
                }else{

                    lacteos.setTag("lacteosoriginal");
                    lacteos.setBackgroundResource(R.drawable.a0_11);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        lacteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (lacteos.getTag().equals("lacteosoriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Lacteos").setValue("Lacteos");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    lacteos.setTag("lacteosx");
                    lacteos.setBackgroundResource(R.drawable.a11_);
                }
                else if (lacteos.getTag().equals("lacteosx")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Lacteos").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    lacteos.setTag("lacteosoriginal");
                    lacteos.setBackgroundResource(R.drawable.a0_11);
                }

            }
        });
    }
    private void FrutosSecos(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("FrutosSecos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    frutosecos.setTag("frutosecosx");
                    frutosecos.setBackgroundResource(R.drawable.a03_);
                }else{

                    frutosecos.setTag("frutosecosoriginal");
                    frutosecos.setBackgroundResource(R.drawable.a0_03);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        frutosecos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (frutosecos.getTag().equals("frutosecosoriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("FrutosSecos").setValue("FrutosSecos");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    frutosecos.setTag("frutosecosx");
                    frutosecos.setBackgroundResource(R.drawable.a03_);
                }
                else if (frutosecos.getTag().equals("frutosecosx")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("FrutosSecos").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    frutosecos.setTag("frutosecosoriginal");
                    frutosecos.setBackgroundResource(R.drawable.a0_03);
                }

            }
        });

    }
    private void Dioxido(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Dioxido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    dioxido.setTag("dioxidox");
                    dioxido.setBackgroundResource(R.drawable.a12_);
                }else{

                    dioxido.setTag("dioxidooriginal");
                    dioxido.setBackgroundResource(R.drawable.a0_12);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        dioxido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dioxido.getTag().equals("dioxidooriginal")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Dioxido").setValue("Dioxido");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    dioxido.setTag("dioxidox");
                    dioxido.setBackgroundResource(R.drawable.a12_);
                }
                else if (dioxido.getTag().equals("dioxidox")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Dioxido").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    dioxido.setTag("dioxidooriginal");
                    dioxido.setBackgroundResource(R.drawable.a0_12);
                }

            }
        });

    }
    private void Moztaza(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Moztaza").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    mostaza.setTag("mostazax");
                    mostaza.setBackgroundResource(R.drawable.a08_);
                }else{

                    mostaza.setTag("mostazaoriginal");
                    mostaza.setBackgroundResource(R.drawable.a0_08);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        mostaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mostaza.getTag().equals("mostazaoriginal")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Moztaza").setValue("Moztaza");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    mostaza.setTag("mostazax");
                    mostaza.setBackgroundResource(R.drawable.a08_);
                }
                else if (mostaza.getTag().equals("mostazax")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Moztaza").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    mostaza.setTag("mostazaoriginal");
                    mostaza.setBackgroundResource(R.drawable.a0_08);
                }

            }
        });

    }
    private void Semillas(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Semillas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    semillas.setTag("semillasx");
                    semillas.setBackgroundResource(R.drawable.a02_);
                }else{

                    semillas.setTag("semillaoriginal");
                    semillas.setBackgroundResource(R.drawable.a0_02);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        semillas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (semillas.getTag().equals("semillaoriginal")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Semillas").setValue("Semillas");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    semillas.setTag("semillasx");
                    semillas.setBackgroundResource(R.drawable.a02_);
                }
                else if (semillas.getTag().equals("semillasx")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Semillas").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });


                    semillas.setTag("semillaoriginal");
                    semillas.setBackgroundResource(R.drawable.a0_02);
                }

            }
        });
    }
    private void Moluscos(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Moluscos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    moluscos.setTag("moluscosx");
                    moluscos.setBackgroundResource(R.drawable.a07_);
                }else{

                    moluscos.setTag("moluscosoriginal");
                    moluscos.setBackgroundResource(R.drawable.a0_07);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        moluscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (moluscos.getTag().equals("moluscosoriginal")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Moluscos").setValue("Moluscos");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    moluscos.setTag("moluscosx");
                    moluscos.setBackgroundResource(R.drawable.a07_);
                }
                else if (moluscos.getTag().equals("moluscosx")) {


                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Moluscos").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    moluscos.setTag("moluscosoriginal");
                    moluscos.setBackgroundResource(R.drawable.a0_07);
                }

            }
        });

    }
    private void Altramuces(){
        ID = mAuth.getUid();
        mUsu.child(ID).child("Alergias").child("Altramuces").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    altramuces.setTag("altramuzesx");
                    altramuces.setBackgroundResource(R.drawable.a14_);
                }else{

                    altramuces.setTag("altramuzesoriginal");
                    altramuces.setBackgroundResource(R.drawable.a0_14);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });
        altramuces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (altramuces.getTag().equals("altramuzesoriginal")) {

                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Altramuces").setValue("Altramuces");

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });
                    altramuces.setTag("altramuzesx");
                    altramuces.setBackgroundResource(R.drawable.a14_);
                }
                else if (altramuces.getTag().equals("altramuzesx")) {
                    mUsu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                mUsu.child(ID).child("Alergias").child("Altramuces").removeValue();

                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });

                    altramuces.setTag("altramuzesoriginal");
                    altramuces.setBackgroundResource(R.drawable.a0_14);
                }

            }
        });
    }

}


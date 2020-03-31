package com.example.inncoffee.ui.quiero;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroAlojenos extends Fragment {

    private ImageView trigo,cangrejo,huevo,pescado,cacahuete
            ,apio,soja,lacteos,frutosecos,
            dioxido,mostaza,semillas,moluscos,altramuces;

    private Button cartacomida, cartadesayuno;



    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
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
        MainActivity.mensajeToolbar.setText("QUIERO / NUEVO PEDIDO");

        cartacomida = (Button) root.findViewById(R.id.alojenoscomidas);
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
        altramuces = (ImageView) root.findViewById(R.id.altramuzes) ;


        trigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (trigo.getTag().equals("trigooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    trigo.setTag("trigox");
                    trigo.setBackgroundResource(R.drawable.a04_);
                }
                else if (trigo.getTag().equals("trigox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    trigo.setTag("trigooriginal");
                    trigo.setBackgroundResource(R.drawable.a0_04);
                }

            }
        });

        cangrejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cangrejo.getTag().equals("cangrejooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    cangrejo.setTag("cangrejox");
                    cangrejo.setBackgroundResource(R.drawable.a01_);
                }
                else if (cangrejo.getTag().equals("cangrejox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    cangrejo.setTag("cangrejooriginal");
                    cangrejo.setBackgroundResource(R.drawable.a0_01);
                }

            }
        });
        huevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (huevo.getTag().equals("huevooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    huevo.setTag("huevox");
                    huevo.setBackgroundResource(R.drawable.a05_);
                }
                else if (huevo.getTag().equals("huevox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    huevo.setTag("huevooriginal");
                    huevo.setBackgroundResource(R.drawable.a0_05);
                }

            }
        });
        pescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pescado.getTag().equals("pescadooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    pescado.setTag("pescadox");
                    pescado.setBackgroundResource(R.drawable.a06_);
                }
                else if (pescado.getTag().equals("pescadox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    pescado.setTag("pescadooriginal");
                    pescado.setBackgroundResource(R.drawable.a0_06);
                }

            }
        });

        cacahuete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cacahuete.getTag().equals("cacahueteoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    cacahuete.setTag("cacahuetex");
                    cacahuete.setBackgroundResource(R.drawable.a10_);
                }
                else if (cacahuete.getTag().equals("cacahuetex")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    cacahuete.setTag("cacahueteoriginal");
                    cacahuete.setBackgroundResource(R.drawable.a0_10);
                }

            }
        });
        apio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (apio.getTag().equals("apiooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    apio.setTag("apiox");
                    apio.setBackgroundResource(R.drawable.a09_);
                }
                else if (apio.getTag().equals("apiox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    apio.setTag("apiooriginal");
                    apio.setBackgroundResource(R.drawable.a0_09);
                }

            }
        });
        soja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (soja.getTag().equals("sojaoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    soja.setTag("sojax");
                    soja.setBackgroundResource(R.drawable.a13_);
                }
                else if (soja.getTag().equals("sojax")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    soja.setTag("sojaoriginal");
                    soja.setBackgroundResource(R.drawable.a0_13);
                }

            }
        });
        lacteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (lacteos.getTag().equals("lacteosoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    lacteos.setTag("lacteosx");
                    lacteos.setBackgroundResource(R.drawable.a11_);
                }
                else if (lacteos.getTag().equals("lacteosx")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    lacteos.setTag("lacteosoriginal");
                    lacteos.setBackgroundResource(R.drawable.a0_11);
                }

            }
        });
        frutosecos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (frutosecos.getTag().equals("frutosecosoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    frutosecos.setTag("frutosecosx");
                    frutosecos.setBackgroundResource(R.drawable.a03_);
                }
                else if (frutosecos.getTag().equals("frutosecosx")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    frutosecos.setTag("frutosecosoriginal");
                    frutosecos.setBackgroundResource(R.drawable.a0_03);
                }

            }
        });
        dioxido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dioxido.getTag().equals("dioxidooriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    dioxido.setTag("dioxidox");
                    dioxido.setBackgroundResource(R.drawable.a12_);
                }
                else if (dioxido.getTag().equals("dioxidox")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    dioxido.setTag("dioxidooriginal");
                    dioxido.setBackgroundResource(R.drawable.a0_12);
                }

            }
        });
        mostaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mostaza.getTag().equals("mostazaoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    mostaza.setTag("mostazax");
                    mostaza.setBackgroundResource(R.drawable.a08_);
                }
                else if (mostaza.getTag().equals("mostazax")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    mostaza.setTag("mostazaoriginal");
                    mostaza.setBackgroundResource(R.drawable.a0_08);
                }

            }
        });
        semillas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (semillas.getTag().equals("semillaoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    semillas.setTag("semillasx");
                    semillas.setBackgroundResource(R.drawable.a02_);
                }
                else if (semillas.getTag().equals("semillasx")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    semillas.setTag("semillaoriginal");
                    semillas.setBackgroundResource(R.drawable.a0_02);
                }

            }
        });
        moluscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (moluscos.getTag().equals("moluscosoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    moluscos.setTag("moluscosx");
                    moluscos.setBackgroundResource(R.drawable.a07_);
                }
                else if (moluscos.getTag().equals("moluscosx")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    moluscos.setTag("moluscosoriginal");
                    moluscos.setBackgroundResource(R.drawable.a0_07);
                }

            }
        });
        altramuces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (altramuces.getTag().equals("altramuzesoriginal")) {
                    Toast.makeText(getActivity(),"Probando 1", Toast.LENGTH_SHORT).show();
                    altramuces.setTag("altramuzesx");
                    altramuces.setBackgroundResource(R.drawable.a14_);
                }
                else if (altramuces.getTag().equals("altramuzesx")) {
                    Toast.makeText(getActivity(),"Probando 2", Toast.LENGTH_SHORT).show();
                    altramuces.setTag("altramuzesoriginal");
                    altramuces.setBackgroundResource(R.drawable.a0_14);
                }

            }
        });





        inicialize();
        return root;
    }


}

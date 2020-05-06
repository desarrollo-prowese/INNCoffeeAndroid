package com.example.inncoffee.ui.quiero;

import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroFragment extends Fragment {

    private Button Nuevopedido,pedidofinalizado,pedidofinalizado2,pedidofinalizado3,mispedidos;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String ID ;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    public static boolean TengoPedidoSinFinalizar = false;
    public static boolean TengoPedidoSinFinalizarComidas = false;
    public static boolean TengoPedidoSinFinalizarMerienda = false;
    public static boolean TengoPedidoSin = false;
    public static boolean TengoPedidoSinComidas = false;
    public static boolean TengoPedidoSinMerienda = false;
    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
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
        MainActivity.mensajeToolbar.setText("PEDIDO");
        Nuevopedido = (Button) root.findViewById(R.id.buttonNuevoPedido);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        ID = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mispedidos = (Button) root.findViewById(R.id.buttonMisPedidos);
        pedidofinalizado = (Button) root.findViewById(R.id.pedidocomida);
        pedidofinalizado2 = (Button) root.findViewById(R.id.pedidodesayuno);
        pedidofinalizado3 = (Button) root.findViewById(R.id.pedidomerienda);
        mispedidos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Proximamente", Toast.LENGTH_SHORT).show();
             /*   MisPedidos fragment = new MisPedidos();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();*/
            }

        });
        Nuevopedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(),"Proximamente", Toast.LENGTH_SHORT).show();
                    QuieroNuevoPedido fragment = new QuieroNuevoPedido();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit(); }
            });

        pedidofinalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setMessage("Deseas Borrar La Comanda Pendiente y Finalizar La Comanda Pendiente");
                dialogo1.setCancelable(false);

                dialogo1.setPositiveButton("Finalizar Comanda", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        FinalizarPedidoComidas fragment = new FinalizarPedidoComidas();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosSinFinalizar").child(ID);
                        ref.removeValue();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });
        pedidofinalizado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setMessage("Deseas Borrar La Comanda Pendiente y Finalizar La Comanda Pendiente");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Finalizar Comanda", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FinalizarPedido fragment = new FinalizarPedido();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosSinFinalizarComidas").child(ID);
                        ref1.removeValue();
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

        pedidofinalizado3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setMessage("Deseas Borrar La Comanda Pendiente y Finalizar La Comanda Pendiente");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Finalizar Comanda", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FinalizarPedido fragment = new FinalizarPedido();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID);
                        ref1.removeValue();
                    }
                });

                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

        if (TengoPedidoSinFinalizar){
            pedidofinalizado2.setVisibility(View.VISIBLE);
        }else {
            pedidofinalizado2.setVisibility(View.INVISIBLE);
        }

        if (TengoPedidoSinFinalizarComidas){
            pedidofinalizado.setVisibility(View.VISIBLE);
        }else {
            pedidofinalizado.setVisibility(View.INVISIBLE);
        }
        if (TengoPedidoSinFinalizarMerienda){
            pedidofinalizado3.setVisibility(View.VISIBLE);
        }else {
            pedidofinalizado3.setVisibility(View.INVISIBLE);
        }




        Finalizado1();
        Finalizado();
        Finalizado3();
        HomeFragment1.num = 1;
        inicialize();
        return root;
    }

    private void Finalizado(){

        Log.v("QUE es Desayuno :  ", String.valueOf(TengoPedidoSinFinalizar));
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

    private void Finalizado3(){

        Log.v("QUE es Desayuno :  ", String.valueOf(TengoPedidoSinFinalizarMerienda));
        mDatabase.getReference("MisPedidos").child("PedidosFinalizadosMerienda").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TengoPedidoSinFinalizarMerienda = dataSnapshot.exists();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void Finalizado1(){
        Log.v("QUE es Comidas :  ", String.valueOf(TengoPedidoSinFinalizarComidas));
        mDatabase.getReference("MisPedidos").child("PedidosFinalizadosComidas").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    TengoPedidoSinFinalizarComidas = dataSnapshot.exists();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

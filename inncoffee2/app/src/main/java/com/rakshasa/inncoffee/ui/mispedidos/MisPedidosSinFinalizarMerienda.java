package com.rakshasa.inncoffee.ui.mispedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.combos.Combos;
import com.rakshasa.inncoffee.ui.mensajes.AdapterMensaje;
import com.rakshasa.inncoffee.ui.mensajes.MensajesClass;
import com.rakshasa.inncoffee.ui.mensajes.MensajesFragment;
import com.rakshasa.inncoffee.ui.ofertas.AdapterOfertas;
import com.rakshasa.inncoffee.ui.quiero.Bebidas;
import com.rakshasa.inncoffee.ui.quiero.FinalizarPedido;
import com.rakshasa.inncoffee.ui.quiero.FinalizarPedidoMerienda;
import com.rakshasa.inncoffee.ui.quiero.QuieroAlojenos;
import com.rakshasa.inncoffee.ui.quiero.QuieroFragment;
import com.rakshasa.inncoffee.ui.quiero.Tostadas;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MisPedidosSinFinalizarMerienda extends DialogFragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private ArrayList<MisPedidosClass> mMensaje = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private AdapterPedidos mAdapter;
    private RecyclerView mPedidos;
    private TextView sumatotal,finalizar,seguir;
    private String ID ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    private static final String USERS = "MisPedidos";
    private String texto,precios;
    private AlertDialog.Builder dialogo2;





    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getActivity(), QuieroFragment.class);
                    startActivity(intent);
                //    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
              //      Log.w("TAG", "onAuthStateChanged - Cerro sesion");
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


                //    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
              //      Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.mispedidossinfinalizarmerienda, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mPedidos = (RecyclerView) root.findViewById(R.id.mispedidossinfinalizar);
        QuieroAlojenos.ComidaoDesayuno = 0;
        mPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance();
        sumatotal = (TextView) root.findViewById(R.id.total2);
        finalizar = (TextView) root.findViewById(R.id.finalizar);
        seguir = (TextView) root.findViewById(R.id.Seguir);
        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        getMensajesFromFirebase();
        inicialize();

        seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                dismiss();
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo2 = new AlertDialog.Builder(getContext());
                dialogo2.setMessage("");
                dialogo2.setCancelable(false);
                dialogo2.setPositiveButton("Recoger en local", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {
                        FinalizarPedidoMerienda fragment = new FinalizarPedidoMerienda();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        dismiss();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID);
                        ref.removeValue();
                        MisPedidosSinFinalizar.Lugar = "Recoger en local";

                    }
                });
                dialogo2.setNegativeButton("Llevar a mi oficina", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo2, int id) {
                        PedidoEnviarMerienda dialog = new PedidoEnviarMerienda();
                        dialog.show(getParentFragmentManager(), "Dialog" );
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosSinFinalizarMerienda").child(ID);
                        ref.removeValue();
                        dismiss();

                    }
                });
                dialogo2.show();


            }
        });

        return root;
    }


    public void removeItem(int position) {
        mAdapter.deleteItemMerienda(position);
        dismiss();

    }

    private void getMensajesFromFirebase() {

        ID = mAuth.getUid();
        mUsuario.child("PedidosSinFinalizarMerienda").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double total = 0;
                String processed = "";
                finalizar.setVisibility(View.INVISIBLE);
                if (dataSnapshot.exists()) {

                    finalizar.setVisibility(View.VISIBLE);
                    mMensaje.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        double number = Double.parseDouble(ds.child("precio").getValue(String.class).replaceAll("[.,€]", ""));
                        total = total + number;

                        NumberFormat formatter = new DecimalFormat("0,00");

                        processed = formatter.format(total);

                        sumatotal.setText(processed);
                    //    Log.v("PRecio ", processed + " ///"+ total );
                        texto = ds.child("texto").getValue().toString();
                        precios = ds.child("precio").getValue().toString();

                        mMensaje.add(new MisPedidosClass(texto,precios));
                        keys.add(ds.getKey());

                    }


                    mAdapter = new AdapterPedidos(getContext(), keys, mMensaje, R.layout.contenido_mispedidos);
                    mPedidos.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new AdapterPedidos.OnItemClickListener() {

                        @Override
                        public void onItemClick(final int position) {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext(), position);
                            dialogo1.setMessage("Deseas Borrar esta Linea");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    removeItem(position);
                                }
                            });
                            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    dialogo1.cancel();
                                }
                            });
                            dialogo1.show();

                        }

                    });
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

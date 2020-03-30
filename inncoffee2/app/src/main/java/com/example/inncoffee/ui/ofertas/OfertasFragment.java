package com.example.inncoffee.ui.ofertas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OfertasFragment extends Fragment {

    private AdapterOfertas mAdapter;
    private RecyclerView mRecycle;
    private ArrayList<OfertasClass> mOfertas = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getActivity(), MensajesFragment.class);
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


                    Intent intent = new Intent(getActivity(), MensajesFragment.class);
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

        View root = inflater.inflate(R.layout.ofertas_fragment, container, false);
        MainActivity.mensajeToolbar.setText("MIS OFERTAS");

        mRecycle = (RecyclerView) root.findViewById(R.id.listaofertas);

        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        inicialize();

        getOfertasFromFirebase();
        return root;
    }


    public void removeItem(int position) {
        mAdapter.deleteItem(position);
        OfertasFragment fragment = new OfertasFragment();
        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
        ftEs.replace(R.id.nav_host_fragment, fragment);
        ftEs.addToBackStack(null);
        ftEs.commit();


    }

    private void getOfertasFromFirebase() {
        mDatabase.child("Ofertas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    mOfertas.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String ofertas = ds.child("ofertas").getValue().toString();
                        String porcentaje = ds.child("porcentaje").getValue().toString();


                        Log.v("Probando ", ofertas + porcentaje);
                        mOfertas.add(new OfertasClass(ofertas,porcentaje));
                        keys.add(ds.getKey());

                    }


                    mAdapter = new AdapterOfertas(getContext(), mOfertas, keys, R.layout.contenido_ofertas);
                    mRecycle.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new AdapterOfertas.OnItemClickListener() {

                        @Override
                        public void onItemClick(final int position) {

                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity(), position);
                            dialogo1.setMessage("Deseas activar esta oferta");
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

package com.rakshasa.inncoffee.ui.mensajes;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.home.HomeFragment1;
import com.rakshasa.inncoffee.ui.quiero.QuieroNuevoPedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MensajesFragment extends Fragment {



    private AdapterMensaje mAdapter;
    private RecyclerView mRecycle;
    private ArrayList<MensajesClass> mMensaje = new ArrayList<>();
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
                   // Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    //Log.w("TAG", "onAuthStateChanged - Cerro sesion");
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


                   // Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                  //  Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mensajes, container, false);
        MainActivity.mensajeToolbar.setText("PROMOCIONES / MIS MENSAJES");
        mRecycle = (RecyclerView) root.findViewById(R.id.listamensajes);

        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        inicialize();
        getMensajesFromFirebase();
        HomeFragment1.num = 1;

      return root;
    }
    public void removeItem(int position) {
        mAdapter.deleteItem(position);
        MensajesFragment fragment = new MensajesFragment();
        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
        ftEs.replace(R.id.nav_host_fragment, fragment);
        ftEs.addToBackStack(null);
        ftEs.commit();


    }

    private void getMensajesFromFirebase() {
        mDatabase.child("Mensajes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {


                        mMensaje.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {


                            String texto = ds.child("texto").getValue().toString();
                            mMensaje.add(new MensajesClass(texto));
                            keys.add(ds.getKey());

                        }




                        mAdapter = new AdapterMensaje(getContext(), mMensaje, keys, R.layout.contenido_mensaje);
                        mAdapter.setOnItemClickListener(new AdapterMensaje.OnItemClickListener() {

                            @Override
                            public void onItemClick(int position) {
                                removeItem(position);
                            }

                        });
                        mRecycle.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}

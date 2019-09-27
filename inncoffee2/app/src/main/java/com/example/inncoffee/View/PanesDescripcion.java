package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.Model.Panes;
import com.example.inncoffee.R;
import com.example.inncoffee.ViewHolder.ObjetosViewHolderPanes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

//import com.example.inncoffee.ViewHolder.ObjetosViewHolder;

public class PanesDescripcion extends AppCompatActivity {
    public String paso;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    Button seleccionar;
    FirebaseRecyclerOptions<Panes> options;
    FirebaseRecyclerAdapter<Panes, ObjetosViewHolderPanes> adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_objetos);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        recyclerview = findViewById(R.id.rViewObjetos);
        recyclerview.setHasFixedSize(true);
        seleccionar = findViewById(R.id.buttonSelec);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Desayuno").child("Tipos_Pan");
        options = new FirebaseRecyclerOptions.Builder<Panes>().setQuery(databaseReference, Panes.class).build();
        adapter = new FirebaseRecyclerAdapter<Panes, ObjetosViewHolderPanes>(options) {
            @Override
            protected void onBindViewHolder(final ObjetosViewHolderPanes objetosViewHolder, int i, final Panes PanesDescripcion) {
                Picasso.get().load(PanesDescripcion.getImagelink()).into(objetosViewHolder.Imagen, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                objetosViewHolder.titulo.setText(PanesDescripcion.getNombre());
                objetosViewHolder.btSelec.setOnClickListener(new View.OnClickListener() {
                    public void onClick(final View v) {
                        String uid = mAuth.getCurrentUser().getUid();
                        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String Alergias1;

                                Alergias1 = dataSnapshot.child("Alergias").getValue().toString();


                                //System.out.println("afjasfasjf"+ Alergias);
                                if (TostadasClasicas.tostada == "1") {
                                    Tostadas cambio = new Tostadas();
                                    cambio.tipo_pan = PanesDescripcion.getNombre();
                                    Log.e("Nombre de usuario: ", "" + Alergias1);
                                    Intent intent2 = new Intent(v.getContext(), TostadasClasicas.class);
                                    intent2.putExtra("variable_string1", PanesDescripcion.getNombre());
                                    intent2.putExtra("variable_string", Alergias1);
                                    startActivityForResult(intent2, 0);
                                } else if (TostadasClasicas.tostada == "2") {
                                    Tostadas cambio = new Tostadas();
                                    cambio.tipo_pan = PanesDescripcion.getNombre();
                                    Log.e("Nombre de usuario: ", "" + Alergias1);
                                    Intent intent2 = new Intent(v.getContext(), Tostadas.class);
                                    intent2.putExtra("variable_string1", PanesDescripcion.getNombre());
                                    intent2.putExtra("variable_string", Alergias1);
                                    startActivityForResult(intent2, 0);

                                } else if (TostadasClasicas.tostada == "3") {
                                    Tostadas cambio = new Tostadas();
                                    cambio.tipo_pan = PanesDescripcion.getNombre();
                                    Log.e("Nombre de usuario: ", "" + Alergias1);
                                    Intent intent2 = new Intent(v.getContext(), TostadasOriginales.class);
                                    intent2.putExtra("variable_string1", PanesDescripcion.getNombre());
                                    intent2.putExtra("variable_string", Alergias1);
                                    startActivityForResult(intent2, 0);

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }

                        });

                    }
                });
            }


            @Override
            public ObjetosViewHolderPanes onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_tipo_panes, parent, false);

                return new ObjetosViewHolderPanes(view);
            }
        };

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerview.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }
}

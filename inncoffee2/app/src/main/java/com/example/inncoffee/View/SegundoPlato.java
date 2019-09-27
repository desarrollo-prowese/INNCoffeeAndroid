package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.Model.Objetos;
import com.example.inncoffee.R;
import com.example.inncoffee.ViewHolder.ObjetosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegundoPlato extends AppCompatActivity {
    static String SegundoPlato = "Elige Segundo Plato";
    public String paso;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Objetos> options;
    FirebaseRecyclerAdapter<Objetos, ObjetosViewHolder> adapter;

    //st String recuperamos_variable_string
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_objetos);
        recyclerview = findViewById(R.id.rViewObjetos);
        recyclerview.setHasFixedSize(true);
        // = getIntent().getStringExtra("variable_string");
        final String recuperamos_variable_string = getIntent().getStringExtra("variable_string");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("comida").child("platosTapas");
        options = new FirebaseRecyclerOptions.Builder<Objetos>().setQuery(databaseReference, Objetos.class).build();
        adapter = new FirebaseRecyclerAdapter<Objetos, ObjetosViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ObjetosViewHolder objetosViewHolder, int i, Objetos objetos) {
                System.out.println(objetos.getAlergia());

                List<String> myList = new ArrayList<String>(Arrays.asList(recuperamos_variable_string.split(",")));
                System.out.println(myList.toString());
                boolean funciona = true;
                for (String item : myList) {
                    if (!objetos.getAlergia().contains(item)) {
                        Picasso.get().load(objetos.getImagelink()).into(objetosViewHolder.Imagen, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        objetosViewHolder.titulo.setText(objetos.getNombre());
                        objetosViewHolder.Precio.setText(objetos.getPrecio());

                    } else {
                        Picasso.get().load(R.drawable.error).into(objetosViewHolder.Imagen, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        objetosViewHolder.titulo.setText(objetos.getNombre());
                        objetosViewHolder.Precio.setText(objetos.getPrecio());

                    }

                }
                objetosViewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(view.getContext(), Menu_Completo.class);
                        SegundoPlato = objetosViewHolder.titulo.getText().toString();
                        startActivity(intent2);

                    }
                });


            }

            @Override
            public ObjetosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bebidas_rc_3, parent, false);

                return new ObjetosViewHolder(view);
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




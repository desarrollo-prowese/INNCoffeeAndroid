package com.example.inncoffee.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.Model.Objetos_Pedidos;
import com.example.inncoffee.R;
import com.example.inncoffee.ViewHolder.ObjetosViewHolderPedidos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Ver_Pedidos extends AppCompatActivity {
    static ArrayList<String> aListNumbers;
    static ArrayList<String> aListNumbers1;
    public String paso;
    // private Fragment activity;
    ListView list;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Objetos_Pedidos> options;
    FirebaseRecyclerAdapter<Objetos_Pedidos, ObjetosViewHolderPedidos> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth mAuth;
    //st String recuperamos_variable_string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.vista_recyclerview_pedidos);
        recyclerview = findViewById(R.id.recyclerPedidos);
        recyclerview.setHasFixedSize(true);
        String uid = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("pedidos");
        options = new FirebaseRecyclerOptions.Builder<Objetos_Pedidos>().setQuery(databaseReference, Objetos_Pedidos.class).build();
        adapter = new FirebaseRecyclerAdapter<Objetos_Pedidos, ObjetosViewHolderPedidos>(options) {
            @Override
            protected void onBindViewHolder(final ObjetosViewHolderPedidos ObjetosViewHolderPedidos, int i, Objetos_Pedidos objetos) {

                boolean funciona = true;

                ObjetosViewHolderPedidos.precioPedido.setText(objetos.getTotal());
                ObjetosViewHolderPedidos.fechaPedido.setText(objetos.getFecha());

                String[] str = objetos.getNombre().split(",");
                //List<String> list = Arrays.asList(str);
                aListNumbers = new ArrayList<String>(Arrays.asList(str));
                String[] str1 = objetos.getPrecio().split(",");
                System.out.println("ARRRRRAAYY" + aListNumbers.toString());
                aListNumbers1 = new ArrayList<String>(Arrays.asList(str1));

                ObjetosViewHolderPedidos.Nombre.setText(objetos.getNombre() + "\r\n");
                ObjetosViewHolderPedidos.Precio.setText(objetos.getPrecio() + "\r\n");


            }

            @Override
            public ObjetosViewHolderPedidos onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_pedidos_clientes, parent, false);


                return new ObjetosViewHolderPedidos(view);
            }
        };
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
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

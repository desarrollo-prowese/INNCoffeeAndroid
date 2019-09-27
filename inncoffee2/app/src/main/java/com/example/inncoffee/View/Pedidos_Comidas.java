package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.Adapter.AdapterItem;
import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Pedidos_Comidas extends AppCompatActivity {
    static ArrayList<String> titulo = new ArrayList<>();
    static ArrayList<String> Precio = new ArrayList<>();
    ListView list;
    TextView Comida;
    TextView precioTotal;
    Button finalizarPedido;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.vista_pedido_comidas);
        // Tostadas = (TextView) findViewById(R.id.textViewContinuaComida);
        Comida = findViewById(R.id.textViewContinuaComida);
        precioTotal = findViewById(R.id.textViewPtotal);
        finalizarPedido = findViewById(R.id.buttonFinalizarPedido);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String rTitulo = extras.getString("variable_stringComidaTitulo");
        String rPrecio = extras.getString("variable_stringComidaPrecio");
        //final String rAlergenos=extras.getString("variable_stringAlergenos");
        //double valor = Double.parseDouble(rPrecio);
        titulo.add(rTitulo);
        Precio.add(rPrecio);
        double sum = 0;
        for (int i = 0; i < Precio.size(); i++) {
            sum = sum + Double.parseDouble(Precio.get(i));
        }


        System.out.println(sum);
        String sumaTotal = valueOf(sum);
        precioTotal.setText(sumaTotal);
        AdapterItem adapter = new AdapterItem(this, titulo, Precio);
        list = findViewById(R.id.listView);
        list.setAdapter(adapter);


        Comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Comidas.class);
                startActivity(intent2);
            }
        });
        finalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).child("pedidos").limitToLast(1).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ultimoCliente = dataSnapshot.getKey();

                        String key = mDatabase.child("Users").child(uid).child("pedidos").push().getKey();

                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + ultimoCliente);
                        Intent intent3 = new Intent(view.getContext(), Finalizar_Pedido_Comida.class);
                        intent3.putExtra("variable_string", key);
                        startActivityForResult(intent3, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    //code specific to first list item
                    // Toast.makeText(getApplicationContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    //code specific to 2nd list item
                    //Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                } else if (position == 2) {

                    // Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                } else if (position == 3) {

                    //Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
                } else if (position == 4) {

                    //Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

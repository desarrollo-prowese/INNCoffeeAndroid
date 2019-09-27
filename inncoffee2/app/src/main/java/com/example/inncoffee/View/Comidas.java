package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Comidas extends AppCompatActivity {
    static String recuperamos_variable_stringPT;
    static String bebidas_comida = "0";
    private TextView combos;
    private TextView sandwich;
    private TextView platosytapas;
    private TextView postres;
    private TextView bebidas;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.view_comida);
        combos = findViewById(R.id.textViewCombo);
        sandwich = findViewById(R.id.textViewSandwich);
        platosytapas = findViewById(R.id.textViewPlatos);
        postres = findViewById(R.id.textViewPostres);
        bebidas = findViewById(R.id.textViewBebidas);

        combos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Combos.class);

                startActivity(intent2);
            }
        });
        sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias2;

                        Alergias2 = dataSnapshot.child("Alergias").getValue().toString();


                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias2);
                        Intent intent4 = new Intent(view.getContext(), Sandwich.class);
                        intent4.putExtra("variable_string", Alergias2);
                        startActivityForResult(intent4, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });
            }
        });
        platosytapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias2;

                        Alergias2 = dataSnapshot.child("Alergias").getValue().toString();


                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias2);
                        Intent intent4 = new Intent(view.getContext(), PlatosyTapas.class);
                        intent4.putExtra("variable_string", Alergias2);
                        startActivityForResult(intent4, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });

            }
        });
        postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias2;

                        Alergias2 = dataSnapshot.child("Alergias").getValue().toString();


                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias2);
                        Intent intent4 = new Intent(view.getContext(), Postres.class);
                        intent4.putExtra("variable_string", Alergias2);
                        startActivityForResult(intent4, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Tipo_Bebidas.class);
                bebidas_comida = "1";
                startActivity(intent2);
            }
        });
    }
}

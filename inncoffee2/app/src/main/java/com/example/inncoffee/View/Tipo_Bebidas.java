package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tipo_Bebidas extends AppCompatActivity {
    static String recuperamos_variable_string;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ImageView cafe;
    private ImageView te;
    private ImageView bebidas;
    private ImageView zumos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.vista_elegirbebida);
        cafe = findViewById(R.id.imageViewCafes);
        te = findViewById(R.id.imageViewTe);
        bebidas = findViewById(R.id.imageViewBebidas);
        zumos = findViewById(R.id.imageViewZumos);
        recuperamos_variable_string = getIntent().getStringExtra("variable_string");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //final String rAlergias = extras.getString("variable_string1");


        System.out.println(recuperamos_variable_string);
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias1;

                        Alergias1 = dataSnapshot.child("Alergias").getValue().toString();


                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias1);
                        Intent intent2 = new Intent(view.getContext(), Bebidas.class);
                        intent2.putExtra("variable_string", Alergias1);
                        startActivityForResult(intent2, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });

            }
        });
        zumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias;

                        Alergias = dataSnapshot.child("Alergias").getValue().toString();


                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias);
                        Intent intent3 = new Intent(view.getContext(), Zumos.class);
                        intent3.putExtra("variable_string", Alergias);
                        startActivityForResult(intent3, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

            }
        });
        cafe.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent4 = new Intent(view.getContext(), Cafes.class);
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
        te.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent4 = new Intent(view.getContext(), Te.class);
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

    }
}

package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Medio_Menu extends AppCompatActivity {
    static String medioMenu = "0";
    private Button primerPlato;
    private Button bebida;
    private Button postre;
    private Button anadirCombo;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.vista_medio_menu);
        primerPlato = findViewById(R.id.buttonPplatoMedio);
        bebida = findViewById(R.id.buttonBebidaMedio);
        postre = findViewById(R.id.buttonPostreMedio);
        anadirCombo = findViewById(R.id.buttonAnadirCMedio);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //  String rTitulo = extras.getString("variable_stringNombre");
        //  String sPlato = extras.getString("variable_stringSPlato");

        primerPlato.setText(PrimerPlato.PrimerPlato);
        postre.setText(Postres.Postre);
        bebida.setText(Bebidas.EligeBebida);
        primerPlato.setOnClickListener(new View.OnClickListener() {
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
                        medioMenu = "1";
                        Intent intent2 = new Intent(view.getContext(), PrimerPlato.class);
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

        bebida.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent2 = new Intent(view.getContext(), Tipo_Bebidas.class);
                        intent2.putExtra("variable_string", Alergias1);
                        Menu_Completo.bebidaMenuComleto = "2";
                        startActivityForResult(intent2, 0);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });
            }
        });
        postre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String uid = mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Alergias1;
                        Alergias1 = dataSnapshot.child("Alergias").getValue().toString();

                        Menu_Completo.postreMenuCompleto = "2";
                        //System.out.println("afjasfasjf"+ Alergias);
                        Log.e("Nombre de usuario: ", "" + Alergias1);
                        Intent intent2 = new Intent(view.getContext(), Postres.class);
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
        anadirCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Pedidos_Comidas.class);
                Bundle extras = new Bundle();

                extras.putString("variable_stringComidaTitulo", primerPlato.getText().toString() + " / " + bebida.getText().toString() + " / " + postre.getText().toString());
                extras.putString("variable_stringComidaPrecio", "7.80");
                intent2.putExtras(extras);
                startActivity(intent2);
            }
        });
    }
}

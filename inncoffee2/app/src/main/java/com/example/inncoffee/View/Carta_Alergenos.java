package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carta_Alergenos extends AppCompatActivity {
    private Button gluten;
    private Button crustaceos;
    private Button huevo;
    private Button pescado;
    private Button cacahuete;
    private Button apio;
    private Button soja;
    private Button lacteos;
    private Button frutosSecos;
    private Button dioxido;
    private Button mostaza;
    private Button sesamo;
    private Button moluscos;
    private Button altramuces;
    private Button desayunos;
    private Button comidas;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_alergia);
        mAuth = FirebaseAuth.getInstance();
        final ArrayList<String> alergia = new ArrayList<>();
        gluten = findViewById(R.id.buttonGluten);
        crustaceos = findViewById(R.id.buttonCrustaceos);
        huevo = findViewById(R.id.buttonHuevos);
        pescado = findViewById(R.id.buttonPescado);
        cacahuete = findViewById(R.id.buttonCacahuetes);
        apio = findViewById(R.id.buttonApio);
        soja = findViewById(R.id.buttonSoja);
        lacteos = findViewById(R.id.buttonLacteos);
        frutosSecos = findViewById(R.id.buttonFrutosSecos);
        dioxido = findViewById(R.id.buttonDioxido);
        mostaza = findViewById(R.id.buttonMostaza);
        sesamo = findViewById(R.id.buttonSesamo);
        moluscos = findViewById(R.id.buttonMoluscos);
        altramuces = findViewById(R.id.buttonAltramuces);
        desayunos = findViewById(R.id.buttonDesayuno);
        comidas = findViewById(R.id.buttonComidas);

        gluten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("gluten");
            }
        });
        crustaceos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("crustaceos");
            }
        });
        huevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("huevo");
            }
        });
        pescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("pescado");
            }
        });
        cacahuete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("cacahuetes");
            }
        });
        apio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("apio");
            }
        });
        soja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("soja");
            }
        });
        lacteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("lacteos");
            }
        });
        gluten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("gluten");
            }
        });
        frutosSecos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("frutos secos");
            }
        });
        dioxido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("dioxido de azufre y sulfitos");
            }
        });
        mostaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("mostaza");
            }
        });
        sesamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("sesamo");
            }
        });
        moluscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("moluscos");
            }
        });
        altramuces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alergia.add("altramuces");
            }
        });
        desayunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(alergia.get(0));
                Intent intent2 = new Intent(view.getContext(), Carta_Desayuno.class);
                String res = String.join(",", alergia);
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                Map newPost = new HashMap();
                newPost.put("Alergias", res);
                current_user_db.updateChildren(newPost);
                intent2.putExtra("variable_string", res);
                startActivityForResult(intent2, 0);
            }
        });
        comidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(alergia.get(0));
                Intent intent2 = new Intent(view.getContext(), Comidas.class);
                String res = String.join(",", alergia);
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                Map newPost = new HashMap();
                newPost.put("Alergias", res);
                current_user_db.updateChildren(newPost);
                intent2.putExtra("variable_string", res);
                startActivityForResult(intent2, 0);
            }
        });

    }
}

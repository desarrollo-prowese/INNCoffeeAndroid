package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;

public class Combos extends AppCompatActivity {
    private ImageView Menucompleto;
    private ImageView MedioMenu;
    private ImageView TapayBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_combos);
        Menucompleto = findViewById(R.id.imageViewMenuCompleto);
        MedioMenu = findViewById(R.id.imageViewMenuSimple);
        TapayBebida = findViewById(R.id.imageViewTapayBebida);

        Menucompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent envio = new Intent(Combos.this, Menu_Completo.class);
                Bundle extras = new Bundle();
                extras.putString("variable_stringNombre", "Elige Primero Plato");
                envio.putExtras(extras);
                startActivity(envio);
            }
        });

        MedioMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent envio = new Intent(Combos.this, Medio_Menu.class);
                Bundle extras = new Bundle();
                extras.putString("variable_stringNombre", "Elige Primero Plato");
                envio.putExtras(extras);
                startActivity(envio);
            }
        });

        TapayBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent envio = new Intent(Combos.this, Menu_Tapas.class);
                Bundle extras = new Bundle();
                extras.putString("variable_stringNombre", "Elige Primero Plato");
                envio.putExtras(extras);
                startActivity(envio);
            }
        });
    }
}

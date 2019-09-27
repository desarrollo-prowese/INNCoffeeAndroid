package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;

public class Carta_Desayuno extends AppCompatActivity {
    public String recuperacion;
    private TextView bebida;
    private TextView Tostadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_desayuno_1);
        bebida = findViewById(R.id.textViewExtraordinaria);
        Tostadas = findViewById(R.id.textViewTostadas);
        final String recuperamos_variable_string = getIntent().getStringExtra("variable_string");

        Tostadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperacion = recuperamos_variable_string;
                // System.out.println(recuperamos_variable_string);
                Intent intent2 = new Intent(view.getContext(), Tipo_Tostadas.class);
                intent2.putExtra("variable_string", recuperacion);
                startActivityForResult(intent2, 0);
            }
        });

        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Tipo_Bebidas.class);
                intent2.putExtra("variable_string", recuperacion);
                startActivityForResult(intent2, 0);
            }
        });
    }
}

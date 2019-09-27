package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;

public class NuevoPedido_2 extends AppCompatActivity {
    private Button cartaComida;
    private Button cartaDesayuno;
    private Button selecAlergia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuevo_pedido_2);

        cartaComida = findViewById(R.id.buttonCartaComida);
        cartaDesayuno = findViewById(R.id.buttonCartaDesayuno);
        selecAlergia = findViewById(R.id.buttonElegirAlergia);

        cartaDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoPedido_2.this, Carta_Desayuno.class);
                startActivity(intent);
            }
        });
        cartaComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(NuevoPedido_2.this, Comidas.class);
                startActivity(nuevo);
            }
        });
        selecAlergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoPedido_2.this, Carta_Alergenos.class);
                startActivity(intent);
            }
        });
    }
}

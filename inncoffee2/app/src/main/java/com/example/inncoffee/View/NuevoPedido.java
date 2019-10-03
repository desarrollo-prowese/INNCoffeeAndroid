package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;

public class NuevoPedido extends AppCompatActivity {

    private RelativeLayout nuevoPedido;
    private RelativeLayout misPedidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pedidos);

        nuevoPedido = findViewById(R.id.buttonNuevoPedido);
        misPedidos = findViewById(R.id.buttonMisPedidos);

        nuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevoPedido.this, Establecimiento.class);
                startActivity(intent);
            }
        });
        misPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuevoPedido.this, Ver_Pedidos.class);
                startActivity(intent);
            }
        });

    }
}

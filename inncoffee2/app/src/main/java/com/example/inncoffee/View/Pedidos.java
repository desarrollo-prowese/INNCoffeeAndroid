package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.Adapter.AdapterItem;
import com.example.inncoffee.R;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Pedidos extends AppCompatActivity {
    static ArrayList<String> titulo = new ArrayList<>();
    static ArrayList<String> Precio = new ArrayList<>();
    ListView list;
    TextView Bebidas;
    TextView Tostadas;
    TextView precioTotal;
    Button finalizarPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pedido);
        Tostadas = findViewById(R.id.textViewContinuaComida);
        Bebidas = findViewById(R.id.textViewContinuaBebida);
        precioTotal = findViewById(R.id.textViewPtotal);
        finalizarPedido = findViewById(R.id.buttonFinalizarPedido);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String rTitulo = extras.getString("variable_stringTostadaTitulo");
        String rPrecio = extras.getString("variable_stringTostadaPrecio");
        final String rAlergenos = extras.getString("variable_stringAlergenos");
        double valor = Double.parseDouble(rPrecio);
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

        Tostadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hola");
                Intent intent1 = new Intent(view.getContext(), Tipo_Tostadas.class);
                startActivity(intent1);
            }
        });
        Bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), Tipo_Bebidas.class);
                Bundle extras = new Bundle();
                //extras.putString("variable_string1",rAlergenos);
                intent2.putExtras(extras);
                startActivity(intent2);
            }
        });
        finalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pasarTitulo = "";
                String pasarPrecio = "";
                Double precioPasado;
                pasarPrecio = String.join(",", Precio);
                pasarTitulo = String.join(",", titulo);
                //precioPasado= Double.valueOf(pasarPrecio);
                // System.out.println(precioPasado);
                System.out.println("esto son los titulos    " + pasarTitulo);
                System.out.println("esto son los precios    " + pasarPrecio);
                Intent intent3 = new Intent(view.getContext(), Finalizar_Pedido.class);
                Bundle extras = new Bundle();
                extras.putString("ArrayTitulo", pasarTitulo);
                extras.putString("ArrayPrecio", pasarPrecio);
                intent3.putExtras(extras);
                startActivity(intent3);
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




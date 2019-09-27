package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.Adapter.AdapterItem;
import com.example.inncoffee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.inncoffee.View.Pedidos_Comidas.Precio;
import static com.example.inncoffee.View.Pedidos_Comidas.titulo;
import static java.lang.String.valueOf;

public class Finalizar_Pedido_Comida extends AppCompatActivity {
    static String ultimoCliente;
    ListView list;
    // ArrayList<String> titulo = new ArrayList<>();
    // ArrayList<String>Precio = new ArrayList<>();
    Button buttonPago;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.vista_finalizar_pedido);
        buttonPago = findViewById(R.id.buttonPagar);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String ArrayTitulo = extras.getString("ArrayTitulo");
        final String ArrayPrecio = extras.getString("ArrayPrecio");
        final String recuperamos_variable_string = getIntent().getStringExtra("variable_string");
        System.out.println("titulo  " + ArrayTitulo);
        System.out.println("Precio  " + ArrayPrecio);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        double sum = 0;
        for (int i = 0; i < Precio.size(); i++) {
            sum = sum + Double.parseDouble(Precio.get(i));
        }


        System.out.println(sum);
        final String sumaTotal = valueOf(sum);
        final String fecha = dateFormat.format(date);
//
        AdapterItem adapter = new AdapterItem(this, titulo, Precio);
        list = findViewById(R.id.listViewPedido);
        list.setAdapter(adapter);

        buttonPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String res = String.join(",", titulo);
                final String res2 = String.join(",", Precio);
                final String user_id = mAuth.getCurrentUser().getUid();
                System.out.println("esta es la variable      " + recuperamos_variable_string);

                String nuevoCliente = recuperamos_variable_string + 1;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference clientesRef = ref.child("Users").child(user_id).child("pedidos").child(nuevoCliente);

                clientesRef.child("Nombre").setValue(res);
                clientesRef.child("Precio").setValue(res2);
                clientesRef.child("Fecha").setValue(fecha);
                clientesRef.child("Total").setValue(sumaTotal);

                Intent inten1 = new Intent(v.getContext(), Pago_realizado.class);
                startActivity(inten1);
                finish();
            }
        });

    }
}

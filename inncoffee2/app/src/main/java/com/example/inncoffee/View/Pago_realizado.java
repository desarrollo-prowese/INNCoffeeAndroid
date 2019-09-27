package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.Main2Activity;
import com.example.inncoffee.R;

public class Pago_realizado extends AppCompatActivity {
    private Button pagorealizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_realizado);

        pagorealizado = findViewById(R.id.buttonVolver);

        pagorealizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), Main2Activity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}

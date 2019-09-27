package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inncoffee.R;

public class Establecimiento extends AppCompatActivity {
    private ImageView entrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_establecimiento);
        entrar = findViewById(R.id.imageView8);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Establecimiento.this, Google_Maps.class);
                startActivity(intent);
            }
        });
    }
}

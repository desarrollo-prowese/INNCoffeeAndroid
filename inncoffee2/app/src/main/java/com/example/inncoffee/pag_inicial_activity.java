package com.example.inncoffee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class pag_inicial_activity extends Activity {


    private View _bg__pag_inicial_ek2;
    private ImageView trazado_2;
    private ImageView imagen_1;
    private ImageView trazado_1;
    private ImageView color_mode_inncoffe_ek1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicial);


        _bg__pag_inicial_ek2 = findViewById(R.id._bg__pag_inicial_ek2);
        trazado_2 = findViewById(R.id.trazado_2);
        imagen_1 = findViewById(R.id.imagen_1);
        trazado_1 = findViewById(R.id.trazado_1);
        color_mode_inncoffe_ek1 = findViewById(R.id.color_mode_inncoffe_ek1);


        //custom code goes here

    }

    public void initializeUi() {

    }

    public void initializeObjects() {

    }

    public void initializeListeners() {

    }

}
	
	
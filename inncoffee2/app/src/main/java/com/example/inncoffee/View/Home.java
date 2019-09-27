package com.example.inncoffee.View;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.inncoffee.Main2Activity;

public class Home extends Main2Activity {
    private ImageView imagen1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * Setting title and itemChecked
         */

        //inflate your activity layout here!

       /* imagen1= findViewById(R.id.imageButton);
        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this   , NuevoPedido.class);
                startActivity(intent);
            }
        });*/
    }


}

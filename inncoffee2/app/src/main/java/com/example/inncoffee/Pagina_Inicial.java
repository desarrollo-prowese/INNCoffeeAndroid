package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;


public class Pagina_Inicial extends AppCompatActivity {



        private ImageView imageView13;
        private ImageView imageView18;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_pag_inicial);

            imageView13 = findViewById(R.id.imageView13);
            imageView18 = findViewById(R.id.imageView18);

            imageView13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://innoffices.es/"));
                    startActivity(intent);

                }
            });

            imageView18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Pagina_Inicial.this, PrincipalActivity.class));
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                }
            });
        }
    }



package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class pantalla_inn_coffee___1_activity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText etFullName, etUsername, etPassword, etConfirmPassword, etTelefono, etCentro;

    private View _bg__pantalla_inn_coffee___1_ek2;
    private ImageView whatsapp_image_2019_07_23_at_15_12_24_2__ek1;
    private ImageView rectangle_ek9;
    private ImageView color_mode_inncoffe_ek15;
    private View rectangle_ek10;
    private ImageView path_ek22;
    private View rect_ngulo_1460_ek1;
    private TextView correo_electr_nico;
    private View rect_ngulo_1461_ek9;
    private TextView crear_cuenta;
    private View rect_ngulo_1478;
    private TextView nombre;
    private View rect_ngulo_1479;
    private TextView contrase_a;
    private View rect_ngulo_1480;
    private TextView repetir_contrase_a;
    private TextView label;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inn_coffee___1);

        color_mode_inncoffe_ek15 = findViewById(R.id.color_mode_inncoffe_ek15);
        rect_ngulo_1480 = findViewById(R.id.rect_ngulo_1480);
        // repetir_contrase_a = (EditText) findViewById(R.id.repetir_contrase_a);

        mAuth = FirebaseAuth.getInstance();
        label = findViewById(R.id.txViewNombre);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(pantalla_inn_coffee___1_activity.this, pantalla_inn_coffee___3_activity.class);
                    startActivity(intent);
                    finish();
                }
                return;
            }
        };

        /*
        rect_ngulo_1461_ek9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = correo_electr_nico.getText().toString();
                String password = contrase_a.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(pantalla_inn_coffee___1_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(pantalla_inn_coffee___1_activity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                            String FullName = nombre.getText().toString();
                            //String Telefono = etTelefono.getText().toString();
                            //String Center = etCentro.getText().toString();
                            String Alergias = "nu";
                            Object obj = new Object();
                            obj = Alergias;
                            Map newPost = new HashMap();
                            newPost.put("FullName", FullName);
                            newPost.put("Alergias", obj);
                            current_user_db.updateChildren(newPost);
                            sendEmailVerification();
                            Toast.makeText(pantalla_inn_coffee___1_activity.this, "Registro completado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(pantalla_inn_coffee___1_activity.this, pantalla_inn_coffee___3_activity.class);
                            startActivity(intent);

                            finish();
                        }
                    }
                });
            }
        });
        */
        //custom code goes here

    }

    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });

    }
}
	
	
package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class pantalla_inn_coffee___2_activity extends Activity {

    private View _bg__pantalla_inn_coffee___2_ek2;
    private ImageView whatsapp_image_2019_07_23_at_15_12_24_2__ek2;
    private ImageView rectangle_ek11;
    private ImageView color_mode_inncoffe_ek17;
    private View rectangle_ek12;
    private ImageView path_ek24;
    private View rect_ngulo_1460_ek2;
    private EditText correo_electr_nico_ek1;
    private ImageButton rect_ngulo_1461_ek11;
    private TextView iniciar_sesi_n_ek1;
    private View rect_ngulo_1478_ek1;
    private EditText contrase_a_ek1;
    private TextView __has_olvidado_tu_contrase_a_;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText etLoginUsername, etLoginPassword;
    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inn_coffee___2);

            /*
            _bg__pantalla_inn_coffee___2_ek2 = findViewById(R.id._bg__pantalla_inn_coffee___2_ek2);
            whatsapp_image_2019_07_23_at_15_12_24_2__ek2 = findViewById(R.id.whatsapp_image_2019_07_23_at_15_12_24_2__ek2);
            rectangle_ek11 = findViewById(R.id.rectangle_ek11);
            color_mode_inncoffe_ek17 = findViewById(R.id.color_mode_inncoffe_ek17);
            rectangle_ek12 = findViewById(R.id.rectangle_ek12);
            path_ek24 = findViewById(R.id.path_ek24);
            rect_ngulo_1460_ek2 = findViewById(R.id.rect_ngulo_1460_ek2);
            correo_electr_nico_ek1 = findViewById(R.id.correo_electr_nico_ek1);
            rect_ngulo_1461_ek11 = findViewById(R.id.rect_ngulo_1461_ek11);
            iniciar_sesi_n_ek1 = findViewById(R.id.iniciar_sesi_n_ek1);
            rect_ngulo_1478_ek1 = findViewById(R.id.rect_ngulo_1478_ek1);
            contrase_a_ek1 = findViewById(R.id.contrase_a_ek1);
            __has_olvidado_tu_contrase_a_ = findViewById(R.id.__has_olvidado_tu_contrase_a_);
            */

        //custom code goes here
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(pantalla_inn_coffee___2_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return;
            }
        };

        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        /*
        rect_ngulo_1461_ek11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = correo_electr_nico_ek1.getText().toString();
                String password = contrase_a_ek1.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(pantalla_inn_coffee___2_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                startActivity(new Intent(pantalla_inn_coffee___2_activity.this, Main2Activity.class));
                            } else {
                                Toast.makeText(pantalla_inn_coffee___2_activity.this, "Porfavor verifica tu correo", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(pantalla_inn_coffee___2_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    // Toast.makeText(LoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                });
            }
        });
*/

    }
}
	
	
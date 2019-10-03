package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class pantalla_inn_coffee___2_activity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText emailEditText;
    private EditText passwordEditText;

    private RelativeLayout back;
    private RelativeLayout login;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inn_coffee___2);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(pantalla_inn_coffee___2_activity.this, Main2Activity.class));
                    finish();
                }
            }
        };

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pantalla_inn_coffee___2_activity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
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
                });
            }
        });
    }

    public void initializeObjects() {

    }

    public void initializeListeners() {

    }
}
	
	
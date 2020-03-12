package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Registrotest extends Activity{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText etFullName, etUsername, etPassword, etConfirmPassword,etTelefono, etCentro;

    private Button btnRegister;
    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "Email sent.");
                }
            }
        });

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_test);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Registrotest.this, menu_parallax_activity.class);
                    startActivity(intent);
                    finish();
                }
                return;
            }
        };


        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etTelefono = findViewById(R.id.etTelefono);
        etCentro = findViewById(R.id.etCentro);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registrotest.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                  if (!task.isSuccessful()) {

                                      String user_id = mAuth.getCurrentUser().getUid();
                                      String FullName = etFullName.getText().toString();
                                      String Phone = etTelefono.getText().toString();
                                      String Center   = etFullName.getText().toString();
                                      Object obj = new Object();
                                      String alergias = "na";
                                      obj = alergias;
                                      Map <String, Object> newPost = new HashMap();
                                      newPost.put("FullName", FullName);
                                      newPost.put("Alergias", obj);
                                      newPost.put("Phone", Phone);
                                      newPost.put("Center", Center);
                                      sendEmailVerification();
                                      mDatabase.child("Users").child(user_id).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task2) {
                                              if (!task2.isSuccessful()) {
                                                  startActivity(new Intent(Registrotest.this, menu_parallax_activity.class));
                                                  finish();
                                              }
                                              else {

                                                  Toast.makeText(Registrotest.this, "Error al Registro " + task2.isSuccessful(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      });
                                  }
                                  else {

                                      Toast.makeText(Registrotest.this, "Error No se a Podido Registrar este Usuario " + task.getException(),
                                              Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });
    }



}
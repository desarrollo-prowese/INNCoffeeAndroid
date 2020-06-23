package com.rakshasa.inncoffee.RegistroLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;

public class Registro extends Activity{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText etFullName, etUsername, etPassword, etConfirmPassword;

    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(Registro.this, RegistroVerifica.class);
                    startActivity(intent);
                    finish();
                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
      /*  FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(firebaseAuthListener);
        GoogleSignIn.getLastSignedInAccount(this);*/
        mAuth.addAuthStateListener(firebaseAuthListener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.signOut();
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        inicialize();
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

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map <String, Object> newPost = new HashMap<>();
                                    Log.w("TAG","Se Registro Correctamente ", task.getException());
                                    // Sign in success, update UI with the signed-in user's information
                                    String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                    String FullName = etFullName.getText().toString();
                                    String email = etUsername.getText().toString();
                                    Object obj = new Object();String Alergia = "0";obj = Alergia;
                                    Object obj1 = new Object();String Phone = "";obj1 = Phone;
                                    Object obj2 = new Object();String Center = "";obj2 = Center;
                                    Object obj3 = new Object();String PhotoURL = "";obj3 = PhotoURL;
                                    Object obj4 = new Object();String FechaNac = "";obj4 = FechaNac;
                                    Object obj5 = new Object();String DineroAcumulados = "0.00€";obj5 = DineroAcumulados;
                                    newPost.put("FullName", FullName);
                                    newPost.put("Alergias", obj);
                                    newPost.put("Phone", obj1);
                                    newPost.put("Center", obj2);
                                    newPost.put("PhotoURL", obj3);
                                    newPost.put("FechaNac", obj4);
                                    newPost.put("Email", email);
                                    newPost.put("DineroAcumulados", obj5);
                                    sendEmailVerification();
                                    mDatabase.child("Users").child(user_id).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            if (!task2.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                                Toast.makeText(Registro.this, "Se Registro " + task2.isSuccessful(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                   // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Registro.this, "Ya estas registrado " + task.isSuccessful(),
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }
        });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()); //adding user info to database
        Intent loginIntent = new Intent(this, RegistroVerifica.class);
        startActivity(loginIntent);
    }

}
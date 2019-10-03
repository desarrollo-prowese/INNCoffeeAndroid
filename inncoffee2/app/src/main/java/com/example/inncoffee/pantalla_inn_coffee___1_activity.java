package com.example.inncoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Objects;

public class pantalla_inn_coffee___1_activity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText etFullName, etUsername, etPassword, etConfirmPassword, etTelefono, etCentro;

    private EditText emailEditText;
    private EditText passwordConfirmationEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private RelativeLayout createAccountRelativeLayout;
    private RelativeLayout back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pantalla_inn_coffee___1);
        mAuth = FirebaseAuth.getInstance();
        createAccountRelativeLayout = findViewById(R.id.create_account);
        nameEditText = findViewById(R.id.name_edit_text);
        passwordConfirmationEditText = findViewById(R.id.password_confirmation_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        back = findViewById(R.id.back);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(pantalla_inn_coffee___1_activity.this, pantalla_inn_coffee___3_activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pantalla_inn_coffee___1_activity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });

        createAccountRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(pantalla_inn_coffee___1_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(pantalla_inn_coffee___1_activity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            String FullName = nameEditText.getText().toString();
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
    }

    public void initializeUi() {

    }

    public void initializeObjects() {

    }

    public void initializeListeners() {

    }

    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
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
}
	
	
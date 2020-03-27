
	 
	/*
     *	This content is generated from the API File Info.
     *	(Alt+Shift+Ctrl+I).
     *
     *	@desc
     *	@file 		pag_inicial
     *	@date 		0
     *	@title 		PAG INICIAL
     *	@author
     *	@keywords
     *	@generator 	Export Kit v1.2.8.xd
     *
     */


    package com.example.inncoffee.RegistroLogin;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.example.inncoffee.MainActivity;
    import com.example.inncoffee.PrincipalActivity;
    import com.example.inncoffee.R;
    import com.example.inncoffee.ui.home.HomeFragment;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.FragmentTransaction;

    public class Login extends AppCompatActivity {

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener firebaseAuthListener;
        private EditText etLoginUsername, etLoginPassword;
        private Button btnLogin;
        private EditText etUsername, etPassword;

        private void inicialize() {
            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Log.w("TAG", "onAuthStateChanged - Logueado");

                    } else {
                        Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                    }
                }
            };

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            inicialize();

            //custom code goes here
            mAuth = FirebaseAuth.getInstance();




            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    Toast.makeText(Login.this, "Porfavor verifica tu correo", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        /* Toast.makeText(LoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();*/
                    });
                }
            });


        }
        public void updateUI(FirebaseUser currentUser) {
            Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
            profileIntent.putExtra("Email", currentUser.getEmail());
            Log.v("DATA", currentUser.getUid());
            startActivity(profileIntent);
    }
    }
	
	
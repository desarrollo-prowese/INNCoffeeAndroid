
	 
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
        private EditText etLoginUsername, etLoginPassword;
        private Button btnLogin;
        private EditText etUsername, etPassword;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.pantalla_inn_coffee___2);


            //custom code goes here
            mAuth = FirebaseAuth.getInstance();
            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        Intent intent = new Intent(pantalla_inn_coffee___2_activity.this, menu_parallax_activity.class);
                        startActivity(intent);
                        finish();
                    }
                    return;
                }
            };

            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(pantalla_inn_coffee___2_activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    startActivity(new Intent(pantalla_inn_coffee___2_activity.this, menu_parallax_activity.class));
                                } else {
                                    Toast.makeText(pantalla_inn_coffee___2_activity.this, "Porfavor verifica tu correo", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(pantalla_inn_coffee___2_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        /* Toast.makeText(LoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();*/
                    });
                }
            });


        }
    }
	
	
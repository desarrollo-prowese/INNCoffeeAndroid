
	 
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


    package com.rakshasa.inncoffee.RegistroLogin;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.PorterDuff;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.rakshasa.inncoffee.MainActivity;
    import com.rakshasa.inncoffee.PrincipalActivity;
    import com.rakshasa.inncoffee.R;
    import com.rakshasa.inncoffee.ui.home.HomeFragment;
    import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    import java.util.Objects;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
    import io.paperdb.Paper;

    public class Login extends AppCompatActivity {

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener firebaseAuthListener;
        private EditText etLoginUsername, etLoginPassword;
        private Button btnLogin;
        private TextView restablecer;
        private EditText etUsername, etPassword;
        private final String UserEmail = "UserEmail";
        private final String UserPassword = "UserPassword";
        private CheckBox checkBox;
        private Carga carga;

        private void inicialize () {
            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth) {
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
        public void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            inicialize();
            checkBox = (CheckBox) findViewById(R.id.checkBox);
            carga = new Carga(Login.this);
            //custom code goes here
            mAuth = FirebaseAuth.getInstance();
            restablecer = findViewById(R.id.restablecer);
            restablecer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    startActivity(new Intent(Login.this, Restablecer.class));
                    finish();
                }
            });


            Paper.init(this);

            String UserEmails = Paper.book().read(UserEmail);
            String UserPasswords = Paper.book().read(UserPassword);
            if (UserEmails != "" && UserPasswords != "") {
                if (!TextUtils.isEmpty(UserEmails) && !TextUtils.isEmpty(UserPasswords)) {

                    Loguear(UserEmails, UserPasswords);

                }
            }

            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                     String email = etUsername.getText().toString();
                     String password = etPassword.getText().toString();
                    if(checkBox.isChecked()){

                        Paper.book().write(UserEmail, email);
                        Paper.book().write(UserPassword,password);
                    }

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
                                carga.startCarga();
                                if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
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
        private void Loguear (String email, String password) {



            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete (@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        carga.startCarga();
                        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            Toast.makeText(Login.this, "Porfavor verifica tu correo", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
               //Toast.makeText(LoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
            });
        }
        public void updateUI(FirebaseUser currentUser) {
            Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
            profileIntent.putExtra("Email", currentUser.getEmail());
            Log.v("DATA", currentUser.getUid());
            startActivity(profileIntent);
    }
}
	
	
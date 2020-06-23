package com.rakshasa.inncoffee.RegistroLogin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.quiero.Tostadas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Restablecer extends Activity {


    private FirebaseUser mUser;
    private String ID ;
    private DatabaseReference mUsuario;
    private EditText email;
    private String EmailText = "";
    private FirebaseDatabase mDatabase;
    private static final String USERS = "Users";
    private Button rect_ngulo_1461_ek12;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restablecer);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mUsuario = mDatabase.getReference(USERS);

        email = findViewById(R.id.etUsername2);
        rect_ngulo_1461_ek12 = findViewById(R.id.rect_ngulo_1461_ek12);


        //custom code goes here
        rect_ngulo_1461_ek12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailText = email.getText().toString();
                if (TextUtils.isEmpty(EmailText)) {
                    Toast.makeText(Restablecer.this, "Debe Ingresar el Email", Toast.LENGTH_SHORT).show();
                return;
                }

                resetpassword();

            }
        });
    }
      private void resetpassword(){

          mAuth.sendPasswordResetEmail(EmailText).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()){

                      startActivity(new Intent(Restablecer.this, Login.class));
                      finish();
                      Toast.makeText(Restablecer.this, "Se ha envido un correo para restablecer tu contraseña", Toast.LENGTH_SHORT).show();

                  }else {

                      Toast.makeText(Restablecer.this, "No se pudo enviar el correo de restablecer contraseña", Toast.LENGTH_SHORT).show();

                  }
              }
          });
      }
}



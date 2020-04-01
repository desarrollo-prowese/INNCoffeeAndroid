
	 
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
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Paint;
    import android.graphics.PorterDuff;
    import android.graphics.PorterDuffXfermode;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.inncoffee.R;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.UserProfileChangeRequest;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    import java.io.ByteArrayOutputStream;
    import java.util.Objects;

    import androidx.annotation.NonNull;

    public class RegistroVerifica extends Activity {


        private FirebaseUser mUser;
        private String ID ;
        private DatabaseReference mUsuario;
        private Bitmap original, mask;
        Bitmap resultado, maskbitmap;
        private Uri mImageUri;
        private FirebaseDatabase mDatabase;
        private static final String USERS = "Users";
        private Button rect_ngulo_1461_ek12;
        private FirebaseAuth mAuth;
        private TextView para_terminar_tu_registro_verifica_tu_correo_electr_nico_mediante_el_enlace_que_acabamos_de_enviarte_por_mail_;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_verifica);
            Bitmap mascara = MaskFilter();
            mAuth = FirebaseAuth.getInstance();

            mDatabase = FirebaseDatabase.getInstance();
            mUsuario = mDatabase.getReference(USERS);


            rect_ngulo_1461_ek12 = findViewById(R.id.rect_ngulo_1461_ek12);


            //custom code goes here
            rect_ngulo_1461_ek12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      startActivity(new Intent(RegistroVerifica.this, Login.class));
                      finish();
                }
            });
        }

        private Bitmap MaskFilter(){
            try {
                original = BitmapFactory.decodeResource(getResources(),R.drawable.com_facebook_profile_picture_blank_portrait);
                mask = BitmapFactory.decodeResource(getResources(),R.drawable.trazado_52_ek6);
                if (original != null){
                    int iv_wight = original.getWidth();
                    int iv_height = original.getHeight();

                    resultado = Bitmap.createBitmap(iv_wight,iv_height,Bitmap.Config.ARGB_8888);
                    maskbitmap = Bitmap.createScaledBitmap(mask,iv_wight,iv_height,true);
                    Canvas canvas = new Canvas(resultado);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    canvas.drawBitmap(original, 0,0 ,null);
                    canvas.drawBitmap(maskbitmap,0,0, paint);
                    paint.setXfermode(null);
                    paint.setStyle(Paint.Style.STROKE);


                }

            }catch (OutOfMemoryError outOfMemoryError){

                outOfMemoryError.printStackTrace();
            }
            handleUpload(resultado);
            return resultado;
        }

        private void handleUpload(Bitmap bitmap) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Perfiles")
                    .child(uid + ".jpeg");



            reference.putBytes(baos.toByteArray())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // getDownloadUrl(reference);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: ",e.getCause() );
                        }
                    });

        }
        private void getDownloadUrl(StorageReference reference) {
            reference.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("TAG", "onSuccess: " + uri);

                            //setUserProfileUrl(uri);
                        }
                    });
        }

        private void setUserProfileUrl(final Uri uri) {

            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build();

            mUser.updateProfile(request)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(RegistroVerifica.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistroVerifica.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
	
	
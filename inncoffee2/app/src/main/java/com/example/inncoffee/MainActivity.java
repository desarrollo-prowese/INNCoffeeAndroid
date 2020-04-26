package com.example.inncoffee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.inncoffee.RegistroLogin.Carga;
import com.example.inncoffee.RegistroLogin.Login;
import com.example.inncoffee.ui.home.HomeFragment;
import com.example.inncoffee.ui.home.HomeFragment1;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.mispedidos.MisPedidosClass;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizar;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.example.inncoffee.ui.ofertas.OfertasClass;
import com.example.inncoffee.ui.ofertas.OfertasFragment;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    public static TextView mensajeToolbar;
    public static ImageView irmensaje;
    protected DrawerLayout drawer;
    public static ImageView bIncio;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference mRefo;
    private DatabaseReference mRefos;
    private DatabaseReference mUsuario;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static int pendingNotifications;
    private static final String USERS = "Users";
    private static ImageView perfil;
    public  String email,fname,centre;
    public static boolean back= false;
    public  TextView fnames,centro;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Bitmap original, mask;
    Bitmap resultado, maskbitmap;
    private String ID ;
    private Uri mImageUri;


    // badge text view
    TextView badgeCounter;
    // change the number to see badge in action
    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {


                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);


                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = getIntent();
        getApplicationContext().getCacheDir();
        Toolbar toolbar = findViewById(R.id.toolbar);
        inicialize();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
        final String email  = intent.getStringExtra("Email");
        Bundle extra = intent.getExtras();
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navHeaderView = navigationView.getHeaderView(0);

        fnames = (TextView) navHeaderView.findViewById(R.id.TextoNombre);
        centro = (TextView) navHeaderView.findViewById(R.id.Centro);
        perfil = (ImageView) navHeaderView.findViewById(R.id.ImagenPerfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = mDatabase.getReference("Mensajes");
        mRefo = mDatabase.getReference("Ofertas");
        mRefos = mDatabase.getReference("MisPedidos");
        mUsuario = mDatabase.getReference(USERS);
        Log.v("USERID", mUsuario.getKey());
        Log.v("USERGUID", mAuth.getUid());
        UpdateBarra();

        if (mUser != null) {
               if (mUser.getPhotoUrl() != null) {

                   Glide.with(getApplicationContext()).load(mUser.getPhotoUrl().toString()).into(perfil);
               }

        }



        mensajeToolbar = (TextView) findViewById(R.id.mesajestolbar);


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tInicio:
                        HomeFragment1 fragment = new HomeFragment1();
                        FragmentTransaction ftEs = getSupportFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        mensajeToolbar.setText("");
                        break;
                    case R.id.tInvita:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Crecemos juntos");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                        break;
                    case R.id.tOfertas:

                        OfertasFragment fragments = new OfertasFragment();
                        FragmentTransaction ftEss = getSupportFragmentManager().beginTransaction();
                        ftEss.replace(R.id.nav_host_fragment, fragments);
                        ftEss.addToBackStack(null);
                        ftEss.commit();
                        break;
                    case R.id.tPedidos:

                        String texto = "Probando INN COFFEE";

                        MensajesClass user=new MensajesClass(texto);

                        String key=mRef.push().getKey();
                        mRef.child(key).setValue(user);

                        Toast.makeText(MainActivity.this, "Proximamente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tIra:

                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://innoffices.es/"));
                        startActivity(intent);
                        break;
                    case R.id.tCerrar:
                        mAuth.signOut();
                        Paper.book().destroy();
                        startActivity(new Intent(MainActivity.this, Pagina_Inicial.class));
                        Toast.makeText(MainActivity.this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
                        finish();
                        break;

                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });



        bIncio = (ImageView) findViewById(R.id.bInicio);
        bIncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment1 fragment = new HomeFragment1();
                FragmentTransaction ftEs = getSupportFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
                finish();
                startActivity(getIntent());
            }
        });


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    pendingNotifications = (int) dataSnapshot.getChildrenCount();
                    badgeCounter = findViewById(R.id.badge_counter);
                    badgeCounter.setText(Integer.toString(pendingNotifications));


                }else {

                    badgeCounter = findViewById(R.id.badge_counter);
                    badgeCounter.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        irmensaje = (ImageView) findViewById(R.id.irmensaje);

        irmensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MensajesFragment fragment = new MensajesFragment();
                FragmentTransaction ftEs = getSupportFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });


    }
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(getIntent());
    }*/



   /* @Override
    protected void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
        profileIntent.putExtra("Email", currentUser.getEmail());
        Log.v("DATA", currentUser.getUid());
        startActivity(profileIntent);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            try {

                original = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
                mask = BitmapFactory.decodeResource(getResources(),R.drawable.inncoffeelogo);
                if (original != null){
                    int iv_wight = original.getWidth();
                    int iv_height = original.getHeight();


                    resultado = Bitmap.createBitmap(iv_wight,iv_height,Bitmap.Config.ARGB_8888);
                    maskbitmap = Bitmap.createScaledBitmap(mask,iv_wight,iv_height, true);
                    Canvas canvas = new Canvas(resultado);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    canvas.drawBitmap(original, 0,0 ,null);
                    canvas.drawBitmap(maskbitmap,0,0, paint);
                    paint.setXfermode(null);
                    paint.setStyle(Paint.Style.STROKE);
                    perfil.setImageBitmap(resultado);
                    handleUpload(resultado);

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);



        }


    }
    private void handleUpload(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference("Perfiles")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);

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


                        setUserProfileUrl(uri);
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

                        Toast.makeText(MainActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                        mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    String foto = mUser.getPhotoUrl().toString();
                                    Log.v("QUE FOTO ES " , foto);
                                    ID = mAuth.getUid();
                                    mUsuario.child(ID).child("PhotoURL").setValue(foto);

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("TAG", "Failed to read value.", databaseError.toException());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        private void UpdateBarra(){
            mUsuario.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                    ID = mAuth.getUid();
                    assert ID != null;
                    fname = dataSnapshot.child(ID).child("FullName").getValue().toString();
                    centre = dataSnapshot.child(ID).child("Center").getValue().toString();
                    Log.v("NOMBRE :  " , fname);
                    Log.v("CENTRO :   " , centre);

                 }
                    if (fname != null)
                        fnames.setText(fname);
                    if (centre != null) {
                        centro.setText(centre);
                    }
                }


                @Override
                public void onCancelled (@NonNull DatabaseError databaseError) {
                    Log.w("TAG", "Failed to read value.", databaseError.toException());
                }
            });

    }
}

package com.example.inncoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.inncoffee.RegistroLogin.Login;
import com.example.inncoffee.ui.home.HomeFragment;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.mensajes.MensajesClass;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.example.inncoffee.ui.ofertas.OfertasClass;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

public class MainActivity extends AppCompatActivity {

    public static TextView mensajeToolbar;
    public static ImageView irmensaje;
    protected DrawerLayout drawer;
    public static ImageView bIncio;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference mRefo;
    private DatabaseReference mUsuario;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static int pendingNotifications;
    private static final String USERS = "Users";
    private String email;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


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
        Intent intent = getIntent();
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
        final TextView fnames = (TextView) navHeaderView.findViewById(R.id.TextoNombre);
        final TextView centro = (TextView) navHeaderView.findViewById(R.id.Centro);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Mensajes");
        mRefo = mDatabase.getReference("Ofertas");
        mUsuario = mDatabase.getReference(USERS);
        Log.v("USERID", mUsuario.getKey());
        Log.v("USERGUID", mAuth.getUid());
        mUsuario.addValueEventListener(new ValueEventListener() {
            String fname;
            String centre;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()){
                       if(Objects.equals(keyId.child("Email").getValue(), email)) {
                           fname = keyId.child("FullName").getValue().toString().trim();
                           centre = keyId.child("Center").getValue().toString().trim();

                   }
               }
            }
                if (fname != null )
                fnames.setText(fname);
                if (centre != null){
                centro.setText(centre);
                }

        }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });


        mensajeToolbar = (TextView) findViewById(R.id.mesajestolbar);


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tInicio:
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction ftEs = getSupportFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                        mensajeToolbar.setText("");
                        break;
                    case R.id.tInvita:

                        String texto = "Probando INN COFFEE";

                        MensajesClass user=new MensajesClass(texto);

                        String key=mRef.push().getKey();
                        mRef.child(key).setValue(user);


                        Toast.makeText(MainActivity.this, "Data inserted...", Toast.LENGTH_SHORT).show();
                    //    Toast.makeText(MainActivity.this, "Proximamente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tOfertas:

                        String ofertas = "NUEVO TAZON CON CEREALES";
                        String porcentaje = "-20%";

                        OfertasClass user1=new OfertasClass(ofertas,porcentaje);

                        String key1=mRefo.push().getKey();
                        mRefo.child(key1).setValue(user1);


                        Toast.makeText(MainActivity.this, "Proximamente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tPagos:
                        Toast.makeText(MainActivity.this, "Proximamente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tPedidos:
                        Toast.makeText(MainActivity.this, "Proximamente", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tIra:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://innoffices.es/"));
                        startActivity(intent);
                        break;
                    case R.id.tCerrar:
                        mAuth.signOut();
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
                HomeFragment fragment = new HomeFragment();
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

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }


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


}

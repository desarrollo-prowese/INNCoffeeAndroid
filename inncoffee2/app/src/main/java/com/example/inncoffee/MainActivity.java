package com.example.inncoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inncoffee.ui.home.HomeFragment;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static TextView mensajeToolbar;
    public static ImageView irmensaje;
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navHeaderView = navigationView.getHeaderView(0);

        mensajeToolbar = (TextView) findViewById(R.id.mesajestolbar);


       /* DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navHeaderView = navigationView.getHeaderView(0);*/
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
                    case R.id.nav_mensaje:
                        Toast.makeText(MainActivity.this, "nav1 selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_puntos:
                        Toast.makeText(MainActivity.this, "nav1 selected", Toast.LENGTH_SHORT).show();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}

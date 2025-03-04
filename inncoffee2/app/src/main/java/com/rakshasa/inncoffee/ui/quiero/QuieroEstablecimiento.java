package com.rakshasa.inncoffee.ui.quiero;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.home.HomeFragment;
import com.rakshasa.inncoffee.ui.quiero.libreriamap.FloatingMarkerTitlesOverlay;
import com.rakshasa.inncoffee.ui.quiero.libreriamap.MarkerInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroEstablecimiento extends Fragment implements OnMapReadyCallback {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mUsuario;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private static final String USERS = "Users";
    private String ID;
    private Marker mMark;
    private FloatingMarkerTitlesOverlay floatingMarkersOverlay;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private void inicialize () {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getActivity(), QuieroEstablecimiento.class);
                    startActivity(intent);
                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {


                    Intent intent = new Intent(getActivity(), QuieroEstablecimiento.class);
                    startActivity(intent);


                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quieroestablecimiento_fragment, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUsuario = mDatabase.getReference(USERS);
        inicialize();
        ID = mAuth.getUid();
        Log.v("QUe esta pasando ", ID);
        Log.v("USERID", mUsuario.getKey());
        Log.v("USERGUID", mAuth.getUid());

        floatingMarkersOverlay = root.findViewById(R.id.map_floating_markers_overlay);


        return root;
    }


    public void onMapReady (final GoogleMap googleMap) {

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.getUiSettings().isZoomControlsEnabled();
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().isMyLocationButtonEnabled();
        }


        LatLng Centro = new LatLng(37.4038783, -5.9789261);
        LatLng SevillaCartuja = new LatLng(37.4086401, -6.0077992);
        final String title = "INN COFFEE SEVILLA CARTUJA";
        final int color = Color.BLACK;
        final MarkerInfo mi = new MarkerInfo(SevillaCartuja, title, color);
        googleMap.addMarker(new MarkerOptions().position(SevillaCartuja).title("INN COFFEE SEVILLA CARTUJA"));

        LatLng SevillaNervion = new LatLng(37.3792235, -5.976995);
        final String title2 = "INN COFFEE SEVILLA NERVION";
        final int color2 = Color.BLACK;
        final MarkerInfo mi2 = new MarkerInfo(SevillaNervion, title2, color2);
        googleMap.addMarker(new MarkerOptions().position(SevillaNervion).title("INN COFFEE SEVILLA NERVION"));

        LatLng SevillaEste = new LatLng(37.4087005, -5.946891);
        final String title3 = "INN COFFEE SEVILLA ESTE";
        final int color3 = Color.BLACK;
        final MarkerInfo mi3 = new MarkerInfo(SevillaEste, title3, color3);
        googleMap.addMarker(new MarkerOptions().position(SevillaEste).title("INN COFFEE SEVILLA ESTE"));


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick (final Marker marker) {
                if (marker.getTitle().equals("INN COFFEE SEVILLA NERVION")) {
                    Toast.makeText(getActivity(), " Elegiste  Nervion", Toast.LENGTH_SHORT).show();

                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                mUsuario.child(ID).child("Center").setValue("INN COFFEE SEVILLA NERVION");

                                QuieroNuevoPedido2 fragment = new QuieroNuevoPedido2();
                                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                                ftEs.replace(R.id.nav_host_fragment, fragment);
                                ftEs.addToBackStack(null);
                                ftEs.commit();

                            }
                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }


                if (marker.getTitle().equals("INN COFFEE SEVILLA ESTE")) {
                    Toast.makeText(getActivity(), " Elegiste  Sevilla Este", Toast.LENGTH_SHORT).show();

                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                mUsuario.child(ID).child("Center").setValue("INN COFFEE SEVILLA ESTE");

                                QuieroNuevoPedido2 fragment = new QuieroNuevoPedido2();
                                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                                ftEs.replace(R.id.nav_host_fragment, fragment);
                                ftEs.addToBackStack(null);
                                ftEs.commit();
                            }
                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }
                if (marker.getTitle().equals("INN COFFEE SEVILLA CARTUJA")) {

                    Toast.makeText(getActivity(), " Elegiste  Cartuja", Toast.LENGTH_SHORT).show();


                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                mUsuario.child(ID).child("Center").setValue("INN COFFEE SEVILLA CARTUJA");

                                QuieroNuevoPedido2 fragment = new QuieroNuevoPedido2();
                                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                                ftEs.replace(R.id.nav_host_fragment, fragment);
                                ftEs.addToBackStack(null);
                                ftEs.commit();
                            }
                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }


                return false;
            }
        });
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Centro)     // Sets the center of the map to Geolocation
                .zoom(12.6f)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        floatingMarkersOverlay.setSource(googleMap);
        floatingMarkersOverlay.setMaxFloatingTitlesCount(3);
        floatingMarkersOverlay.addMarker(0, mi);
        floatingMarkersOverlay.addMarker(1, mi2);
        floatingMarkersOverlay.addMarker(2, mi3);
    }


}


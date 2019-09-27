package com.example.inncoffee.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.Model.Objetos;
import com.example.inncoffee.R;
import com.example.inncoffee.ViewHolder.ObjetosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Te extends AppCompatActivity {
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    public String paso;
    public String tipo_pan = "Panes";
    public int position;
    public int positionNueva;
    Button seleccion;
    Button entera;
    Button media;
    ImageView suma;
    ImageView resta;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Objetos> options;
    FirebaseRecyclerAdapter<Objetos, ObjetosViewHolder> adapter;
    private Parcelable mListState = null;
    private Float precioUnidad;
    private int CantidadInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_objetos);
        recyclerview = findViewById(R.id.rViewObjetos);

        final String recuperamos_variable_string = getIntent().getStringExtra("variable_string");


        recyclerview.setHasFixedSize(true);
        seleccion = findViewById(R.id.buttonSelec);
        entera = findViewById(R.id.buttonEntera);
        media = findViewById(R.id.buttonMedia);
        // entera.setVisibility(View.GONE);
        // media.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Desayuno").child("Te_e_infusiones");
        options = new FirebaseRecyclerOptions.Builder<Objetos>().setQuery(databaseReference, Objetos.class).build();
        List<String> myList = new ArrayList<String>(Arrays.asList(recuperamos_variable_string.split(",")));
        adapter = new FirebaseRecyclerAdapter<Objetos, ObjetosViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ObjetosViewHolder objetosViewHolder, int i, final Objetos objetos) {
                List<String> myList = new ArrayList<String>(Arrays.asList(recuperamos_variable_string.split(",")));
                for (String item : myList) {
                    if (!objetos.getAlergia().contains(item)) {
                        Picasso.get().load(objetos.getImagelink()).into(objetosViewHolder.Imagen, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });

                        objetosViewHolder.titulo.setText(objetos.getNombre());
                        objetosViewHolder.Precio.setText(objetos.getPrecio());
                        System.out.println("PREEEEEEECIO " + objetosViewHolder.Precio.getText().toString());

                        precioUnidad = Float.parseFloat(objetos.getPrecio());
                        final String recuperamos_descripcion_panes = getIntent().getStringExtra("variable_string1");

                        objetosViewHolder.Suma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorSuma = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalSuma = valorSuma + 1;
                                Double valorPrecioSuma = Double.parseDouble(objetosViewHolder.Precio.getText().toString());

                                Double sumaTotal = valorPrecioSuma + precioUnidad;
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) >= precioUnidad) {

                                    objetosViewHolder.Resta.setEnabled(true);

                                }
                                double valorfinal = Math.round(sumaTotal * 100d) / 100d;
                                objetosViewHolder.Cantidad.setText("" + totalSuma);
                                objetosViewHolder.Precio.setText("" + valorfinal);
                                System.out.println(objetosViewHolder.Precio.getText().toString());

                            }
                        });
                        objetosViewHolder.Resta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorResta = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalResta = valorResta - 1;
                                Double valorPrecioResta = Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                Double totalPrecioResta = valorPrecioResta - precioUnidad;
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) == precioUnidad) {
                                    objetosViewHolder.Resta.setEnabled(false);
                                }
                                double valorfinalResta = Math.round(totalPrecioResta * 100d) / 100d;
                                objetosViewHolder.Cantidad.setText("" + totalResta);
                                objetosViewHolder.Precio.setText("" + valorfinalResta);
                                if (Float.parseFloat(objetosViewHolder.Cantidad.getText().toString()) == 0) {
                                    objetosViewHolder.Precio.setText("" + 0.00);
                                }
                            }
                        });

                        objetosViewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Menu_Completo.bebidaMenuComleto.equals("1")) {
                                    try {
                                        Intent intentMC = new Intent(view.getContext(), Menu_Completo.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentMC);
                                    } finally {
                                        finish();
                                    }


                                } else if (Menu_Completo.bebidaMenuComleto.equals("2")) {
                                    try {
                                        Intent intentMC = new Intent(view.getContext(), Medio_Menu.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentMC);
                                        finishActivity(0);
                                    } finally {
                                        finish();
                                    }
                                } else if (Menu_Completo.bebidaMenuComleto.equals("3")) {
                                    try {
                                        Intent intentT = new Intent(view.getContext(), Menu_Tapas.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentT);
                                    } finally {
                                        finish();
                                    }
                                } else if (Comidas.bebidas_comida.equals("1")) {
                                    try {
                                        Intent intent2 = new Intent(view.getContext(), Pedidos_Comidas.class);
                                        Bundle extras = new Bundle();
                                        System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringComidaTitulo", objetos.getNombre());
                                        extras.putString("variable_stringComidaPrecio", objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringAlergenos", recuperamos_variable_string);
                                        intent2.putExtras(extras);
                                        Comidas.bebidas_comida = "0";
                                        startActivity(intent2);
                                    } finally {
                                        finish();
                                    }
                                } else {
                                    try {
                                        Intent intent2 = new Intent(view.getContext(), Pedidos.class);
                                        Bundle extras = new Bundle();
                                        System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringTostadaTitulo", objetos.getNombre());
                                        extras.putString("variable_stringTostadaPrecio", objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringAlergenos", recuperamos_variable_string);
                                        intent2.putExtras(extras);
                                        startActivity(intent2);
                                    } finally {
                                        finish();
                                    }
                                }
                            }
                        });


                    } else {
                        Picasso.get().load(R.drawable.error).into(objetosViewHolder.Imagen, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });

                        objetosViewHolder.titulo.setText(objetos.getNombre());
                        objetosViewHolder.Precio.setText(objetos.getPrecio());
                        System.out.println("PREEEEEEECIO " + objetosViewHolder.Precio.getText().toString());
                        precioUnidad = Float.parseFloat(objetos.getPrecio());
                        final String recuperamos_descripcion_panes = getIntent().getStringExtra("variable_string1");
                        objetosViewHolder.btSelec.setText(recuperamos_descripcion_panes + " (Seleccionar)");

                        objetosViewHolder.Suma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorSuma = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalSuma = valorSuma + 1;
                                Double valorPrecioSuma = Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                // FloaotalPrecioSuma=valorPrecioSuma+precioUnidad;
                                // t tDecimalFormat formato1 = new DecimalFormat("#.00");
                                Double sumaTotal = valorPrecioSuma + precioUnidad;
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) >= precioUnidad) {

                                    objetosViewHolder.Resta.setEnabled(true);

                                }
                                double valorfinal = Math.round(sumaTotal * 100d) / 100d;
                                objetosViewHolder.Cantidad.setText("" + totalSuma);
                                objetosViewHolder.Precio.setText("" + valorfinal);
                                System.out.println(objetosViewHolder.Precio.getText().toString());

                            }
                        });
                        objetosViewHolder.Resta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorResta = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalResta = valorResta - 1;
                                Double valorPrecioResta = Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                Double totalPrecioResta = valorPrecioResta - precioUnidad;
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) == precioUnidad) {
                                    objetosViewHolder.Resta.setEnabled(false);
                                }
                                double valorfinalResta = Math.round(totalPrecioResta * 100d) / 100d;
                                objetosViewHolder.Cantidad.setText("" + totalResta);
                                objetosViewHolder.Precio.setText("" + valorfinalResta);
                                if (Float.parseFloat(objetosViewHolder.Cantidad.getText().toString()) == 0) {
                                    objetosViewHolder.Precio.setText("" + 0.00);
                                }
                            }
                        });

                        objetosViewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Menu_Completo.bebidaMenuComleto.equals("1")) {
                                    try {
                                        Intent intentMC = new Intent(view.getContext(), Menu_Completo.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentMC);
                                    } finally {
                                        finish();
                                    }


                                } else if (Menu_Completo.bebidaMenuComleto.equals("2")) {
                                    try {
                                        Intent intentMC = new Intent(view.getContext(), Medio_Menu.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentMC);
                                        finishActivity(0);
                                    } finally {
                                        finish();
                                    }
                                } else if (Menu_Completo.bebidaMenuComleto.equals("3")) {
                                    try {
                                        Intent intentT = new Intent(view.getContext(), Menu_Tapas.class);
                                        Bebidas.EligeBebida = objetosViewHolder.titulo.getText().toString();
                                        Menu_Completo.bebidaMenuComleto = "0";
                                        startActivity(intentT);
                                    } finally {
                                        finish();
                                    }
                                } else if (Comidas.bebidas_comida.equals("1")) {
                                    try {
                                        Intent intent2 = new Intent(view.getContext(), Pedidos_Comidas.class);
                                        Bundle extras = new Bundle();
                                        System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringComidaTitulo", objetos.getNombre());
                                        extras.putString("variable_stringComidaPrecio", objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringAlergenos", recuperamos_variable_string);
                                        intent2.putExtras(extras);
                                        Comidas.bebidas_comida = "0";
                                        startActivity(intent2);
                                    } finally {
                                        finish();
                                    }
                                } else {
                                    try {
                                        Intent intent2 = new Intent(view.getContext(), Pedidos.class);
                                        Bundle extras = new Bundle();
                                        System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringTostadaTitulo", objetos.getNombre());
                                        extras.putString("variable_stringTostadaPrecio", objetosViewHolder.Precio.getText().toString());
                                        extras.putString("variable_stringAlergenos", recuperamos_variable_string);
                                        intent2.putExtras(extras);
                                        startActivity(intent2);
                                    } finally {
                                        finish();
                                    }
                                }
                            }
                        });

                    }

                }
            }


            @Override
            public ObjetosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bebidas_rc_3, parent, false);

                return new ObjetosViewHolder(view);
            }
        };

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerview.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();

        mListState = recyclerview.getLayoutManager().onSaveInstanceState();

        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recyclerview.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);
        }

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerview.setLayoutManager(gridLayoutManager);
    }
}

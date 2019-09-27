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

import com.example.inncoffee.Model.Objetos_PlatosTapas;
import com.example.inncoffee.R;
import com.example.inncoffee.ViewHolder.ObjetosViewHolderPlatosTapas;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.inncoffee.View.Comidas.recuperamos_variable_stringPT;

//import static com.example.inncoffee.View.Tipo_Tostadas.recuperamos_variable_string;

public class PlatosyTapas extends AppCompatActivity {
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
    FirebaseRecyclerOptions<Objetos_PlatosTapas> options;
    FirebaseRecyclerAdapter<Objetos_PlatosTapas, ObjetosViewHolderPlatosTapas> adapter;
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
        //System.out.println("ESta es una variable string" + recuperamos_variable_stringPT.toString());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("comida").child("platosTapas");
        options = new FirebaseRecyclerOptions.Builder<Objetos_PlatosTapas>().setQuery(databaseReference, Objetos_PlatosTapas.class).build();
        // List<String> myList = new ArrayList<String>(Arrays.asList(recuperamos_variable_string.split(",")));
        adapter = new FirebaseRecyclerAdapter<Objetos_PlatosTapas, ObjetosViewHolderPlatosTapas>(options) {
            @Override
            protected void onBindViewHolder(final ObjetosViewHolderPlatosTapas objetosViewHolder, int i, final Objetos_PlatosTapas objetos) {
                if (recuperamos_variable_stringPT == null) {
                    recuperamos_variable_stringPT = "no";
                }
                System.out.println("ESta es una variable string" + recuperamos_variable_stringPT);
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
                        objetosViewHolder.Precio.setText("0");
                        System.out.println("PREEEEEEECIO " + objetosViewHolder.Precio.getText().toString());
                        objetosViewHolder.Suma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorSuma = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalSuma = valorSuma + 1;
                                Double valorPrecioSuma = Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                Double sumaTotal = valorPrecioSuma + Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) >= Double.parseDouble(objetosViewHolder.Precio.getText().toString())) {

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
                                Double totalPrecioResta = valorPrecioResta - Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) == Double.parseDouble(objetosViewHolder.Precio.getText().toString())) {
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
                        //
                        objetosViewHolder.btPlato.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                objetosViewHolder.btPlato.setEnabled(false);
                                objetosViewHolder.btTapa.setEnabled(true);
                                objetosViewHolder.Precio.setText(objetos.getPrecioPlato().toString());
                            }
                        });
                        objetosViewHolder.btTapa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                objetosViewHolder.btTapa.setEnabled(false);
                                objetosViewHolder.btPlato.setEnabled(true);
                                objetosViewHolder.Precio.setText(objetos.getPrecioTapa().toString());
                            }
                        });

                        objetosViewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent2 = new Intent(view.getContext(), Pedidos_Comidas.class);
                                Bundle extras = new Bundle();
                                System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                if (objetosViewHolder.btPlato.isEnabled() == true) {
                                    extras.putString("variable_stringComidaTitulo", objetos.getNombre() + "/tapa");
                                } else {
                                    extras.putString("variable_stringComidaTitulo", objetos.getNombre() + "/plato");
                                }

                                extras.putString("variable_stringComidaPrecio", objetosViewHolder.Precio.getText().toString());
                                // extras.putString("variable_stringAlergenos",recuperamos_variable_string);
                                intent2.putExtras(extras);
                                startActivity(intent2);

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
                        objetosViewHolder.Precio.setText("0");
                        System.out.println("PREEEEEEECIO " + objetosViewHolder.Precio.getText().toString());
                        //objetosViewHolder.btSelec.setText("Seleccionar Pan");
                        precioUnidad = Float.parseFloat("0");
                        // CantidadInicial = objetosViewHolder.Cantidad.getText();
                        // final String recuperamos_variable_string = getIntent().getStringExtra("variable_string1");

                        // objetosViewHolder.Cantidad.setText('0');

                        objetosViewHolder.Suma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorSuma = Integer.parseInt(objetosViewHolder.Cantidad.getText().toString());
                                int totalSuma = valorSuma + 1;
                                Double valorPrecioSuma = Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                // FloaotalPrecioSuma=valorPrecioSuma+precioUnidad;
                                // t tDecimalFormat formato1 = new DecimalFormat("#.00");
                                Double sumaTotal = valorPrecioSuma + Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) >= Double.parseDouble(objetosViewHolder.Precio.getText().toString())) {

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
                                Double totalPrecioResta = valorPrecioResta - Double.parseDouble(objetosViewHolder.Precio.getText().toString());
                                if (Float.parseFloat(objetosViewHolder.Precio.getText().toString()) == Double.parseDouble(objetosViewHolder.Precio.getText().toString())) {
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
                        //
                        objetosViewHolder.btPlato.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                objetosViewHolder.btPlato.setEnabled(false);
                                objetosViewHolder.btTapa.setEnabled(true);
                                objetosViewHolder.Precio.setText(objetos.getPrecioPlato().toString());
                            }
                        });
                        objetosViewHolder.btTapa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                objetosViewHolder.btPlato.setEnabled(false);
                                objetosViewHolder.btTapa.setEnabled(true);
                                objetosViewHolder.Precio.setText(objetos.getPrecioTapa().toString());
                            }
                        });

                        objetosViewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent intent2 = new Intent(view.getContext(), Pedidos_Comidas.class);
                                    Bundle extras = new Bundle();
                                    System.out.println("PREEEEEEECIO 2 " + objetosViewHolder.Precio.getText().toString());
                                    if (objetosViewHolder.btPlato.isEnabled() == true) {
                                        extras.putString("variable_stringComidaTitulo", objetos.getNombre() + "/tapa");
                                    } else {
                                        extras.putString("variable_stringComidaTitulo", objetos.getNombre() + "/plato");
                                    }
                                    extras.putString("variable_stringComidaPrecio", objetosViewHolder.Precio.getText().toString());
                                    //extras.putString("variable_stringAlergenos",recuperamos_variable_string);
                                    intent2.putExtras(extras);
                                    startActivity(intent2);
                                } finally {
                                    finish();
                                }
                            }
                        });

                    }

                }
            }


            @Override
            public ObjetosViewHolderPlatosTapas onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_platos_y_tapas_rc, parent, false);

                return new ObjetosViewHolderPlatosTapas(view);
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

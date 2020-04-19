package com.example.inncoffee.ui.TipoTe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mispedidos.MisPedidosClass;
import com.example.inncoffee.ui.mispedidos.MisPedidosSinFinalizar;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.example.inncoffee.ui.tostadas.TostadasDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Infusiones extends Fragment {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    public static long id= 0;
    private long idpanes = 0;
    TostadasDB tostadasdb;
    private Button boton,next, selecionarpan, selecionarpan1;
    private boolean MediaoEntera = true;
    private boolean SelecionaPan = true;
    private ImageView Imagen;
    private int contador2 = 1;
    private TextView contador;
    private String ID ;
    private DatabaseReference mUsuario;
    private DatabaseReference mCompare;
    private static final String USERS = "MisPedidos";
    private ImageView menos,plus;
    public static TextView nombreArticulo,precio,desca,añadir;
    private ArrayList<TostadasDB> mtos = new ArrayList<>();
    private String nombre,nombreleche,imagen,imagenpan,precios,barra,precioLeche,desc;



    private void inicialize() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(getActivity(), QuieroFragment.class);
                    startActivity(intent);
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


                    Intent intent = new Intent(getActivity(), QuieroFragment.class);
                    startActivity(intent);


                    Log.w("TAG", "onAuthStateChanged - Logueado");

                } else {
                    Log.w("TAG", "onAuthStateChanged - Cerro sesion");
                }
            }
        };

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.te_infusiones, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTosta = mDatabase.getReference("Te");
        mCompare = mDatabase.getReference();
        tostadasdb = new TostadasDB();
        desca = (TextView) root.findViewById(R.id.descripcionarticulo);
        Imagen = (ImageView) root.findViewById(R.id.imagentostada);
        nombreArticulo = (TextView) root.findViewById(R.id.nombrearticulo);
        precio = (TextView) root.findViewById(R.id.precio);
        selecionarpan = (Button) root.findViewById(R.id.selecionarpan);
        selecionarpan1 = (Button) root.findViewById(R.id.selecionarpan1);
        contador = (TextView) root.findViewById(R.id.textView5);
        menos = (ImageView) root.findViewById(R.id.imagecontador2);
        plus = (ImageView) root.findViewById(R.id.imagecontador1);
        añadir = (TextView) root.findViewById(R.id.añadir);
        mUsuario = mDatabase.getReference(USERS);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        contador.setText(String.valueOf(contador2));

        if (contador2 == 1) {
            menos.setVisibility(View.INVISIBLE);
        }
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2--;
                contador.setText(String.valueOf(contador2));
                if (contador2 == 1) {
                    menos.setVisibility(View.INVISIBLE);
                } else if (contador2 < 99) {

                    plus.setVisibility(View.VISIBLE);
                }

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("que pasa", String.valueOf(contador2));
                contador2++;
                contador.setText(String.valueOf(contador2));
                if (contador2 == 99) {
                    plus.setVisibility(View.INVISIBLE);
                    menos.setVisibility(View.VISIBLE);
                } else if (contador2 > 1) {
                    menos.setVisibility(View.VISIBLE);

                }

            }
        });


        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (TextUtils.isEmpty(nombreleche)) {
                    Toast.makeText(getContext(), "Selecione Una Leche", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ID = mAuth.getUid();
                    final String key3 = mUsuario.push().getKey();
                    mUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            double total = 0;
                            String processed = "";
                                if (contador2 == 1) {
                                    String texto = contador2 + " /"  + nombre + "/" + nombreleche;
                                    double number = Double.valueOf(precios.replaceAll("[,.€]", ""));
                                    double number2 = Double.valueOf(precioLeche.replaceAll("[,.€]", ""));
                                    total = total + number + number2;
                                    NumberFormat formatter = new DecimalFormat("###,##€");

                                    processed = formatter.format(total);
                                    String precio = processed;
                                    MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                    mUsuario.child("PedidosSinFinalizar").child(ID).child(key3).setValue(user2);
                                    mUsuario.child("PedidosFinalizados").child(ID).child(key3).setValue(user2);

                                } else if (contador2 > 1) {
                                    String texto = contador2 + " /" + nombre + "/" + nombreleche;
                                    double number = Double.valueOf(precios.replaceAll("[,.€]", ""));
                                    double number2 = Double.valueOf(precioLeche.replaceAll("[,.€]", ""));
                                    total = total + number + number2 * contador2;
                                    NumberFormat formatter = new DecimalFormat("###,##€");

                                    processed = formatter.format(total);

                                    String precio = processed;
                                    MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                                    mUsuario.child("PedidosSinFinalizar").child(ID).child(key3).setValue(user2);
                                    mUsuario.child("PedidosFinalizados").child(ID).child(key3).setValue(user2);


                            }

                            MisPedidosSinFinalizar fragment = new MisPedidosSinFinalizar();
                            FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                            ftEs.replace(R.id.nav_host_fragment, fragment);
                            ftEs.addToBackStack(null);
                            ftEs.commit();
                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });


                }

            }
        });



        selecionarpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarpan.setVisibility(View.INVISIBLE);
                selecionarpan1.setVisibility(View.VISIBLE);
                SelecionaPan = false;
                añadir.setVisibility(View.INVISIBLE);

                mTosta.child("TipoLeches").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            idpanes = 1;
                            nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                            barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                            precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                            imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                            Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                            nombreArticulo.setText(nombreleche);
                            precio.setText(precioLeche);

                            Log.v("NONBRE ARTICULO ", nombreleche);
                            Log.v("MI ID ", String.valueOf(idpanes));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ID = mAuth.getUid();
                mCompare.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                        Log.v("Es Trigo" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Leches").getValue() +" // "+ dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue() );
                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                        if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue()))
                        {
                            selecionarpan1.setVisibility(View.INVISIBLE);
                            añadir.setVisibility(View.INVISIBLE);
                        }
                        else
                            selecionarpan1.setVisibility(View.VISIBLE);

                    }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        selecionarpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarpan.setVisibility(View.VISIBLE);
                selecionarpan1.setVisibility(View.INVISIBLE);
                selecionarpan.setText(barra);
                SelecionaPan = true;
                añadir.setVisibility(View.VISIBLE);

                if (SelecionaPan == true) {

                        mTosta.child("Infusiones").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                    desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                                    desca.setText(desc);
                                    nombreArticulo.setText(nombre);
                                    precio.setText(precios);

                                    Log.v("NONBRE ARTICULO ", nombre);
                                    Log.v("MI ID ", String.valueOf(id));

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        ID = mAuth.getUid();
                        mCompare.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue() );
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                            dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue()))
                                    {
                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

        });


       if (SelecionaPan == true) {

           mTosta.child("Infusiones").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()) {
                           id = dataSnapshot.getChildrenCount();
                           id = 1;
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           mTosta.child("Infusiones").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()) {
                           id = 1;
                           nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                           precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                           imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                           Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                           desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                           desca.setText(desc);
                           nombreArticulo.setText(nombre);
                           precio.setText(precios);

                           Log.v("NONBRE ARTICULO ", nombre);
                           Log.v("MI ID ", String.valueOf(id));

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
               ID = mAuth.getUid();
               mCompare.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                       Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue() );
                       if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                           if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                   dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue()))
                           {
                               añadir.setVisibility(View.INVISIBLE);
                           }
                           else{
                               añadir.setVisibility(View.VISIBLE);
                           }
                       }

                   }

                   @Override
                   public void onCancelled (@NonNull DatabaseError databaseError) {

                   }
               });

       }
       else if (SelecionaPan == false){
           mTosta.child("TipoLeches").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()) {
                       idpanes = dataSnapshot.getChildrenCount();
                       idpanes = 1;

                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
           mTosta.child("TipoLeches").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.exists()) {
                       idpanes = 1;
                       nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                       barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                       precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                       imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                       Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                       nombreArticulo.setText(nombreleche);
                       precio.setText(precioLeche);

                       Log.v("NONBRE ARTICULO ", nombreleche);
                       Log.v("MI ID ", String.valueOf(idpanes));

                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
           ID = mAuth.getUid();
           mCompare.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                   Log.v("Es Trigo" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue() );
                   if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                       if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                               dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue()))
                       {
                           selecionarpan1.setVisibility(View.INVISIBLE);
                           añadir.setVisibility(View.INVISIBLE);
                       }
                       else
                           selecionarpan1.setVisibility(View.VISIBLE);
                   }

               }

               @Override
               public void onCancelled (@NonNull DatabaseError databaseError) {

               }
           });


       }



        next = (Button) root.findViewById(R.id.test);

         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (SelecionaPan == true) {
                     mTosta.child("Infusiones").addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (dataSnapshot.exists()) {
                                     id++;
                                     nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                     precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                     imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                     Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                     desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                                     desca.setText(desc);
                                     nombreArticulo.setText(nombre);
                                     precio.setText(precios);

                                     if (id == 7) {
                                         id = 1;
                                         nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                         precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                         imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                         Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                         desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                                         desca.setText(desc);
                                         nombreArticulo.setText(nombre);
                                         precio.setText(precios);
                                     }
                                     Log.v("MI ID ", String.valueOf(id));
                                     Log.v("NONBRE ARTICULO ", nombre);

                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         ID = mAuth.getUid();
                         mCompare.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                 Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue() );
                                 if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                     if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                             dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue()))
                                     {
                                         añadir.setVisibility(View.INVISIBLE);
                                     }
                                     else{
                                         añadir.setVisibility(View.VISIBLE);
                                     }
                                 }

                             }

                             @Override
                             public void onCancelled (@NonNull DatabaseError databaseError) {

                             }
                         });


                 }
                 else if (SelecionaPan == false){

                     mTosta.child("TipoLeches").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             if (dataSnapshot.exists()) {
                                 idpanes++;
                                 nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                 barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                 precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                                 imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                 Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                 nombreArticulo.setText(nombreleche);
                                 precio.setText(precioLeche);

                                 if (idpanes == 7) {
                                     idpanes = 1;
                                      nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                      barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                      precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                                      imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                     Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                     nombreArticulo.setText(nombreleche);
                                     precio.setText(precioLeche);
                                 }
                                 Log.v("MI ID ", String.valueOf(idpanes));
                                 Log.v("NONBRE ARTICULO ", nombreleche);

                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                     ID = mAuth.getUid();
                     mCompare.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                             Log.v("Es Trigo" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue() );
                             if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                 if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                         dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue()))
                                 {
                                     selecionarpan1.setVisibility(View.INVISIBLE);
                                     añadir.setVisibility(View.INVISIBLE);
                                 }
                                 else
                                     selecionarpan1.setVisibility(View.VISIBLE);
                             }
                         }

                         @Override
                         public void onCancelled (@NonNull DatabaseError databaseError) {

                         }
                     });

                 }

             }
         });


        boton = (Button) root.findViewById(R.id.button5);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelecionaPan == true) {

                    mTosta.child("Infusiones").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    id--;
                                    nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                    precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                    imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                    Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                    desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                                    desca.setText(desc);
                                    nombreArticulo.setText(nombre);
                                    precio.setText(precios);

                                    if (id == 0) {
                                        id = 6;
                                        nombre = dataSnapshot.child(String.valueOf(id)).child("nombrearticulo").getValue().toString();
                                        precios = dataSnapshot.child(String.valueOf(id)).child("precio").getValue().toString();
                                        imagen = dataSnapshot.child(String.valueOf(id)).child("imagen").getValue().toString();
                                        Glide.with(Objects.requireNonNull(getContext())).load(imagen).into(Imagen);
                                        desc = dataSnapshot.child(String.valueOf(id)).child("descarticulo").getValue().toString();
                                        desca.setText(desc);
                                        nombreArticulo.setText(nombre);
                                        precio.setText(precios);
                                    }
                                    Log.v("MI ID ", String.valueOf(id));
                                    Log.v("NONBRE ARTICULO ", nombre);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        ID = mAuth.getUid();
                        mCompare.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                                Log.v("Es Lacteos" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue() );
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").exists()){
                                    if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue().equals(
                                            dataSnapshot.child("Te").child(String.valueOf(id)).child("Alergia").getValue()))
                                    {
                                        añadir.setVisibility(View.INVISIBLE);
                                    }
                                    else{
                                        añadir.setVisibility(View.VISIBLE);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError databaseError) {

                            }
                        });


                }
                else if (SelecionaPan == false ){

                    mTosta.child("TipoLeches").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                idpanes--;
                                nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                                imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                nombreArticulo.setText(nombreleche);
                                precio.setText(precioLeche);

                                if (idpanes == 0) {
                                    idpanes = 6;
                                    nombreleche = dataSnapshot.child(String.valueOf(idpanes)).child("tipo").getValue().toString();
                                     barra = dataSnapshot.child(String.valueOf(idpanes)).child("barra").getValue().toString();
                                    precioLeche = dataSnapshot.child(String.valueOf(idpanes)).child("precio").getValue().toString();
                                    imagenpan = dataSnapshot.child(String.valueOf(idpanes)).child("imagen").getValue().toString();
                                    Glide.with(Objects.requireNonNull(getContext())).load(imagenpan).into(Imagen);
                                    nombreArticulo.setText(nombreleche);
                                    precio.setText(precioLeche);
                                }
                                Log.v("MI ID ", String.valueOf(idpanes));
                                Log.v("NONBRE ARTICULO ", nombreleche);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    ID = mAuth.getUid();
                    mCompare.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            Log.v("Es Trigo" ,dataSnapshot.child("Users").child(ID).child("Alergias").child("Lacteos").getValue() +" // "+ dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue() );
                            if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").exists()){
                                if (dataSnapshot.child("Users").child(ID).child("Alergias").child("Trigo").getValue().equals(
                                        dataSnapshot.child("Te").child("TipoLeches").child(String.valueOf(idpanes)).child("Alergia").getValue()))
                                {

                                    selecionarpan1.setVisibility(View.INVISIBLE);
                                    añadir.setVisibility(View.INVISIBLE);
                                }
                                else
                                    selecionarpan1.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });



        inicialize();
        return root;
    }
}

package com.example.inncoffee.ui.quiero;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.RegistroLogin.Registro;
import com.example.inncoffee.ui.home.HomeFragment1;
import com.example.inncoffee.ui.mispedidos.AdapterPedidos;
import com.example.inncoffee.ui.mispedidos.MisPedidosClass;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.IPaymentResult;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.ResultResponse;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVV;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley.TPVVRequestQueue;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FinalizarPedido extends Fragment {


    private Button Cancelar,Pagar,MisPuntos;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseDatabase mDatabase;
    private ArrayList<MisPedidosClass> mMensaje = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();

    private String ID ;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private DatabaseReference mUsuario;
    private DatabaseReference mUsuarios;
    private DatabaseReference mComanda;
    private DatabaseReference mPedido;
    private static final String USERS = "MisPedidos";
    private AdapterPedidos mAdapter;
    private RecyclerView mPedidos;
    private TextView sumatotal;
    private String texto,precios,que;
    private String importe;
    private String reference, fname;
    private String language = null;
    private String tipoOperacion = "0";
    private String urlKO = null;
    private String urlOK = null;
    private double number;
    private double total = 0;
    private double totals = 0;
    private double puntostotal = 0;
    private double puntos;
    private double puntoss;
    private String processeds = "";
    private String processedss = "";
    private String processed = "";
    private String Comanda;
    public static int NumeroLista;





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

        View root = inflater.inflate(R.layout.finalizarpedido, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        mPedidos = (RecyclerView) root.findViewById(R.id.VerpedidoComidas);
        mPedidos.setLayoutManager(new LinearLayoutManager(getActivity()));
        sumatotal = (TextView) root.findViewById(R.id.total5) ;
        mDatabase = FirebaseDatabase.getInstance();
        MisPuntos = (Button) root.findViewById(R.id.mispuntos);
        Pagar = (Button) root.findViewById(R.id.pagar);
        configuracionLibreria();
        mUsuario = mDatabase.getReference(USERS);
        mUsuarios = mDatabase.getReference("Users");
        mPedido = mDatabase.getReference("PedidoPagado");
        mComanda = mDatabase.getReference("Comanda");
        TPVVRequestQueue.mContext = getActivity().getApplicationContext();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        Cancelar = (Button) root.findViewById(R.id.cancelar);
        Cancelare();
        inicialize();
        MisPuntos();
        PuntosAcumulado();
        Puntos();
        Pagare();
        Comanda = getRandomOrderCode();
        getMensajesFromFirebase();


        return root;
    }
    private String getRandomOrderCode() {
        char[] charArray = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }
    private void PuntosAcumulado(){

        ID = mAuth.getUid();
        assert ID != null;
        mUsuarios.child(ID).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("DineroAcumulados").exists()){
                        ID = mAuth.getUid();
                        assert ID != null;
                        puntoss = Double.parseDouble(dataSnapshot.child("DineroAcumulados").getValue(String.class).replaceAll("[,.€]", ""));
                        NumberFormat formatter = new DecimalFormat("0,00€");
                        processedss = formatter.format(puntoss);
                        Log.v("QUE TENGO ACUMULADO : ", processedss);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Puntos(){
        ID = mAuth.getUid();
        assert ID != null;
        mUsuarios.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("DineroAcumulados").exists()){
                    for (DataSnapshot ds : dataSnapshot.child("DineroAcumulado").getChildren()) {
                        ID = mAuth.getUid();
                        assert ID != null;
                        double suma = Double.parseDouble(dataSnapshot.child("DineroAcumulados").getValue(String.class).replaceAll("[,.€]", ""));
                        puntos = Double.parseDouble(ds.getValue(String.class).replaceAll("[,.€]", ""));
                        puntostotal = puntostotal + puntos + suma;

                        NumberFormat formatter = new DecimalFormat("0,00€");

                        processeds = formatter.format(puntostotal);
                        Log.v("QUE MAS TENGO : ", processeds);
                        mUsuarios.child(ID).child("DineroAcumulados").setValue(processeds);
                        mUsuarios.child(ID).child("DineroAcumulado").removeValue();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void showResult(String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage((CharSequence) str).setTitle((CharSequence) str2);
        builder.create().show();
    }

    private void Pagare(){



        Pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                TPVV.doWebViewPayment(getContext(),  String.valueOf(Comanda), Double.valueOf(importe), TPVVConstants.PAYMENT_TYPE_NORMAL, null, "Desayuno InnCoffee" , new IPaymentResult() {
                    @Override
                    public void paymentResultKO(ErrorResponse errorResponse) {
                        showResult(errorResponse.toString(), "Result KO");
                        Toast.makeText(getActivity(), "Fallo En El Pago", Toast.LENGTH_LONG).show();
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosFinalizados").child(ID);
                        ref1.removeValue();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void paymentResultOK(ResultResponse resultResponse) {
                        showResult(resultResponse.toString(), "Result OK");
                        double Tengo = (number * Double.parseDouble("5") /100);
                        NumberFormat formatter = new DecimalFormat("0,00€");
                        String processedsd = formatter.format(Tengo);
                        Log.v("CUANTOS PUNTOS CONSIGO ",processedsd);
                        mUsuarios.child(ID).child("DineroAcumulado").child(Comanda).setValue(processedsd);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date date = new Date();
                        Map<String, Object> newPost = new HashMap<>();
                        newPost.put("Fecha", dateFormat.format(date));
                        newPost.put("PuntosAcumulado", processedsd);
                        newPost.put("NumeroComanda", Comanda);
                        newPost.put("Precio", processed);
                        mUsuarios.child(ID).child("Puntos").child(Comanda).setValue(newPost);
                        mComanda.child(ID).child("Imprimir").child(Comanda).setValue(newPost);



                      /*  String key=mPedido.push().getKey();
                        mPedido.child(ID).child(key).child("texto").setValue("// "+texto +" // "+ precios +" // Pagado Con Targeta"+" //  Precio Total: "+ processed + " //");

                        mPedido.child(ID).child(key).child("orden").setValue(Comanda);*/
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosFinalizados").child(ID);
                        ref1.removeValue();
                     /*   MisPedidos fragment = new MisPedidos();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();*/

                        if (resultResponse.getIdentifier() != null) {
                            ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("reference", resultResponse.getIdentifier().toString()));
                            Toast.makeText(getActivity(), "La referencia se ha copiado en el portapapeles", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });

    }

    private void MisPuntos(){

        MisPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("QUE CUANTOS PUNTOS", puntoss + " // "+ number);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setMessage("Deseas Utilizar Los CoINNs Acumulados que Tienes: " + processedss );
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        if (puntoss < number){
                            Toast.makeText(getActivity(),"No tienes Suficente INNCOOFEEcoins ", Toast.LENGTH_LONG).show();
                        }else if(puntoss >= number){
                        ID = mAuth.getUid();
                        assert ID != null;
                        mUsuarios.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String processed = "";
                                if (dataSnapshot.exists()) {

                                        total = puntoss - total;
                                        NumberFormat formatter = new DecimalFormat("0,00€");
                                        processed = formatter.format(total);
                                        Log.v("QUE DA: ",processed);
                                        mUsuarios.child(ID).child("DineroAcumulados").setValue(processed);
                                        processeds = formatter.format(puntoss);

                                    }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                     /*   String key=mPedido.push().getKey();
                        mPedido.child(ID).child(key).child("texto2").setValue("// Pagado Con CoINNs"+" //  Precio Total: "+ processed + " //");
                        mPedido.child(ID).child(key).child("orden").setValue(Comanda);*/
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosFinalizados").child(ID);
                        ref1.removeValue();
                       /* MisPedidos fragment = new MisPedidos();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();*/

                    } }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                       /* double Tengo = (number * Double.parseDouble("5") /100);
                        NumberFormat formatter = new DecimalFormat("0,00€");
                        String processedsd = formatter.format(Tengo);
                        Log.v("CUANTOS PUNTOS CONSIGO ",processedsd);
                        mUsuarios.child(ID).child("DineroAcumulado").child(Comanda).setValue(processedsd);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date date = new Date();
                        Map<String, Object> newPost = new HashMap<>();
                        newPost.put("Fecha", dateFormat.format(date));
                        newPost.put("PuntosAcumulado", processedsd);
                        newPost.put("NumeroComanda", Comanda);
                        newPost.put("Precio", processed);

                        mUsuarios.child(ID).child("Puntos").child(Comanda).setValue(newPost);
                        mComanda.child(ID).child("Imprimir").child(Comanda).setValue(newPost);*/
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }
        });

    }

    private void moveRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.child("Comanda").child(ID).child("Imprimir").child("Lista").child(String.valueOf(NumeroLista++)).setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Log.d("TAG", "Success!");
                        } else {
                            Log.d("TAG", "Copy failed!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        fromPath.child("MisPedidos").child("PedidosFinalizados").child(ID).addListenerForSingleValueEvent(valueEventListener);
    }

    private void Cancelare(){

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
                dialogo1.setMessage("Desear Cancelar este Pedido");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        mUser = FirebaseAuth.getInstance().getCurrentUser();
                        mAuth = FirebaseAuth.getInstance();
                        ID = mAuth.getUid();
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("MisPedidos").child("PedidosFinalizados").child(ID);
                        ref1.removeValue();
                        HomeFragment1 fragment = new HomeFragment1();
                        FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                        ftEs.replace(R.id.nav_host_fragment, fragment);
                        ftEs.addToBackStack(null);
                        ftEs.commit();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();

            }
        });

    }

    private void getMensajesFromFirebase() {

        ID = mAuth.getUid();
        mUsuario.child("PedidosFinalizados").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    mMensaje.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        number = Double.parseDouble(ds.child("precio").getValue(String.class).replaceAll("[,.€]", ""));
                        total = total + number;

                        NumberFormat formatter = new DecimalFormat("0,00€");
                        processed = formatter.format(total);
                        importe = Double.valueOf(total).toString();
                        Log.v("QUe importe es", importe);
                    //    Log.v("LOG CACHE :", String.valueOf(getActivity().getCacheDir()));

                        sumatotal.setText(processed);

                        texto = ds.child("texto").getValue().toString();
                        precios = ds.child("precio").getValue().toString();

                        mMensaje.add(new MisPedidosClass(texto, precios));
                        keys.add(ds.getKey());

                        String[] blist = new String[5];
                        for(int i=0;i<mMensaje.size();i++){
                            blist[i]= String.valueOf(mMensaje.get(i));
                        }
                         que = Arrays.toString(blist);

                    }


                    mAdapter = new AdapterPedidos(getActivity(), mMensaje, keys, R.layout.contenido_mispedidos);
                    mPedidos.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setDefaultConfig() {
        if (TPVVConfiguration.getCurrency() == null) {
            TPVVConfiguration.setCurrency("978");
        }
    }
    private void configLicenses() {
        TPVVConfiguration.setFuc("351003009");
        TPVVConfiguration.setTerminal("1");
        TPVVConfiguration.setEnvironment(TPVVConstants.ENVIRONMENT_REAL) ;
        TPVVConfiguration.setLicense("862CQBjeHi7ZwpyNcq2Q");

    }


    private void configuracionLibreria() {
        setDefaultConfig();
        configLicenses();
        try {
            ProviderInstaller.installIfNeeded(getContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e2) {
            e2.printStackTrace();
        }
        TPVVConfiguration.setProgressBarColor("#AC1C43");
        TPVVConfiguration.setEnableResultAlert(true);
        TPVVConfiguration.setResultAlertTextButtonOk("Continuar");
        TPVVConfiguration.setResultAlertTextButtonKo("Continuar");
        TPVVConfiguration.setResultAlertTextOk("Operación realizada correctamente.");
        TPVVConfiguration.setResultAlertTextKo("Se ha producido un error al intentar realizar la operación.");
        TPVVConfiguration.setProgressBarColor("#FE9A2E");
    }
}

package com.example.inncoffee.ui.quiero;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
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
import java.util.Random;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
    private static final String USERS = "MisPedidos";
    private AdapterPedidos mAdapter;
    private RecyclerView mPedidos;
    private TextView sumatotal;
    private String texto,precios;
    private String importe;
    private String reference;
    private String language = null;
    private String tipoOperacion = "0";
    private String urlKO = null;
    private String urlOK = null;
    private double number;
    private double total = 0;




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
        configuracionLibreria();
        mUsuario = mDatabase.getReference(USERS);
        TPVVRequestQueue.mContext = getActivity().getApplicationContext();
        Pagar = (Button) root.findViewById(R.id.pagar);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        Cancelar = (Button) root.findViewById(R.id.cancelar);
        Cancelare();
        inicialize();

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


    public void showResult(String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage((CharSequence) str).setTitle((CharSequence) str2);
        builder.create().show();
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
                String processed = "";
                if (dataSnapshot.exists()) {
                    mMensaje.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        number = Double.parseDouble(ds.child("precio").getValue(String.class).replaceAll("[,.€]", ""));
                        total = total + number;

                        NumberFormat formatter = new DecimalFormat("##,##");
                        processed = formatter.format(total);
                        importe = Double.valueOf(total).toString();
                        Log.v("QUe importe es", String.valueOf(Double.valueOf(importe)));
                        Log.v("LOG CACHE :", String.valueOf(getActivity().getCacheDir()));

                        sumatotal.setText(processed);

                        texto = ds.child("texto").getValue().toString();
                        precios = ds.child("precio").getValue().toString();

                        mMensaje.add(new MisPedidosClass(texto,precios));
                        keys.add(ds.getKey());

                    }


                    mAdapter = new AdapterPedidos(getActivity(), mMensaje, keys, R.layout.contenido_mispedidos);
                    mPedidos.setAdapter(mAdapter);
                  /*  mAdapter.setOnItemClickListener(new AdapterPedidos.OnItemClickListener() {

                        @Override
                        public void onItemClick(final int position) {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext(), position);
                            dialogo1.setMessage("Desear Borrar esta Linea");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    removeItem(position);
                                }
                            });
                            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    dialogo1.cancel();
                                }
                            });
                            dialogo1.show();

                        }

                    });*/
                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPVV.doWebViewPayment(getContext(),  getRandomOrderCode(), Double.valueOf(100), TPVVConstants.PAYMENT_TYPE_NORMAL, null, "descripcion" , new IPaymentResult() {
                    @Override
                    public void paymentResultKO(ErrorResponse errorResponse) {
                        showResult(errorResponse.toString(), "Result KO");
                    }

                    @Override
                    public void paymentResultOK(ResultResponse resultResponse) {
                        showResult(resultResponse.toString(), "Result OK");
                        if (resultResponse.getIdentifier() != null) {
                            ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("reference", resultResponse.getIdentifier().toString()));
                            Toast.makeText(getActivity(), "La referencia se ha copiado en el portapapeles", Toast.LENGTH_LONG).show();
                        }
                    }
                });
              /*  Log.v("LOG:  ",getActivity() +"/"+ getRandomOrderCode()+"/"+ Double.valueOf(importe)+"/"+ TPVVConstants.PAYMENT_TYPE_NORMAL+"/"+ null +"/"+ "PRueba1");
                TPVV.doWebViewPayment(getActivity(), getRandomOrderCode(), Double.valueOf(importe), TPVVConstants.PAYMENT_TYPE_NORMAL, TPVVConstants.REQUEST_REFERENCE, "descripción", new IPaymentResult() {
                    @Override
                    public void paymentResultKO(ErrorResponse errorResponse) {
                        showResult(errorResponse.toString(), "Result KO");
                    }

                    @Override
                    public void paymentResultOK(ResultResponse resultResponse) {
                        showResult(resultResponse.toString(), "Result OK");
                        Log.v("QUEPASO ", resultResponse.toString());
                        if (resultResponse.getIdentifier() != null) {
                            ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("reference", resultResponse.getIdentifier().toString()));
                            Toast.makeText(getActivity(), "La referencia se ha copiado en el portapapeles", Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
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
        TPVVConfiguration.setTerminal("001");
        TPVVConfiguration.setEnvironment(TPVVConstants.ENVIRONMENT_REAL) ;
        TPVVConfiguration.setLicense("123456789a");

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

package com.rakshasa.inncoffee.ui.quiero;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rakshasa.inncoffee.MainActivity;
import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.combos.Combos;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosClass;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosSinFinalizarComidas;
import com.rakshasa.inncoffee.ui.mispedidos.MisPedidosSinFinalizarMerienda;
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterBebidasComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterLechesComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterDesayuno.AdapterBebidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterDesayuno.AdapterLeches;
import com.rakshasa.inncoffee.ui.quiero.AdapterMerienda.AdapterBebidasMerienda;
import com.rakshasa.inncoffee.ui.quiero.AdapterMerienda.AdapterLechesMerienda;
import com.rakshasa.inncoffee.ui.quiero.AdapterMerienda.AdapterMerienda;
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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartaMerienda extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ConstraintLayout MENU1,MENUa,Menu2,ventanaMenu1,ventanaMenu2,CartaComida,CartaMerienda, MENUVentana1,Ventana2,Comidas,VentanaBebidas,Bebidas,Cafes,Te,Combinados,Zumos,menu,
            ventanacafe,ventamate,ventanaconbi,ventanazumos,Cafesolo,ventanacafesolo,ventanaconbina,
            ventanacafeconleche,Cafeconleches,Cafeespecial,ventanacafeespecial,ventanaLeches,Leches,Tes,Desteinados,Infusiones,tescambio,desteinadoscambio,infusionescambio,Refresco,ResfrescoCambio,Ensalada,Ensaladaventana,Picoteo,Picoteoventana,Sandwiches,SanwichesVentana,Burger,Burgerventana;
    private RecyclerView ListaZumo,ListaBebidas,ListaCafeSolos,ListaCafeconLeches,ListaCafeespecial,ListaLeches,ListaTe,ListaDesteinados,ListaInfusiones,ListaRefresco,ListaMerienda;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mTosta,mTosta2,mZumos,mpedidos;
    private ArrayList<MisPedidosClass> mMenus = new ArrayList<>();
    private ArrayList<MisPedidosClass> mMensaje1 = new ArrayList<>();
    private ArrayList<MisPedidosClass> mMensaje2= new ArrayList<>();
    private ArrayList<MisPedidosClass> mMensaje3 = new ArrayList<>();
    private ArrayList<MisPedidosClass> mMensaje4 = new ArrayList<>();
    private ArrayList<MisPedidosClass> mZumo = new ArrayList<>();
    private ArrayList<MisPedidosClass> mBebidas = new ArrayList<>();
    private ArrayList<MisPedidosClass> mCafesolo = new ArrayList<>();
    private ArrayList<MisPedidosClass> mCafeconLeche = new ArrayList<>();
    private ArrayList<MisPedidosClass> mCafeEspecial = new ArrayList<>();
    private ArrayList<MisPedidosClass> mTe = new ArrayList<>();
    private ArrayList<MisPedidosClass> mDesteinados = new ArrayList<>();
    private ArrayList<MisPedidosClass> mInfusiones = new ArrayList<>();
    private ArrayList<MisPedidosClass> mLeches = new ArrayList<>();
    private ArrayList<MisPedidosClass> mRefresco = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private AdapterBebidasMerienda mAdapter1;
    private AdapterLechesMerienda mAdapter2;
    private AdapterMerienda mAdapter3;
    private Button VerPedido;
    private String ID ;
    private TextView sumatotal , PrecioMenu1,PrecioMenu2;
    private FirebaseUser mUser;
    private Spinner PrimerPlato,SegundoPlato,Postre,Bebidasmenu,Postre1,Bebidasmenu1,MedioPlato;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsuario;
    private ImageView Cambiomenu,Cambiomenu2, cambiobebidas,Cafem,Tem,Combinadosm,Zumosm,CafesoloCambio,CafeconLecheCambio,
            CafeespecialCambio,LecheCambio,cambiote,cambiodesteinados,cambioinfusiones,Refrescocambio,MenuC,Menua,añadir1,añadir2,ensalada,burger,picoteo,sandwiches;


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

        View root = inflater.inflate(R.layout.postres, container, false);
        MainActivity.mensajeToolbar.setText("PEDIDO / NUEVO PEDIDO");
        QuieroAlojenos.ComidaoDesayuno = 1;
        Combos.menua = 0;
        mDatabase = FirebaseDatabase.getInstance();
        mUsuario = mDatabase.getReference("MisPedidos");
        mTosta = mDatabase.getReference("CombosPrecios");
        mTosta2 = mDatabase.getReference();
        mZumos = mDatabase.getReference("Bebidas");
        VerPedido = (Button) root.findViewById(R.id.button4);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        menu = (ConstraintLayout) root.findViewById(R.id.menu);
        CartaMerienda = (ConstraintLayout) root.findViewById(R.id.Cartameriendas);
        CartaMerienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CartaComidas fragment = new CartaComidas();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });
        CartaComida = (ConstraintLayout) root.findViewById(R.id.CartaComidas);
        CartaComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CartaDesayunos fragment = new CartaDesayunos();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();
            }
        });

        VerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                MisPedidosSinFinalizarMerienda dialog = new MisPedidosSinFinalizarMerienda();
                dialog.show(getParentFragmentManager(), "Dialog" );
            }
        });




        Cambiomenu = (ImageView) root.findViewById(R.id.cambiomenu);
        Cambiomenu2 = (ImageView) root.findViewById(R.id.cambiomenu2);
        cambiobebidas = (ImageView) root.findViewById(R.id.cambioBebidas2);
        Cafem = (ImageView) root.findViewById(R.id.cambiomenu3) ;
        sumatotal = (TextView) root.findViewById(R.id.total8);
        Refresco =(ConstraintLayout) root.findViewById(R.id.Refrescos);
        ResfrescoCambio = (ConstraintLayout) root.findViewById(R.id.ventanaRefresco);
        Refrescocambio = (ImageView) root.findViewById(R.id.Refrescoscambio);
        ListaRefresco = (RecyclerView) root.findViewById(R.id.ListaRefresco);
        ListaRefresco.setLayoutManager(new LinearLayoutManager(getContext()));
        Refresco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ResfrescoCambio.getLayoutParams().height == 1){
                    ResfrescoCambio.getLayoutParams().height = 550;
                    ResfrescoCambio.requestLayout();
                    Refrescocambio.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ResfrescoCambio.getLayoutParams().height == 550){
                    ResfrescoCambio.getLayoutParams().height = 1;
                    ResfrescoCambio.requestLayout();
                    Refrescocambio.setBackgroundResource(R.drawable.plusnegros);
                }
            }

        });


        Tem = (ImageView) root.findViewById(R.id.cambiomenut3);
        Tes = (ConstraintLayout) root.findViewById(R.id.Tes);
        tescambio = (ConstraintLayout) root.findViewById(R.id.ventanaTes);
        ListaTe = (RecyclerView) root.findViewById(R.id.ListaTe);
        ListaTe.setLayoutManager(new LinearLayoutManager(getContext()));
        cambiote = (ImageView) root.findViewById(R.id.Tecambio);
        Tes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (tescambio.getLayoutParams().height == 1){
                    tescambio.getLayoutParams().height = 750;
                    tescambio.requestLayout();
                    cambiote.setBackgroundResource(R.drawable.menornegros);
                }
                else if (tescambio.getLayoutParams().height == 750){
                    tescambio.getLayoutParams().height = 1;
                    tescambio.requestLayout();
                    cambiote.setBackgroundResource(R.drawable.plusnegros);
                }
            }

        });
        cambiodesteinados = (ImageView) root.findViewById(R.id.Desteinadoscambio);
        desteinadoscambio =(ConstraintLayout) root.findViewById(R.id.ventanadesteinados);
        ListaDesteinados = (RecyclerView) root.findViewById(R.id.ListaDesteinados);
        ListaDesteinados.setLayoutManager(new LinearLayoutManager(getContext()));
        Desteinados = (ConstraintLayout) root.findViewById(R.id.Desteinados);
        Desteinados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (desteinadoscambio.getLayoutParams().height == 1){
                    desteinadoscambio.getLayoutParams().height = 750;
                    desteinadoscambio.requestLayout();
                    cambiodesteinados.setBackgroundResource(R.drawable.menornegros);
                }
                else if (desteinadoscambio.getLayoutParams().height == 750){
                    desteinadoscambio.getLayoutParams().height = 1;
                    desteinadoscambio.requestLayout();
                    cambiodesteinados.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });


        infusionescambio = (ConstraintLayout) root.findViewById(R.id.ventanaInfusiones);
        ListaInfusiones = (RecyclerView) root.findViewById(R.id.ListaInfusiones);
        ListaInfusiones.setLayoutManager(new LinearLayoutManager(getContext()));
        cambioinfusiones = (ImageView) root.findViewById(R.id.Infusionescambio);
        Infusiones = (ConstraintLayout) root.findViewById(R.id.Infusiones);
        Infusiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (infusionescambio.getLayoutParams().height == 1){
                    infusionescambio.getLayoutParams().height = 750;
                    infusionescambio.requestLayout();
                    cambioinfusiones.setBackgroundResource(R.drawable.menornegros);
                }
                else if (infusionescambio.getLayoutParams().height == 750){
                    infusionescambio.getLayoutParams().height = 1;
                    infusionescambio.requestLayout();
                    cambioinfusiones.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });




        Combinadosm = (ImageView) root.findViewById(R.id.cambiomenu4);
        Zumosm = (ImageView) root.findViewById(R.id.cambiomenuz3);
        CafesoloCambio = (ImageView) root.findViewById(R.id.cafesolocambio);
        CafeconLecheCambio = (ImageView) root.findViewById(R.id.cafeconlechecambio);
        CafeespecialCambio = (ImageView) root.findViewById(R.id.cafeEspecialcambio);
        LecheCambio = (ImageView) root.findViewById(R.id.Lechescambio);




        MENU1 = (ConstraintLayout) root.findViewById(R.id.MENU1);
        MENUVentana1 = (ConstraintLayout) root.findViewById(R.id.MENUVentada1);
        MENU1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("String:" , String.valueOf(MENUVentana1.getLayoutParams().height));
                if (MENUVentana1.getLayoutParams().height == 1){
                    MENUVentana1.getLayoutParams().height = 1050;
                    MENUVentana1.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1050 || VentanaBebidas.getLayoutParams().height == 1050 ){
                        menu.setVisibility(View.INVISIBLE);
                    }
                    Cambiomenu.setBackgroundResource(R.drawable.menornegros);
                }
                else if (MENUVentana1.getLayoutParams().height == 1050){
                    MENUVentana1.getLayoutParams().height = 1;
                    MENUVentana1.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1 && VentanaBebidas.getLayoutParams().height == 1 ){
                        menu.setVisibility(View.VISIBLE);
                    }
                    Cambiomenu.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });



        ListaMerienda = (RecyclerView) root.findViewById(R.id.Meriendas);
        ListaMerienda.setLayoutManager(new LinearLayoutManager(getContext()));
        Cafes = (ConstraintLayout) root.findViewById(R.id.Cafess);
        ventanacafe = (ConstraintLayout) root.findViewById(R.id.ventanacafes);
        ventanacafesolo = (ConstraintLayout) root.findViewById(R.id.ventanacafesolo);
        ListaCafeSolos = (RecyclerView) root.findViewById(R.id.ListaCafeSolos);
        ListaCafeSolos.setLayoutManager(new LinearLayoutManager(getContext()));
        Cafesolo = (ConstraintLayout) root.findViewById(R.id.Cafesolos);
        Cafesolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanacafesolo.getLayoutParams().height == 1){
                    ventanacafesolo.getLayoutParams().height = 510;
                    ventanacafesolo.requestLayout();
                    CafesoloCambio.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanacafesolo.getLayoutParams().height == 510){
                    ventanacafesolo.getLayoutParams().height = 1;
                    ventanacafesolo.requestLayout();
                    CafesoloCambio.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });

        ventanacafeconleche = (ConstraintLayout) root.findViewById(R.id.ventanacafeconleche);
        ListaCafeconLeches = (RecyclerView) root.findViewById(R.id.ListaCafeconLeches);
        ListaCafeconLeches.setLayoutManager(new LinearLayoutManager(getContext()));
        Cafeconleches = (ConstraintLayout) root.findViewById(R.id.Cafeconleches);
        Cafeconleches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanacafeconleche.getLayoutParams().height == 1){
                    ventanacafeconleche.getLayoutParams().height = 650;
                    ventanacafeconleche.requestLayout();
                    CafeconLecheCambio.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanacafeconleche.getLayoutParams().height == 650){
                    ventanacafeconleche.getLayoutParams().height = 1;
                    ventanacafeconleche.requestLayout();
                    CafeconLecheCambio.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });


        ventanacafeespecial = (ConstraintLayout) root.findViewById(R.id.ventanacafeEspecial);
        ListaCafeespecial = (RecyclerView) root.findViewById(R.id.ListaCafeEspecial);
        ListaCafeespecial.setLayoutManager(new LinearLayoutManager(getContext()));
        Cafeespecial = (ConstraintLayout) root.findViewById(R.id.CafeEspecial);
        Cafeespecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanacafeespecial.getLayoutParams().height == 1){
                    ventanacafeespecial.getLayoutParams().height = 650;
                    ventanacafeespecial.requestLayout();
                    CafeespecialCambio.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanacafeespecial.getLayoutParams().height == 650){
                    ventanacafeespecial.getLayoutParams().height = 1;
                    ventanacafeespecial.requestLayout();
                    CafeespecialCambio.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });


        ventanaLeches = (ConstraintLayout) root.findViewById(R.id.ventanaLeches);
        ListaLeches = (RecyclerView) root.findViewById(R.id.ListaLeches);
        ListaLeches.setLayoutManager(new LinearLayoutManager(getContext()));
        Leches = (ConstraintLayout) root.findViewById(R.id.Leches);
        Leches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanaLeches.getLayoutParams().height == 1){
                    ventanaLeches.getLayoutParams().height = 650;
                    ventanaLeches.requestLayout();
                    LecheCambio.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanaLeches.getLayoutParams().height == 650){
                    ventanaLeches.getLayoutParams().height = 1;
                    ventanaLeches.requestLayout();
                    LecheCambio.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });






        Cafes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanacafe.getLayoutParams().height == 1){
                    ventanacafe.getLayoutParams().height = 1350;
                    ventanacafe.requestLayout();
                    Cafem.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanacafe.getLayoutParams().height == 1350){
                    ventanacafe.getLayoutParams().height = 1;
                    ventanacafe.requestLayout();
                    Cafem.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });




        Te = (ConstraintLayout) root.findViewById(R.id.Te);
        ventamate = (ConstraintLayout) root.findViewById(R.id.ventanate);
        Te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventamate.getLayoutParams().height == 1){
                    ventamate.getLayoutParams().height = 1350;
                    ventamate.requestLayout();
                    Tem.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventamate.getLayoutParams().height == 1350){
                    ventamate.getLayoutParams().height = 1;
                    ventamate.requestLayout();
                    Tem.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });

        ventanaconbina = (ConstraintLayout) root.findViewById(R.id.ventanaCombinados);

        Combinados = (ConstraintLayout) root.findViewById(R.id.Combinads);
        ventanaconbi = (ConstraintLayout) root.findViewById(R.id.ventanacombi);
        ListaBebidas = (RecyclerView) root.findViewById(R.id.CombianadosListas);
        ListaBebidas.setLayoutManager(new LinearLayoutManager(getContext()));
        Combinados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanaconbi.getLayoutParams().height == 1 && ventanaconbina.getLayoutParams().height == 1){
                    ventanaconbi.getLayoutParams().height = 1050;
                    ventanaconbina.getLayoutParams().height = 550;
                    ventanaconbina.requestLayout();
                    ventanaconbi.requestLayout();
                    Combinadosm.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanaconbi.getLayoutParams().height == 1050 && ventanaconbina.getLayoutParams().height == 550){
                    ventanaconbi.getLayoutParams().height = 1;
                    ventanaconbina.getLayoutParams().height = 1;
                    ventanaconbina.requestLayout();
                    ventanaconbi.requestLayout();
                    Combinadosm.setBackgroundResource(R.drawable.plusnegros);
                }

            }

        });


        Zumos = (ConstraintLayout) root.findViewById(R.id.zumis);
        ventanazumos = (ConstraintLayout) root.findViewById(R.id.ventanazumos);
        ListaZumo = (RecyclerView) root.findViewById(R.id.zumoslista);
        ListaZumo.setLayoutManager(new LinearLayoutManager(getContext()));
        Zumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanazumos.getLayoutParams().height == 1){
                    ventanazumos.getLayoutParams().height = 510;
                    ventanazumos.requestLayout();
                    Zumosm.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanazumos.getLayoutParams().height == 510){
                    ventanazumos.getLayoutParams().height = 1;
                    ventanazumos.requestLayout();
                    Zumosm.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });


        Bebidas = (ConstraintLayout) root.findViewById(R.id.Bebidas);
        VentanaBebidas = (ConstraintLayout) root.findViewById(R.id.BebidasVentana);
        Bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("String:" , String.valueOf(MENUVentana1.getLayoutParams().height));
                if (VentanaBebidas.getLayoutParams().height == 1){
                    VentanaBebidas.getLayoutParams().height = 1050;
                    VentanaBebidas.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1050 || VentanaBebidas.getLayoutParams().height == 1050 ){
                        menu.setVisibility(View.INVISIBLE);
                    }
                    cambiobebidas.setBackgroundResource(R.drawable.menornegros);
                }
                else if (VentanaBebidas.getLayoutParams().height == 1050){
                    VentanaBebidas.getLayoutParams().height = 1;
                    VentanaBebidas.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1 && VentanaBebidas.getLayoutParams().height == 1 ){
                        menu.setVisibility(View.VISIBLE);
                    }
                    cambiobebidas.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });




        getMensajesFromFirebase();
        inicialize();
        Añadir();
        return root;
    }

    private void Añadir(){
        ID = mAuth.getUid();
        mUsuario.child("PedidosSinFinalizarMerienda").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double total = 0;
                String processed = "";
                sumatotal.setText(processed);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        double number = Double.parseDouble(ds.child("precio").getValue(String.class).replaceAll("[.,€]", ""));
                        total = total + number;

                        NumberFormat formatter = new DecimalFormat("0,00");

                        processed = formatter.format(total);

                        sumatotal.setText(processed);
                        Log.v("PRecio ", processed + " ///"+ total );


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void getMensajesFromFirebase() {



        mTosta2.child("Merienda").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMensaje4.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mMensaje4.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter3 = new AdapterMerienda(getContext(), mMensaje4, keys, R.layout.contenido_listas34);
                    ListaMerienda.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mZumos.child("Zumos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mZumo.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mZumo.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mZumo, keys, R.layout.contenido_listas34);
                    ListaZumo.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Cafes").child("CafeSolos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mCafesolo.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mCafesolo.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mCafesolo, keys, R.layout.contenido_listas34);
                    ListaCafeSolos.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Cafes").child("CafeconLeches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mCafeconLeche.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mCafeconLeche.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mCafeconLeche, keys, R.layout.contenido_listas34);
                    ListaCafeconLeches.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Cafes").child("Especiales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mCafeEspecial.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mCafeEspecial.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mCafeEspecial, keys, R.layout.contenido_listas34);
                    ListaCafeespecial.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mZumos.child("Cafes").child("Leches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mLeches.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mLeches.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mLeches, keys, R.layout.contenido_listas34);
                    ListaLeches.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Te").child("Infusiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mInfusiones.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String mediaentera = ds.child("preciosin").getValue().toString();
                        mInfusiones.add(new MisPedidosClass(precio,mediaentera,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter2 = new AdapterLechesMerienda(getContext(), mInfusiones, keys, R.layout.contenido_listas2);
                    ListaInfusiones.setAdapter(mAdapter2);
                    mAdapter2.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mZumos.child("Te").child("Desteinados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDesteinados.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String mediaentera = ds.child("preciosin").getValue().toString();
                        mDesteinados.add(new MisPedidosClass(precio,mediaentera,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter2 = new AdapterLechesMerienda(getContext(), mDesteinados, keys, R.layout.contenido_listas2);
                    ListaDesteinados.setAdapter(mAdapter2);
                    mAdapter2.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Te").child("Tes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mTe.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String mediaentera = ds.child("preciosin").getValue().toString();
                        mTe.add(new MisPedidosClass(precio,mediaentera,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter2 = new AdapterLechesMerienda(getContext(), mTe, keys, R.layout.contenido_listas2);
                    ListaTe.setAdapter(mAdapter2);
                    mAdapter2.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mZumos.child("Combinados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mBebidas.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mBebidas.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mBebidas, keys, R.layout.contenido_listas34);
                    ListaBebidas.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mZumos.child("Refrescos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mRefresco.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        mRefresco.add(new MisPedidosClass(precio,enteraprecio));
                        keys.add(ds.getKey());

                    }
                    mAdapter1 = new AdapterBebidasMerienda(getContext(), mRefresco, keys, R.layout.contenido_listas34);
                    ListaRefresco.setAdapter(mAdapter1);
                    mAdapter1.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

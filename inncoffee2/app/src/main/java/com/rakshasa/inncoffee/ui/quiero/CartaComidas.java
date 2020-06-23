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
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterBebidasComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterComidas.AdapterLechesComidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterDesayuno.AdapterBebidas;
import com.rakshasa.inncoffee.ui.quiero.AdapterDesayuno.AdapterLeches;
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

public class CartaComidas extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ConstraintLayout MENU1,MENUa,Menu2,ventanaMenu1,ventanaMenu2,CartaComida,CartaMerienda, MENUVentana1,Ventana2,Comidas,VentanaBebidas,Bebidas,Cafes,Te,Combinados,Zumos,menu,
            ventanacafe,ventamate,ventanaconbi,ventanazumos,Cafesolo,ventanacafesolo,ventanaconbina,
            ventanacafeconleche,Cafeconleches,Cafeespecial,ventanacafeespecial,ventanaLeches,Leches,Tes,Desteinados,Infusiones,tescambio,desteinadoscambio,infusionescambio,Refresco,ResfrescoCambio,Ensalada,Ensaladaventana,Picoteo,Picoteoventana,Sandwiches,SanwichesVentana,Burger,Burgerventana;
    private RecyclerView ListaZumo,ListaBebidas,ListaCafeSolos,ListaCafeconLeches,ListaCafeespecial,ListaLeches,ListaTe,ListaDesteinados,ListaInfusiones,ListaRefresco,EnsaladasLista,PicoteoLista,SandwichesLista,BurgeryciaLista;
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
    private AdapterBebidasComidas mAdapter1;
    private AdapterLechesComidas mAdapter2;
    private AdapterComidas mAdapter3;
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

       View root = inflater.inflate(R.layout.cartacomidas, container, false);
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
                CartaMerienda fragment = new CartaMerienda();
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
                MisPedidosSinFinalizarComidas dialog = new MisPedidosSinFinalizarComidas();
                dialog.show(getParentFragmentManager(), "Dialog" );
            }
        });


        ensalada = (ImageView) root.findViewById(R.id.Ensaladascambio);
        EnsaladasLista = (RecyclerView) root.findViewById(R.id.EnsaladaLista);
        EnsaladasLista.setLayoutManager(new LinearLayoutManager(getContext()));
        Ensalada =  (ConstraintLayout) root.findViewById(R.id.Ensaladas);
        Ensaladaventana = (ConstraintLayout) root.findViewById(R.id.EnsaladaVentana);
        Ensalada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (Ensaladaventana.getLayoutParams().height == 1){
                    Ensaladaventana.getLayoutParams().height = 550;
                    Ensaladaventana.requestLayout();
                    ensalada.setBackgroundResource(R.drawable.menornegros);
                }
                else if (Ensaladaventana.getLayoutParams().height == 550){
                    Ensaladaventana.getLayoutParams().height = 1;
                    Ensaladaventana.requestLayout();
                    ensalada.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });


        picoteo = (ImageView) root.findViewById(R.id.Picoteocambio);
        PicoteoLista = (RecyclerView) root.findViewById(R.id.PicoteoLista);
        PicoteoLista.setLayoutManager(new LinearLayoutManager(getContext()));
        Picoteo =  (ConstraintLayout) root.findViewById(R.id.Picoteo);
        Picoteoventana = (ConstraintLayout) root.findViewById(R.id.PicoteoVentana);
        Picoteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (Picoteoventana.getLayoutParams().height == 1){
                    Picoteoventana.getLayoutParams().height = 350;
                    Picoteoventana.requestLayout();
                    picoteo.setBackgroundResource(R.drawable.menornegros);
                }
                else if (Picoteoventana.getLayoutParams().height == 350){
                    Picoteoventana.getLayoutParams().height = 1;
                    Picoteoventana.requestLayout();
                    picoteo.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });
        burger = (ImageView) root.findViewById(R.id.BurgeryCiacambio);
        BurgeryciaLista = (RecyclerView) root.findViewById(R.id.BurgeryCiaLista);
        BurgeryciaLista.setLayoutManager(new LinearLayoutManager(getContext()));
        Burger =  (ConstraintLayout) root.findViewById(R.id.BurgeryCia);
        Burgerventana = (ConstraintLayout) root.findViewById(R.id.BurgeryCiaVentana);
        Burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (Burgerventana.getLayoutParams().height == 1){
                    Burgerventana.getLayoutParams().height = 550;
                    Burgerventana.requestLayout();
                    burger.setBackgroundResource(R.drawable.menornegros);
                }
                else if (Burgerventana.getLayoutParams().height == 550){
                    Burgerventana.getLayoutParams().height = 1;
                    Burgerventana.requestLayout();
                    burger.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });
        sandwiches = (ImageView) root.findViewById(R.id.Sandwichescambio);
        SandwichesLista = (RecyclerView) root.findViewById(R.id.SandwichesLista);
        SandwichesLista.setLayoutManager(new LinearLayoutManager(getContext()));
        Sandwiches =  (ConstraintLayout) root.findViewById(R.id.Sandwiches);
        SanwichesVentana = (ConstraintLayout) root.findViewById(R.id.SandwichesVentana);
        Sandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (SanwichesVentana.getLayoutParams().height == 1){
                    SanwichesVentana.getLayoutParams().height = 550;
                    SanwichesVentana.requestLayout();
                    sandwiches.setBackgroundResource(R.drawable.menornegros);
                }
                else if (SanwichesVentana.getLayoutParams().height == 550){
                    SanwichesVentana.getLayoutParams().height = 1;
                    SanwichesVentana.requestLayout();
                    sandwiches.setBackgroundResource(R.drawable.plusnegros);
                }
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
                    if (MENUVentana1.getLayoutParams().height == 1050 || Ventana2.getLayoutParams().height == 1050 || VentanaBebidas.getLayoutParams().height == 1050 ){
                        menu.setVisibility(View.INVISIBLE);
                    }
                    Cambiomenu.setBackgroundResource(R.drawable.menornegros);
                }
                else if (MENUVentana1.getLayoutParams().height == 1050){
                    MENUVentana1.getLayoutParams().height = 1;
                    MENUVentana1.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1 && Ventana2.getLayoutParams().height == 1 && VentanaBebidas.getLayoutParams().height == 1 ){
                        menu.setVisibility(View.VISIBLE);
                    }
                    Cambiomenu.setBackgroundResource(R.drawable.plusnegros);
                }
            }
        });
        PrimerPlato = (Spinner) root.findViewById(R.id.Primerplato);
        SegundoPlato = (Spinner) root.findViewById(R.id.Segundoplato);
        Postre = (Spinner) root.findViewById(R.id.Postre);
        MedioPlato = (Spinner) root.findViewById(R.id.Medioplato);
        Bebidasmenu = (Spinner) root.findViewById(R.id.BebidasMenu);
        Postre1 = (Spinner) root.findViewById(R.id.Postre1);
        Bebidasmenu1 = (Spinner) root.findViewById(R.id.BebidasMenu1);
        PrecioMenu1 = (TextView) root.findViewById(R.id.mediaprecio);
        añadir1 = (ImageView) root.findViewById(R.id.añadir1);
        añadir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(PrimerPlato.getSelectedItem().toString().equals("SELECCIONE PRIMER PLATO")){
                    Toast.makeText(getContext(), "Selecione Primer Plato", Toast.LENGTH_SHORT).show();

                }
                else if(SegundoPlato.getSelectedItem().toString().equals("SELECCIONE SEGUNDO PLATO")){
                    Toast.makeText(getContext(), "Selecione Segundo Plato", Toast.LENGTH_SHORT).show();
                }
                else if(Postre.getSelectedItem().toString().equals("SELECCIONE POSTRE")){
                    Toast.makeText(getContext(), "Selecione Postre", Toast.LENGTH_SHORT).show();
                }
                else if(Bebidasmenu.getSelectedItem().toString().equals("SELECCIONE BEBIDAS")||Bebidasmenu.getSelectedItem().toString().equals("____TIPO DE CAFES____")||
                        Bebidasmenu.getSelectedItem().toString().equals("____BEBIDAS____")||Bebidasmenu.getSelectedItem().toString().equals("____REFRESCOS____")||Bebidasmenu.getSelectedItem().toString().equals("____TES,DESTEINADOS,INFUSIONES____") ||Bebidasmenu.getSelectedItem().toString().equals("____ZUMOS____")){
                    Toast.makeText(getContext(), "Selecione Bebida", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth = FirebaseAuth.getInstance();
                    ID = mAuth.getUid();
                    final String key3 = mTosta2.push().getKey();
                    mTosta2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            String texto = PrimerPlato.getSelectedItem().toString() + "//"+ SegundoPlato.getSelectedItem().toString() + "//"+ Postre.getSelectedItem().toString() + "//"+ Bebidasmenu.getSelectedItem().toString() ;
                            String precio = PrecioMenu1.getText().toString();
                            MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                            mTosta2.child("MisPedidos").child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                            mTosta2.child("MisPedidos").child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });
                }
                // Toast.makeText(mContext, "AÑADIR: " + mensajes.getTexto() + " Media " , Toast.LENGTH_SHORT).show();
            }
        });
        Menu2 = (ConstraintLayout) root.findViewById(R.id.MENUC);
        ventanaMenu1 = (ConstraintLayout) root.findViewById(R.id.ventanaMENUC);
        MenuC= (ImageView) root.findViewById(R.id.MENUCcambio);
        Menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanaMenu1.getLayoutParams().height == 1){
                    ventanaMenu1.getLayoutParams().height = 1050;
                    ventanaMenu1.requestLayout();
                    MenuC.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanaMenu1.getLayoutParams().height == 1050){
                    ventanaMenu1.getLayoutParams().height = 1;
                    ventanaMenu1.requestLayout();
                    MenuC.setBackgroundResource(R.drawable.plusnegros);

                }
            }
        });

        PrecioMenu2 = (TextView) root.findViewById(R.id.mediaprecio1);
        añadir2 = (ImageView) root.findViewById(R.id.añadir2);
        añadir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(MedioPlato.getSelectedItem().toString().equals("SELECCIONE PRIMER PLATO")){
                    Toast.makeText(getContext(), "Selecione Primer Plato", Toast.LENGTH_SHORT).show();

                }
                else if(Postre1.getSelectedItem().toString().equals("SELECCIONE POSTRE")){
                    Toast.makeText(getContext(), "Selecione Postre", Toast.LENGTH_SHORT).show();
                }
                else if(Bebidasmenu1.getSelectedItem().toString().equals("SELECCIONE BEBIDAS")||Bebidasmenu1.getSelectedItem().toString().equals("____TIPO DE CAFES____")||
                        Bebidasmenu1.getSelectedItem().toString().equals("____BEBIDAS____")||Bebidasmenu1.getSelectedItem().toString().equals("____REFRESCOS____")||Bebidasmenu1.getSelectedItem().toString().equals("____TES,DESTEINADOS,INFUSIONES____") ||Bebidasmenu1.getSelectedItem().toString().equals("____ZUMOS____")){
                    Toast.makeText(getContext(), "Selecione Bebida", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth = FirebaseAuth.getInstance();
                    ID = mAuth.getUid();
                    final String key3 = mTosta2.push().getKey();
                    mTosta2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                            String texto = MedioPlato.getSelectedItem().toString()  + "//"+ Postre1.getSelectedItem().toString() + "//"+ Bebidasmenu1.getSelectedItem().toString() ;
                            String precio = PrecioMenu2.getText().toString();
                            MisPedidosClass user2 = new MisPedidosClass(texto, precio);
                            mTosta2.child("MisPedidos").child("PedidosSinFinalizarComidas").child(ID).child(key3).setValue(user2);
                            mTosta2.child("MisPedidos").child("PedidosFinalizadosComidas").child(ID).child(key3).setValue(user2);

                        }


                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "Failed to read value.", databaseError.toException());
                        }
                    });
                }
                // Toast.makeText(mContext, "AÑADIR: " + mensajes.getTexto() + " Media " , Toast.LENGTH_SHORT).show();
            }
        });

        MENUa = (ConstraintLayout) root.findViewById(R.id.MENUa);
        ventanaMenu2 = (ConstraintLayout) root.findViewById(R.id.ventanaMENUa);
        Menua = (ImageView) root.findViewById(R.id.MENUacambio);
        MENUa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (ventanaMenu2.getLayoutParams().height == 1){
                    ventanaMenu2.getLayoutParams().height = 750;
                    ventanaMenu2.requestLayout();
                    Menua.setBackgroundResource(R.drawable.menornegros);
                }
                else if (ventanaMenu2.getLayoutParams().height == 750){
                    ventanaMenu2.getLayoutParams().height = 1;
                    ventanaMenu2.requestLayout();
                    Menua.setBackgroundResource(R.drawable.plusnegros);

                }
            }
        });



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

        Comidas = (ConstraintLayout) root.findViewById(R.id.Comidas);
        Ventana2 = (ConstraintLayout) root.findViewById(R.id.ComidasVentada2);
        Comidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.v("String:" , String.valueOf(MENUVentana1.getLayoutParams().height));
                if (Ventana2.getLayoutParams().height == 1){
                    Ventana2.getLayoutParams().height = 1050;
                    Ventana2.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1050 || Ventana2.getLayoutParams().height == 1050 || VentanaBebidas.getLayoutParams().height == 1050 ){
                        menu.setVisibility(View.INVISIBLE);
                    }
                    Cambiomenu2.setBackgroundResource(R.drawable.menornegros);
                }
                else if (Ventana2.getLayoutParams().height == 1050){
                    Ventana2.getLayoutParams().height = 1;
                    Ventana2.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1 && Ventana2.getLayoutParams().height == 1 && VentanaBebidas.getLayoutParams().height == 1 ){
                        menu.setVisibility(View.VISIBLE);
                    }
                    Cambiomenu2.setBackgroundResource(R.drawable.plusnegros);
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
                    if (MENUVentana1.getLayoutParams().height == 1050 || Ventana2.getLayoutParams().height == 1050 || VentanaBebidas.getLayoutParams().height == 1050 ){
                        menu.setVisibility(View.INVISIBLE);
                    }
                    cambiobebidas.setBackgroundResource(R.drawable.menornegros);
                }
                else if (VentanaBebidas.getLayoutParams().height == 1050){
                    VentanaBebidas.getLayoutParams().height = 1;
                    VentanaBebidas.requestLayout();
                    if (MENUVentana1.getLayoutParams().height == 1 && Ventana2.getLayoutParams().height == 1 && VentanaBebidas.getLayoutParams().height == 1 ){
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
        mUsuario.child("PedidosSinFinalizarComidas").child(ID).addValueEventListener(new ValueEventListener() {
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

        mTosta2.child("Comidas").child("Ensaladas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMenus.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String desc = ds.child("descarticulo").getValue().toString();
                        mMenus.add(new MisPedidosClass(precio,enteraprecio,desc));
                        keys.add(ds.getKey());

                    }
                    mAdapter3 = new AdapterComidas(getContext(), mMenus, keys, R.layout.contenido_listas24);
                    EnsaladasLista.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("SandwichesyBocadillos").child("Sandwiches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMensaje1.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String desc = ds.child("descarticulo").getValue().toString();
                        mMensaje1.add(new MisPedidosClass(precio,enteraprecio,desc));
                        keys.add(ds.getKey());

                    }
                    mAdapter3 = new AdapterComidas(getContext(), mMensaje1, keys, R.layout.contenido_listas24);
                    SandwichesLista.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Hamburgesas").child("BurgerYCia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMensaje3.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String desc = ds.child("descarticulo").getValue().toString();
                        mMensaje3.add(new MisPedidosClass(precio,enteraprecio,desc));
                        keys.add(ds.getKey());

                    }
                    mAdapter3 = new AdapterComidas(getContext(), mMensaje3, keys, R.layout.contenido_listas24);
                    BurgeryciaLista.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Comidas").child("Plato").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mMensaje2.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        String enteraprecio = ds.child("precio").getValue().toString();
                        String desc = ds.child("descarticulo").getValue().toString();
                        mMensaje2.add(new MisPedidosClass(precio,enteraprecio,desc));
                        keys.add(ds.getKey());

                    }
                    mAdapter3 = new AdapterComidas(getContext(), mMensaje2, keys, R.layout.contenido_listas24);
                    PicoteoLista.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        mTosta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String precio = dataSnapshot.child("menucompleto").getValue().toString();
                    String enteraprecio = dataSnapshot.child("mediomenu").getValue().toString();
                    Log.v("STIRNG: ",precio + " // "+ enteraprecio);
                    PrecioMenu1.setText(precio);
                    PrecioMenu2.setText(enteraprecio);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Comidas").child("MenuMedio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> medioplato = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    medioplato.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        medioplato.add(precio);

                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, medioplato);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                MedioPlato.setAdapter(adapter);
                MedioPlato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Comidas").child("MenuPrimero").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Primero = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    Primero.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        Primero.add(precio);

                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, Primero);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                PrimerPlato.setAdapter(adapter);
                PrimerPlato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Comidas").child("MenuSegundo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Segundo = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    Segundo.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        Segundo.add(precio);

                        //  itemss.add(precio);
                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, Segundo);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SegundoPlato.setAdapter(adapter);
                SegundoPlato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Comidas").child("MenuPostre").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Postres = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    Postres.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        Postres.add(precio);

                        //  itemss.add(precio);
                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, Postres);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Postre1.setAdapter(adapter);
                Postre1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                Postre.setAdapter(adapter);
                Postre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mTosta2.child("Bebidas").child("BebidasMenu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Bebidass = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    Bebidass.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String precio = ds.child("nombrearticulo").getValue().toString();
                        Bebidass.add(precio);

                        //  itemss.add(precio);
                    }

                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, Bebidass);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Bebidasmenu1.setAdapter(adapter);
                Bebidasmenu1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                Bebidasmenu.setAdapter(adapter);
                Bebidasmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mZumo, keys, R.layout.contenido_listas34);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mCafesolo, keys, R.layout.contenido_listas34);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mCafeconLeche, keys, R.layout.contenido_listas34);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mCafeEspecial, keys, R.layout.contenido_listas34);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mLeches, keys, R.layout.contenido_listas34);
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
                    mAdapter2 = new AdapterLechesComidas(getContext(), mInfusiones, keys, R.layout.contenido_listas2);
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
                    mAdapter2 = new AdapterLechesComidas(getContext(), mDesteinados, keys, R.layout.contenido_listas2);
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
                    mAdapter2 = new AdapterLechesComidas(getContext(), mTe, keys, R.layout.contenido_listas2);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mBebidas, keys, R.layout.contenido_listas34);
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
                    mAdapter1 = new AdapterBebidasComidas(getContext(), mRefresco, keys, R.layout.contenido_listas34);
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

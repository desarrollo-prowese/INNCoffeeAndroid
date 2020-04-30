package com.example.inncoffee.ui.home;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inncoffee.Adapter.ParallaxRecyclerAdapter;
import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.MensajesFragment;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.example.inncoffee.ui.ofertas.OfertasFragment;
import com.example.inncoffee.ui.quiero.QuieroFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView mlist;

    private int[] imageIds = new int[]{
            R.drawable.menu_quiero2,
            R.drawable.pago_y_gano_menu_2,
            R.drawable.mis_ofertas_menu2,
            R.drawable.mis_puntos_menu2,
            R.drawable.menu_quiero2,
            R.drawable.pago_y_gano_menu_2,
            R.drawable.mis_ofertas_menu2,
            R.drawable.mis_puntos_menu2,
            R.drawable.menu_quiero2,
            R.drawable.pago_y_gano_menu_2,
            R.drawable.mis_ofertas_menu2,
            R.drawable.mis_puntos_menu2};

    private String[] label= {

            "PEDIDO ",
            "PAGO Y GANO",
            "MIS OFFERTAS",
            "MIS PUNTOS",
            "PEDIDO ",
            "PAGO Y GANO",
            "MIS OFFERTAS",
            "MIS PUNTOS",
            "PEDIDO ",
            "PAGO Y GANO",
            "MIS OFFERTAS",
            "MIS PUNTOS",

    };

    private boolean isNormalAdapter = false;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        createAdapter(mRecyclerView);
        MainActivity.mensajeToolbar.setText("");

        return root;
    }

    private void createAdapter(RecyclerView recyclerView) {
        final List<String> content = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            if (i == 0){
                content.add("PEDIDO");
            }
            if (i == 1){
                content.add(" PAGO Y GANO ");
            }
            if (i == 2){
                content.add(" MIS OFFERTAS ");
            }
            if (i == 3){
                content.add(" MIS PUNTOS ");
            }
            if (i == 4){
                content.add(" PEDIDO " );
            }
            if (i == 5){
                content.add(" PAGO Y GANO ");
            }
            if (i == 6){
                content.add(" MIS OFFERTAS ");
            }
            if (i == 7){
                content.add(" MIS PUNTOS ");
            }
            if (i == 8){
                content.add(" PEDIDO " );
            }
            if (i == 9){
                content.add(" PAGO Y GANO ");
            }
            if (i == 10){
                content.add(" MIS OFFERTAS ");
            }
            if (i == 11){
                content.add(" MIS PUNTOS ");
            }

        }

        final ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<String>(content) {
            @Override
            public void onBindViewHolderImpl(final RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                ((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i));
                ((ViewHolder) viewHolder).imgViewIcon.setImageResource(imageIds[i]);
                if (i == 0 ){
                    ((ViewHolder) viewHolder).textView.setTextColor(Color.BLACK);
                }
                if (i == 1 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 2 ){

                   ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 3 ){
                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 4 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.BLACK);
                }
                if (i == 5 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 6 ){
                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 7 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 8 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.BLACK);
                }
                if (i == 9 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 10 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
                if (i == 11 ){

                    ((ViewHolder) viewHolder).textView.setTextColor(Color.WHITE);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<String> adapter, int i) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.item, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<String> adapter) {
                return content.size();
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0){
                    Toast.makeText(getActivity(), " PEDIDO ", Toast.LENGTH_SHORT).show();
                    QuieroFragment fragment = new QuieroFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 1){
                    Toast.makeText(getActivity(), " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                }
                else if (position == 2){
                    Toast.makeText(getActivity(), " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                    OfertasFragment fragment = new OfertasFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 3){
                    Toast.makeText(getActivity(), " CoINNs ", Toast.LENGTH_SHORT).show();
                    MisPuntosFragment fragment = new MisPuntosFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 4){
                    Toast.makeText(getActivity(), " PEDIDO ", Toast.LENGTH_SHORT).show();
                    QuieroFragment fragment = new QuieroFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 5){
                    Toast.makeText(getActivity(), " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                }
                else if (position == 6){
                    Toast.makeText(getActivity(), " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                    OfertasFragment fragment = new OfertasFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 7){
                    Toast.makeText(getActivity(), " MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                    MisPuntosFragment fragment = new MisPuntosFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 8){
                    Toast.makeText(getActivity(), " PEDIDO ", Toast.LENGTH_SHORT).show();
                    QuieroFragment fragment = new QuieroFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 9){
                    Toast.makeText(getActivity(), " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                }
                else if (position == 10){
                    Toast.makeText(getActivity(), " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                    OfertasFragment fragment = new OfertasFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (position == 11){
                    Toast.makeText(getActivity(), "  MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                    MisPuntosFragment fragment = new MisPuntosFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else{
                    Toast.makeText(getActivity(), "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View header = getLayoutInflater().inflate(R.layout.header, recyclerView, false);
        adapter.setParallaxHeader(header, recyclerView);
        adapter.setData(content);
        recyclerView.setAdapter(adapter);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imgViewIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.label);
            imgViewIcon = (ImageView) itemView.findViewById(R.id.backgroundImage);

        }
    }

}


	 



    package com.example.inncoffee;

    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.inncoffee.Adapter.AdapterMenu;
    import com.example.inncoffee.View.NuevoPedido;
    import com.squareup.picasso.Picasso;
    import com.squareup.picasso.Target;

    import java.util.ArrayList;
    import java.util.List;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    public class menu_parallax_activity extends AppCompatActivity {

/*
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu_parallax);


            ParallaxRecyclerView recyclerView = (ParallaxRecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new TestRecyclerAdapter(this));

        }*/
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

                "QUIERO ",
                "PAGO Y GANO",
                "MIS OFFERTAS",
                "MIS PUNTOS",
                "QUIERO ",
                "PAGO Y GANO",
                "MIS OFFERTAS",
                "MIS PUNTOS",
                "QUIERO ",
                "PAGO Y GANO",
                "MIS OFFERTAS",
                "MIS PUNTOS",

        };




           private boolean isNormalAdapter = false;
            private RecyclerView mRecyclerView;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main2);
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                createAdapter(mRecyclerView);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (isNormalAdapter) {
                    createCardAdapter(mRecyclerView);
                } else {
                    createAdapter(mRecyclerView);
                }
                isNormalAdapter = !isNormalAdapter;
                return super.onOptionsItemSelected(item);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.main, menu);
                return super.onCreateOptionsMenu(menu);
            }

            private void createCardAdapter(RecyclerView recyclerView) {
                final List<String> content = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    if (i == 0){
                        content.add(" QUIERO " );
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
                        content.add(" QUIERO " );
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
                        content.add(" QUIERO " );
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
                    public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                        ((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i));
                        ((ViewHolder) viewHolder).imgViewIcon.setImageResource(imageIds[i]);

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
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 1){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 2){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 3){
                            Toast.makeText(menu_parallax_activity.this, " MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }
                        else if (position == 4){
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 5){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 6){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 7){
                            Toast.makeText(menu_parallax_activity.this, " MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }
                        else if (position == 8){
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 9){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 10){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 11){
                            Toast.makeText(menu_parallax_activity.this, "  MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }

                        else{
                        Toast.makeText(menu_parallax_activity.this, "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
                    }
                    }
                });

                HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(this);
                recyclerView.setLayoutManager(layoutManagerFixed);
                View header = getLayoutInflater().inflate(R.layout.header, recyclerView, false);
                layoutManagerFixed.setHeaderIncrementFixer(header);
                adapter.setShouldClipView(false);
                adapter.setParallaxHeader(header, recyclerView);
                adapter.setData(content);
                recyclerView.setAdapter(adapter);
            }

            private void createAdapter(RecyclerView recyclerView) {
                final List<String> content = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    if (i == 0){
                       content.add(" QUIERO " );
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
                        content.add(" QUIERO " );
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
                        content.add(" QUIERO " );
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
                    public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                        ((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i));
                        ((ViewHolder) viewHolder).imgViewIcon.setImageResource(imageIds[i]);
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
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 1){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 2){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 3){
                            Toast.makeText(menu_parallax_activity.this, " MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }
                        else if (position == 4){
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 5){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 6){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 7){
                            Toast.makeText(menu_parallax_activity.this, " MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }
                        else if (position == 8){
                            Toast.makeText(menu_parallax_activity.this, " Quiero ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, NuevoPedido.class));
                        }
                        else if (position == 9){
                            Toast.makeText(menu_parallax_activity.this, " PAGO Y GANO ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 10){
                            Toast.makeText(menu_parallax_activity.this, " MIS OFFERTAS ", Toast.LENGTH_SHORT).show();
                        }
                        else if (position == 11){
                            Toast.makeText(menu_parallax_activity.this, "  MIS PUNTOS ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(menu_parallax_activity.this, Pag_MisPuntos.class));
                        }
                        else{
                            Toast.makeText(menu_parallax_activity.this, "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                View header = getLayoutInflater().inflate(R.layout.header, recyclerView, false);
                adapter.setParallaxHeader(header, recyclerView);
                adapter.setData(content);
                recyclerView.setAdapter(adapter);
            }


            static class ViewHolder extends RecyclerView.ViewHolder {
                public TextView textView;

                public ViewHolder(View itemView) {
                    super(itemView);
                    textView = (TextView) itemView.findViewById(R.id.textView);
                }
            }
        }


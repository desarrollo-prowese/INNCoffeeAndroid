
	 



    package com.example.inncoffee;

    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

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


            private boolean isNormalAdapter = false;
            private RecyclerView mRecyclerView;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.menu_parallax);
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
        private int[] imageIds = new int[]{
                R.drawable.menu_quiero2,
                R.drawable.pago_y_gano_menu_2,
                R.drawable.mis_ofertas_menu2,
                R.drawable.mis_puntos_menu2,};

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
                        ((ViewHolder) viewHolder).getBackgroundImage().setImageResource(imageIds[i % imageIds.length]);

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
                        Toast.makeText(menu_parallax_activity.this, "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
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
                        ((ViewHolder) viewHolder).getBackgroundImage().setImageResource(imageIds[i % imageIds.length]);
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
                        Toast.makeText(menu_parallax_activity.this, "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
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


package com.example.inncoffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;


public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Object context;
    private LayoutInflater inflater;


    private int[] imageIds = new int[]{
            R.drawable.menu_quiero2,
            R.drawable.pago_y_gano_menu_2,
            R.drawable.mis_ofertas_menu2,
            R.drawable.mis_puntos_menu2,};


    public TestRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, TestRecyclerAdapter adapter, int i) {
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
           return new ViewHolder(inflater.inflate(R.layout.item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getBackgroundImage().setImageResource(imageIds[position % imageIds.length]);
        Picasso.get().load(imageIds[position % imageIds.length]).into(viewHolder.getBackgroundImage());

        if (position == 0){
            viewHolder.getTextView().setText(" QUIERO ");

        }

        if (position == 1){
            viewHolder.getTextView().setText(" PAGO Y GANO ");
        }
        if (position == 2){
            viewHolder.getTextView().setText(" MIS OFFERTAS ");
        }
        if (position == 3){
            viewHolder.getTextView().setText(" MIS PUNTOS ");
        }
        if (position == 4){
            viewHolder.getTextView().setText(" QUIERO ");
        }
        if (position == 5){
            viewHolder.getTextView().setText(" PAGO Y GANO ");
        }
        if (position == 6){
            viewHolder.getTextView().setText(" MIS OFFERTAS ");
        }
        if (position == 7){
            viewHolder.getTextView().setText(" MIS PUNTOS ");
        }
        if (position == 8){
            viewHolder.getTextView().setText(" QUIERO ");
        }
        if (position == 9){
            viewHolder.getTextView().setText(" PAGO Y GANO ");
        }
        if (position == 10){
            viewHolder.getTextView().setText(" MIS OFFERTAS ");
        }
        if (position == 11){
            viewHolder.getTextView().setText(" MIS PUNTOS ");
        }


        viewHolder.getBackgroundImage().reuse();
    }
    @Override
    public int getItemCount() {
        return 11;
    }

    public static class ViewHolder extends ParallaxViewHolder {

        private final TextView textView;



        public ViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.label);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }


        public TextView getTextView() {
            return textView;
        }
    }


}
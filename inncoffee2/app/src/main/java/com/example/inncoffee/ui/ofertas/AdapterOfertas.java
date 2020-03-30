package com.example.inncoffee.ui.ofertas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inncoffee.R;
import com.example.inncoffee.ui.mensajes.AdapterMensaje;
import com.example.inncoffee.ui.ofertas.OfertasClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOfertas extends RecyclerView.Adapter<AdapterOfertas.ViewHolder>{
    private Context mContext;
    private int resource;
    private ArrayList<String> keys;
    private ArrayList<OfertasClass> ofertaslist;
    private OnItemClickListener mListener;

    public AdapterOfertas(Context mContext, ArrayList<OfertasClass> ofertaslist, ArrayList<String> keys, int resource){
        this.mContext = mContext;
        this.ofertaslist = ofertaslist;
        this.resource = resource;
        this.keys = keys;

    }


    @NonNull
    @Override
    public AdapterOfertas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new AdapterOfertas.ViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OfertasClass ofertas = ofertaslist.get(position);
        holder.textViewofertas.setText(ofertas.getOfertas());
        holder.porcentaje.setText(ofertas.getPorcentaje());
        holder.layor.setImageResource(R.drawable.trazado_52_ek10);
        holder.Fondo.setImageResource(R.color._bg__menu_parallax_ek2_color);
    }

    @Override
    public int getItemCount() {
        return ofertaslist.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterOfertas.OnItemClickListener listener) {
        mListener = listener;
    }


    public void deleteItem(int position) {

        String key = keys.get(position);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Ofertas");
        ref.child(key).removeValue();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView textViewofertas;
        private TextView porcentaje;
        private ImageView layor;
        private ImageView Fondo;


        public View view;

        public ViewHolder(@NonNull View view ,final AdapterOfertas.OnItemClickListener listener) {
            super(view);
            this.view = view;
            this.layor = (ImageView) view.findViewById(R.id.imageView2);
            this.textViewofertas = (TextView) view.findViewById(R.id.textooferta);
            this.porcentaje = (TextView) view.findViewById(R.id.porcentaje);
            this.Fondo = (ImageView) view.findViewById(R.id.fondoofertas);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}

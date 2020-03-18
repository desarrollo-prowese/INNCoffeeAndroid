package com.example.inncoffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inncoffee.R;

import androidx.annotation.NonNull;

public class AdapterMenu extends ArrayAdapter<String> {

    private Context c;
    private String[] Menu;
    private int[] MenuImg;
    LayoutInflater inflater;

    public AdapterMenu(@NonNull Context context, String[] Menu, int[] MenuImg) {
        super(context, R.layout.item, Menu);
        this.c=context;
        this.Menu=Menu;
        this.MenuImg=MenuImg;

    }


    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item,null);
        }

        TextView nametv= (TextView) convertView.findViewById(R.id.label);
        ImageView img = (ImageView) convertView.findViewById(R.id.backgroundImage);

        nametv.setText(Menu[position]);
        img.setImageResource(MenuImg[position]);
        return convertView;

    }
}

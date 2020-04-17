package com.example.inncoffee.RegistroLogin;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.inncoffee.R;

public class Carga {

    private Activity activity;
    private AlertDialog dialog;

    public Carga (Activity myActivity){
     activity = myActivity;

    }

     void startCarga(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.cargando, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog (){
        dialog.dismiss();
    }
}

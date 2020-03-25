package com.example.inncoffee.ui.mensajes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;

public class MensajesFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mensajes, container, false);
        MainActivity.mensajeToolbar.setText("MIS OFFERTAS / MIS MENSAJES");

        return root;
    }
}

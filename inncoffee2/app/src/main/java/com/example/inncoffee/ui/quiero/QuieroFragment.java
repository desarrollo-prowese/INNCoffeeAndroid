package com.example.inncoffee.ui.quiero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuieroFragment extends Fragment {

    private Button Nuevopedido;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quierofragment, container, false);
        MainActivity.mensajeToolbar.setText("QUIERO");
        Nuevopedido = (Button) root.findViewById(R.id.buttonNuevoPedido);
        Nuevopedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuieroNuevoPedido fragment = new QuieroNuevoPedido();
                FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                ftEs.replace(R.id.nav_host_fragment, fragment);
                ftEs.addToBackStack(null);
                ftEs.commit();

            }
        });




        return root;
    }
}

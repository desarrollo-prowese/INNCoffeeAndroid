package com.example.inncoffee.ui.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.page.PageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class HomeFragment1 extends Fragment {
    private CardPagerAdapter<Card> pagerAdapter;
    private static CardViewPager viewPager;
    private ArrayList<Card> items;
    private FixedSpeedScroller scroller;
    public static int num = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home1, container, false);
        MainActivity.mensajeToolbar.setText("");
        if (num == 1){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            num = 0;
        }
        if (savedInstanceState == null) {


            items = new ArrayList<>();

            items.add(new Card(R.drawable.mis_puntos,"CoINNs ","CoINNs", Color.WHITE,R.drawable.menu_registronew1));
            items.add(new Card(R.drawable.mis_ofertas,"PROMOCIONES ","PROMOCIONES", Color.WHITE,R.drawable.menu_registronew1));
            items.add(new Card(R.drawable.pago_y_gano,"PAGO Y GANO ","PAGOYGANO", Color.WHITE,R.drawable.menu_registronew1));
            items.add(new Card(R.drawable.quiero,"PEDIDO ","PEDIDO", Color.WHITE, R.drawable.menu_registronew1));



            if (items.size() == 4) {
                PagerSettings.setSettings7cards(getContext());
            }

        } else {
            items = savedInstanceState.getParcelableArrayList("items");
        }

        viewPager = (CardViewPager) root.findViewById(R.id.carousel_pager);

        pagerAdapter = new CardPagerAdapter<Card>(
                getParentFragmentManager(),
                PageFragment.class,
                R.layout.page_layout,
                items,
                getActivity());

        viewPager.setAdapter(pagerAdapter);

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        viewPager.jumpToCorrectStartpos();

        //Changing the scroll speed to something natural after moving initially scrolling down
        if (scroller != null) {
            final Handler handler = new Handler(); // Have to delay this for the ui to render before changing back speed
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scroller.setScrollDurationFactor(1.2);
                }
            }, 100);
        }

        return root;
    }

    public static int getViewPagerId() {
        return viewPager.getId();
    }

    public static int getPageLimit() {
        return viewPager.getPageLimit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("items", items);
    }

}
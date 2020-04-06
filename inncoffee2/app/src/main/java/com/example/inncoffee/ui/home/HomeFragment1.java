package com.example.inncoffee.ui.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.page.PageFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home1, container, false);
        MainActivity.mensajeToolbar.setText("");
        if (savedInstanceState == null) {

            String colors[] = new String[7];
            String colors2[] = new String[7];

            colors[0] = "#2D4CB4";
            colors[1] = "#D147B6";
            colors[2] = "#F9441F";
            colors[3] = "#F9F5F4";
            colors[4] = "#3CDD4C";
            colors[5] = "#ffff0a";
            colors[6] = "#F19CBB";

            colors2[0] = "#40569E";
            colors2[1] = "#B861A6";
            colors2[2] = "#F94E2F";
            colors2[3] = "#f9C2C2";
            colors2[4] = "#5ABC63";
            colors2[5] = "#ffffaa";
            colors2[6] = "#F19CBB";

            items = new ArrayList<>();

                items.add(new Card(R.drawable.mis_puntos_menu2,"MIS PUNTOS ","MISPUNTOS"));
                items.add(new Card(R.drawable.mis_ofertas_menu2,"MIS OFERTAS ","MISOFERTAS"));
                items.add(new Card(R.drawable.pago_y_gano_menu_2,"PAGO Y GANO ","PAGOYGANO"));
                items.add(new Card(R.drawable.menu_quiero2,"QUIERO ","QUIERO"));


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
                getContext());

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
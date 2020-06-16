package com.example.inncoffee.ui.home.page;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.Card;
import com.example.inncoffee.ui.home.CardPagerAdapter;
import com.example.inncoffee.ui.home.CardViewPager;
import com.example.inncoffee.ui.home.HomeFragment1;
import com.example.inncoffee.ui.home.PagerSettings;
import com.example.inncoffee.ui.mispuntos.MisPuntosFragment;
import com.example.inncoffee.ui.ofertas.OfertasFragment;
import com.example.inncoffee.ui.pagoygano.Pagoygano;
import com.example.inncoffee.ui.quiero.QuieroFragment;
import com.jgabrielfreitas.core.BlurImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class PageFragment extends Fragment {

    public PageLayout pageLayout;
    private boolean startBottomPosition;
    public BlurImageView imagenfondo;

    public static View pageContent;

    public static Bundle createArgs(int pageLayoutId, Parcelable item) {
        Bundle args = new Bundle();
        args.putInt("page_layout_id", pageLayoutId);
        args.putParcelable("item", item);
        return args;
    }

    public void startBottomPosition(boolean startBottom) {
        this.startBottomPosition = startBottom;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        int pageLayoutId = getArguments().getInt("page_layout_id");
        pageLayout = (PageLayout) inflater.inflate(pageLayoutId, container, false);
        pageLayout.setRotation(90);

        Card item = getArguments().getParcelable("item");

        pageContent = pageLayout.findViewById(R.id.page_content);
        imagenfondo = pageLayout.findViewById(R.id.ImagenFondo);


        pageContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageContent.getTag().equals("PEDIDO")) {
                    QuieroFragment fragment = new QuieroFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (pageContent.getTag().equals("PAGOYGANO")){
                    Pagoygano fragment = new Pagoygano();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }

                else if (pageContent.getTag().equals("PROMOCIONES")){
                    Toast.makeText(getActivity(), " PROMOCIONES ", Toast.LENGTH_SHORT).show();
                    OfertasFragment fragment = new OfertasFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
                else if (pageContent.getTag().equals("CoINNs")){
                    MisPuntosFragment fragment = new MisPuntosFragment();
                    FragmentTransaction ftEs = getParentFragmentManager().beginTransaction();
                    ftEs.replace(R.id.nav_host_fragment, fragment);
                    ftEs.addToBackStack(null);
                    ftEs.commit();
                }
            }
        });


        //pageLayout.setBackgroundColor(Color.parseColor("#00" + item.getLayoutColor())); //00 transparent
        //pageContent.getBackground().setColorFilter(Color.parseColor("#" + item.getColor()), PorterDuff.Mode.SRC_ATOP);


       // imagenfondo.setBackgroundResource(item.getImage());
        imagenfondo.setImageDrawable(getActivity().getDrawable(item.getImage()));
        imagenfondo.setBlur(0);
        pageContent.setTag(item.getName());



      /*  LayerDrawable bgDrawable = (LayerDrawable) pageContent.getBackground();
        final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.card_color);
        shape.setColor(Color.parseColor(item.getColor()));*/

        TextView title = (TextView) pageContent.findViewById(R.id.card_name_text_view);
        title.setText(item.getTitle());
        title.setTextColor(item.getColor());
        title.setBackgroundResource(item.getFondo());

        if (pageContent != null) {
            pageContent.setOnTouchListener((CardViewPager) container);
        }

        if (startBottomPosition) {
            setStartposAndSkew(PagerSettings.getSkew(), PagerSettings.getTranslateY());
        }

        return pageLayout;
    }


    public void setStartposAndSkew(float skew, int translate) {
        pageLayout.setTranslated(translate);
        pageLayout.setSkew(skew);
    }

}
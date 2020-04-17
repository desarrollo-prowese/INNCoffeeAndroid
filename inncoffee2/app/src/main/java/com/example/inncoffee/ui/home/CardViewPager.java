package com.example.inncoffee.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.page.PageFragment;
import com.example.inncoffee.ui.quiero.QuieroFragment;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class CardViewPager extends ViewPager implements OnTouchListener {

    private Resources resources;

    private final String packageName;
    private int pageLimit = 4;

    public CardViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        resources = context.getResources();
        packageName = context.getPackageName();

    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter == null) {
            return;
        }

        setOnPageChangeListener((OnPageChangeListener) adapter);

        setCurrentItem(((CardPagerAdapter) adapter).getFirstPosition());
    }

    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;

    @Override
    public boolean onTouch(View view, MotionEvent event) {


        int X_LEFT_MARGIN = 0;
        int X = (int) event.getRawX() + X_LEFT_MARGIN;
        int Y = (int) event.getRawY();

        if (X < PagerSettings.getCardWidth() && Y < PagerSettings.getCardHeight()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastTouchDown = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    Log.v("Probando" , String.valueOf(PageFragment.pageContent.getTag()));
                    if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                        if (PageFragment.pageContent.getTag().equals("PEDIDO")){
                            Toast.makeText(getContext(), "PEDIDO", Toast.LENGTH_SHORT).show();}
                        else if (PageFragment.pageContent.getTag().equals("PAGOYGANO")){
                            Toast.makeText(getContext(), "PAGO Y GANO", Toast.LENGTH_SHORT).show();}
                        else if (PageFragment.pageContent.getTag().equals("PROMOCIONES")){
                            Toast.makeText(getContext(), "PROMOCIONES", Toast.LENGTH_SHORT).show();}
                        else if (PageFragment.pageContent.getTag().equals("COFFEEcoins")){
                            Toast.makeText(getContext(), "COFFEEcoins", Toast.LENGTH_SHORT).show();}

                        else{
                            Toast.makeText(getContext(), "Open settings for card!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        return false;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        setRotation(-90);

        float centerPager = (width - height) * 0.5f; // Moves viewpager to right position after rotating.
        setTranslationX(centerPager); //Investigate this
        setTranslationY(-centerPager);

        setOffscreenPageLimit(pageLimit);

        setPageMargin(-Dimensions.convertDpToPixelInt(845, getContext()));

        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
    }

    public int getPageLimit(){
        return pageLimit;
    }

    public void jumpToCorrectStartpos() {
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(((CardPagerAdapter) getAdapter()).getFirstPosition() - 80);
            }
        });
    }
}

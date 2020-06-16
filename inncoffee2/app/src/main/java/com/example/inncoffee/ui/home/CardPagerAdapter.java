package com.example.inncoffee.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.pdf.PdfDocument;
import android.os.Parcelable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.inncoffee.MainActivity;
import com.example.inncoffee.R;
import com.example.inncoffee.ui.home.page.PageFragment;
import com.example.inncoffee.ui.home.page.PageLayout;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class CardPagerAdapter<T extends Parcelable> extends FragmentPagerAdapter
        implements ViewPager.OnPageChangeListener {
    private static final String TAG = CardViewPager.class.getSimpleName();
    public static int pagesCount;
    private int firstPosition;

    private FragmentManager fragmentManager;
    private List<T> items;

    private Class pageFragmentClass;
    private int pageLayoutId;

    public static int createdFragments = 0;
    private Context context;


    public CardPagerAdapter(FragmentManager fragmentManager,
                            Class pageFragmentClass,
                            int pageLayoutId,
                            List<T> items,
                            Context activity) {
        super(fragmentManager);

        this.fragmentManager = fragmentManager;
        this.pageFragmentClass = pageFragmentClass;
        this.pageLayoutId = pageLayoutId;

        if (items == null) {
            this.items = new ArrayList<T>(0);
        } else {
            this.items = items;
        }

        pagesCount = this.items.size();

        firstPosition = pagesCount * 100 / 2 * 3; //Start in the middle when opening app

    }

    @Override
    public Fragment getItem(int position) {

        position = position % pagesCount;

        try {
            PageFragment pf = (PageFragment) pageFragmentClass.newInstance();
            pf.setArguments(PageFragment.createArgs(pageLayoutId, items.get(position)));
            Log.w("TAG", "SARASDRA: " + position);

            if (createdFragments > 80) {
                pf.startBottomPosition(true);
            }

            createdFragments++;
            return pf;

        } catch (IllegalAccessException e) {
            Log.w(TAG, e.getMessage());
        } catch (InstantiationException e) {
            Log.w(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public int getCount() {
        return pagesCount * 100; //pageCount * Loop
    }


    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {



        PageLayout current = getPageView(position);

        PageLayout next = getPageView(position + 1);
        PageLayout nextnext = getPageView(position + 2);
        PageLayout nextnextnext = getPageView(position + 3);

        PageLayout prev = getPageView(position - 1);
        PageLayout prevprev = getPageView(position - 2);
        PageLayout prevprevprev = getPageView(position - 3);
        PageLayout prevprevprevprev = getPageView(position - 4);

        /*if (positionOffset >= 0.5f) { //Example on how to play around with the cards current offset
            next.bringToFront();
        } else {
            current.bringToFront();
        }*/

        if (current != null) {
            current.setSkew(PagerSettings.getSkew() * positionOffset);
            current.setTranslated(PagerSettings.getTranslateY() * positionOffset);
        }

        /*
        else if (PageFragment.pageContent.getTag().equals("PROMOCIONES")){
            if(CardPagerAdapter.pagesCount == 387){
                PageFragment.imagenfondo.setBlur(0);
            }
        }
        else if (PageFragment.pageContent.getTag().equals("CoINNs")){
            if(CardPagerAdapter.pagesCount == 386){
                PageFragment.imagenfondo.setBlur(0);
            }
        }*/

        // Making sure all cards appearing on the screen are correctly skewed and translated - should factor this out

        if (next != null) {
            next.setTranslated(0);
            next.setSkew(0);
        }

        if (nextnext != null) {
            nextnext.setTranslated(0);
            nextnext.setSkew(0);
        }

        if (nextnextnext != null) {
            nextnextnext.setTranslated(0);
            nextnextnext.setSkew(0);

        }

        if (prev != null) {
            prev.setTranslated(PagerSettings.getTranslateY());
            prev.setSkew(0f);

        }

        if (prevprev != null) {
            prevprev.setTranslated(PagerSettings.getTranslateY());
            prevprev.setSkew(1f);
            prevprev.setHeight(PagerSettings.getCardHeight());
        }

        if (prevprevprev != null) {
            prevprevprev.setTranslated(PagerSettings.getTranslateY());
            prevprevprev.setSkew(0f);
            prevprevprev.setHeight(PagerSettings.getCardHeight());
        }

        if (prevprevprevprev != null) {
            prevprevprevprev.setTranslated(PagerSettings.getTranslateY());
            prevprevprevprev.setSkew(0f);
            prevprevprevprev.setHeight(PagerSettings.getCardHeight());

        }

    }


    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == CardViewPager.SCROLL_STATE_IDLE) {
            if (HomeFragment1.getPageLimit() == 0) {
                return;
            }
        }
    }

    @Override
    public float getPageWidth(int position) {
        return 1.07f;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    private PageLayout getPageView(int position) {
        Fragment f = fragmentManager.findFragmentByTag("android:switcher:" + HomeFragment1.getViewPagerId() + ":" + position);
        if (f != null && f.getView() != null) {
            return (PageLayout) f.getView();
        } else {
            return null;
        }
    }

}
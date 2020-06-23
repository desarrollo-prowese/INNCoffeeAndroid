package com.rakshasa.inncoffee.ui.home;

import android.content.Context;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class PagerSettings {

    private static float skew;
    private static int translateY;

    private static int cardWidth;
    private static int cardHeight;

    public static float getSkew() {
        return skew;
    }

    public static int getTranslateY() {
        return translateY;
    }

    public static int getCardWidth() {
        return cardWidth;
    }

    public static int getCardHeight() {
        return cardHeight;
    }

    public static void setSettings7cards(Context context) {
        translateY = Dimensions.convertDpToPixelInt(70f, context);
        cardWidth = Dimensions.convertDpToPixelInt(360f, context);
        cardHeight = Dimensions.convertDpToPixelInt(70f, context);
        skew = 0f;
    }

}

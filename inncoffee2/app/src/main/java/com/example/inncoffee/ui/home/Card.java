package com.example.inncoffee.ui.home;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.jgabrielfreitas.core.BlurImageView;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class Card implements Parcelable {
    private String title;
    private int color;

    public int getFondo () {
        return fondo;
    }

    public void setFondo (int fondo) {
        this.fondo = fondo;
    }

    private int fondo;


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;



    private String name;

    public Card(int image, String title,String name,int color, int fondo) {
        this.image = image;
        this.title = title;
        this.name = name;
        this.color = color;
        this.fondo = fondo;
    }

    private Card(Parcel in) {
        title = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public static final Creator<Card> CREATOR =
            new Creator<Card>() {
                @Override
                public Card createFromParcel(Parcel in) {
                    return new Card(in);
                }

                @Override
                public Card[] newArray(int size) {
                    return new Card[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


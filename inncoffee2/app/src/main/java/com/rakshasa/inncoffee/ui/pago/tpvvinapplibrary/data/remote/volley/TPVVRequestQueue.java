package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.rakshasa.inncoffee.MainActivity;

import java.io.File;

public class TPVVRequestQueue {
    private static Cache mCache;
    public static Context mContext;
    private static TPVVRequestQueue mInstance;
    private static Network mNetwork;
    private RequestQueue mRequestQueue = getRequestQueue();

    private TPVVRequestQueue(Context context) {
        mContext = context;
    }

    public static synchronized TPVVRequestQueue getInstance(Context context) {
        TPVVRequestQueue tPVVRequestQueue;
        synchronized (TPVVRequestQueue.class) {
            if (mInstance == null) {
                mInstance = new TPVVRequestQueue(context);
            }
            tPVVRequestQueue = mInstance;
        }
        return tPVVRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
           // Log.v("Cache : " ,String.valueOf(mContext) + mCache);
            mCache = new DiskBasedCache(mContext.getCacheDir(), 1048576);

            mNetwork = new BasicNetwork((BaseHttpStack) new SSLHurlStack());
            this.mRequestQueue = new RequestQueue(mCache, mNetwork);
            this.mRequestQueue.start();
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
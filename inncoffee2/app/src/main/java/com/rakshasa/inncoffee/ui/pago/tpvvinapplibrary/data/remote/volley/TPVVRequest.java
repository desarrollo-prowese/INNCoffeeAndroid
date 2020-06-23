package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class TPVVRequest<T> extends Request<T> {
    private final Class<T> clazz;
    private final Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Map<String, String> params;

    public TPVVRequest(String str, Class<T> cls, Map<String, String> map, Map<String, String> map2, Response.Listener<T> listener2, Response.ErrorListener errorListener) {
        super(1, str, errorListener);
        this.clazz = cls;
        this.headers = map;
        this.params = map2;
        this.listener = listener2;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers != null ? this.headers : super.getHeaders();
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParams() throws AuthFailureError {
        return this.params != null ? this.params : super.getParams();
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(T t) {
        this.listener.onResponse(t);
    }

    /* access modifiers changed from: protected */
    public Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(this.gson.fromJson(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers)), this.clazz), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError((Throwable) e));
        } catch (JsonSyntaxException e2) {
            return Response.error(new ParseError((Throwable) e2));
        }
    }
}

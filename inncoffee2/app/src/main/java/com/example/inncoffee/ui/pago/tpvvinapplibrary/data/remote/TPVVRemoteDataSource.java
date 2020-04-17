package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.data.TPVVDataSource;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.data.model.SignedResponseDto;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley.TPVVRequest;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley.TPVVRequestQueue;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;


public class TPVVRemoteDataSource implements TPVVDataSource<String, JSONObject, SignedResponseDto, ErrorResponse> {
    private static TPVVRemoteDataSource mInstance;
    private final Context mContext;

    private TPVVRemoteDataSource(Context context) {
        this.mContext = context;
    }

    public static synchronized TPVVRemoteDataSource getInstance(Context context) {
        TPVVRemoteDataSource tPVVRemoteDataSource;
        synchronized (TPVVRemoteDataSource.class) {
            if (mInstance == null) {
                mInstance = new TPVVRemoteDataSource(context);
            }
            tPVVRemoteDataSource = mInstance;
        }
        return tPVVRemoteDataSource;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public void sendPetition(String str, JSONObject jSONObject, final TPVVDataSource.SendPetitionRepositoryCallback<SignedResponseDto, ErrorResponse> sendPetitionRepositoryCallback) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("datoEntrada", jSONObject.toString());
        Log.d("SDKinApp_log: petition", jSONObject.toString());
        TPVVRequest tPVVRequest = new TPVVRequest(str, SignedResponseDto.class, (Map<String, String>) null, hashMap, new Response.Listener<SignedResponseDto>() {
            public void onResponse(SignedResponseDto signedResponseDto) {
                sendPetitionRepositoryCallback.onSendPetitionOK(signedResponseDto);
                Log.d("SDKinApp_log: response", signedResponseDto.getMensaje().toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                String str = "";
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    str = "TIME_OUT";
                } else if (volleyError instanceof AuthFailureError) {
                    str = "AUTH_FAILURE_ERROR";
                } else if (volleyError instanceof ServerError) {
                    str = "SERVER_ERROR";
                } else if (volleyError instanceof NetworkError) {
                    str = "NETWORK_ERROR";
                } else if (volleyError instanceof ParseError) {
                    str = "PARSE_ERROR";
                }
                sendPetitionRepositoryCallback.onSendPetitionKO(new ErrorResponse(TPVVConstants.CODE_CONNECTION_ERROR, str));
            }
        });
        tPVVRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 1, 1.0f));
        TPVVRequestQueue.getInstance(mContext).addToRequestQueue(tPVVRequest);
    }
}

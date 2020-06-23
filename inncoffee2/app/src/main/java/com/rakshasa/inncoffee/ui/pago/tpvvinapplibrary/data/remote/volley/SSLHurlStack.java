package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.volley;

import com.android.volley.toolbox.HurlStack;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.ssl.TPVVSSLFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class SSLHurlStack extends HurlStack {
    /* access modifiers changed from: protected */
    public HttpURLConnection createConnection(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
        try {
            httpsURLConnection.setSSLSocketFactory(new TPVVSSLFactory());
            httpsURLConnection.setHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpsURLConnection;
    }
}

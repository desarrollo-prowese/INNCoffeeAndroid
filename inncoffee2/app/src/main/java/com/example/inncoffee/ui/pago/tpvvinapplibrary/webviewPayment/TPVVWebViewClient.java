package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;


import androidx.annotation.RequiresApi;

public class TPVVWebViewClient extends WebViewClient {
    Context mContext;
    IWebViewCallback mWebViewCallback;

    interface IWebViewCallback {
        void onErrorPageLoading(String str);

        void onPageLoadFinished(String str, WebView webView);

        void onPageLoading(String str);

        void showAlertResult(String str);
    }

    public TPVVWebViewClient(IWebViewCallback iWebViewCallback, Context context) {
        this.mWebViewCallback = iWebViewCallback;
        this.mContext = context;
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW) {
            this.mWebViewCallback.onPageLoading(str);
            return true;
        }
        TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
        this.mWebViewCallback.showAlertResult(str);
        return false;
    }

    public void onPageFinished(WebView webView, String str) {
        this.mWebViewCallback.onPageLoadFinished(str, webView);
        super.onPageFinished(webView, str);
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("SSL Certificate Error");
        String str = "Certificate error.";
        switch (sslError.getPrimaryError()) {
            case 0:
                str = "The certificate is not yet valid.";
                break;
            case 1:
                str = "The certificate has expired.";
                break;
            case 2:
                str = "The certificate Hostname mismatch.";
                break;
            case 3:
                str = "The certificate authority is not trusted.";
                break;
        }
        builder.setMessage(str);
        final String finalStr = str;
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                TPVVWebViewClient.this.mWebViewCallback.onErrorPageLoading(finalStr);
            }
        });
        builder.create().show();
    }

    @RequiresApi(api = 21)
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String uri = webResourceRequest.getUrl().toString();
        if (TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW) {
            this.mWebViewCallback.onPageLoading(uri);
            return true;
        }
        TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
        this.mWebViewCallback.showAlertResult(uri);
        return false;
    }
}

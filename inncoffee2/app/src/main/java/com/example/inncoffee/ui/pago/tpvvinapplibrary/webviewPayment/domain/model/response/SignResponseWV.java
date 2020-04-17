package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response;

import com.google.gson.annotations.SerializedName;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SignResponseWV {
    @SerializedName("Ds_MerchantParameters")
    private String merchantParameters;
    @SerializedName("Ds_Signature")
    private String signature;
    @SerializedName("Ds_SignatureVersion")
    private String signatureVersion;

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getMerchantParameters() {
        return this.merchantParameters;
    }

    public void setMerchantParameters(String str) {
        this.merchantParameters = str;
    }

    public String getSignatureVersion() {
        return this.signatureVersion;
    }

    public void setSignatureVersion(String str) {
        this.signatureVersion = str;
    }

    public byte[] getPostData() {
        String str = "";
        try {
            str = "Ds_Signature=" + URLEncoder.encode(this.signature, HttpRequest.CHARSET_UTF8) + "&Ds_MerchantParameters=" + URLEncoder.encode(this.merchantParameters, HttpRequest.CHARSET_UTF8) + "&Ds_SignatureVersion=" + URLEncoder.encode(this.signatureVersion, HttpRequest.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.getBytes();
    }
}

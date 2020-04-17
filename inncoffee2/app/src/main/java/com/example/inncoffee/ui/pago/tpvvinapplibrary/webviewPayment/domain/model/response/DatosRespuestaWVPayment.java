package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class DatosRespuestaWVPayment {
    private Map<String, String> mapResponse;
    private String response;
    @SerializedName("Ds_Amount")
    private String responseAmount;
    @SerializedName("Ds_AuthorisationCode")
    private String responseAuthorisationCode;
    @SerializedName("Ds_Card_Brand")
    private String responseCardBrand;
    @SerializedName("Ds_Card_Country")
    private String responseCardCountry;
    @SerializedName("Ds_Card_Number")
    private String responseCardNumber;
    @SerializedName("Ds_Card_Type")
    private String responseCardType;
    @SerializedName("Ds_Currency")
    private String responseCurrency;
    @SerializedName("Ds_Date")
    private String responseDate;
    @SerializedName("Ds_ExpiryDate")
    private String responseExpiryDate;
    @SerializedName("Ds_Hour")
    private String responseHour;
    @SerializedName("Ds_Merchant_Identifier")
    private String responseIdentifier;
    @SerializedName("Ds_ConsumerLanguage")
    private String responseLanguage;
    @SerializedName("Ds_MerchantCode")
    private String responseMerchantCode;
    @SerializedName("Ds_MerchantData")
    private String responseMerchantData;
    @SerializedName("Ds_Nsu")
    private String responseNSU;
    @SerializedName("Ds_Order")
    private String responseOrder;
    @SerializedName("Ds_Response")
    private String responseResponse;
    @SerializedName("Ds_SecurePayment")
    private String responseSecurePayment;
    @SerializedName("Ds_Signature")
    private String responseSignature;
    @SerializedName("Ds_Terminal")
    private String responseTerminal;
    @SerializedName("Ds_TransactionType")
    private String responseTransactionType;

    public String getResponseAmount() {
        return this.responseAmount;
    }

    public void setResponseAmount(String str) {
        this.responseAmount = str;
    }

    public String getResponseCurrency() {
        return this.responseCurrency;
    }

    public void setResponseCurrency(String str) {
        this.responseCurrency = str;
    }

    public String getResponseOrder() {
        return this.responseOrder;
    }

    public void setResponseOrder(String str) {
        this.responseOrder = str;
    }

    public String getResponseMerchantCode() {
        return this.responseMerchantCode;
    }

    public void setResponseMerchantCode(String str) {
        this.responseMerchantCode = str;
    }

    public String getResponseTerminal() {
        return this.responseTerminal;
    }

    public void setResponseTerminal(String str) {
        this.responseTerminal = str;
    }

    public String getResponseResponse() {
        return this.responseResponse;
    }

    public void setResponseResponse(String str) {
        this.responseResponse = str;
    }

    public String getResponseAuthorisationCode() {
        return this.responseAuthorisationCode;
    }

    public void setResponseAuthorisationCode(String str) {
        this.responseAuthorisationCode = str;
    }

    public String getResponseTransactionType() {
        return this.responseTransactionType;
    }

    public void setResponseTransactionType(String str) {
        this.responseTransactionType = str;
    }

    public String getResponseSecurePayment() {
        return this.responseSecurePayment;
    }

    public void setResponseSecurePayment(String str) {
        this.responseSecurePayment = str;
    }

    public String getResponseLanguage() {
        return this.responseLanguage;
    }

    public void setResponseLanguage(String str) {
        this.responseLanguage = str;
    }

    public String getResponseCardNumber() {
        return this.responseCardNumber;
    }

    public void setResponseCardNumber(String str) {
        this.responseCardNumber = str;
    }

    public String getResponseCardType() {
        return this.responseCardType;
    }

    public void setResponseCardType(String str) {
        this.responseCardType = str;
    }

    public String getResponseMerchantData() {
        return this.responseMerchantData;
    }

    public void setResponseMerchantData(String str) {
        this.responseMerchantData = str;
    }

    public String getResponseCardCountry() {
        return this.responseCardCountry;
    }

    public void setResponseCardCountry(String str) {
        this.responseCardCountry = str;
    }

    public String getResponseDate() {
        return this.responseDate;
    }

    public void setResponseDate(String str) {
        this.responseDate = str;
    }

    public String getResponseHour() {
        return this.responseHour;
    }

    public void setResponseHour(String str) {
        this.responseHour = str;
    }

    public String getResponseIdentifier() {
        return this.responseIdentifier;
    }

    public void setResponseIdentifier(String str) {
        this.responseIdentifier = str;
    }

    public String getResponseNSU() {
        return this.responseNSU;
    }

    public void setResponseNSU(String str) {
        this.responseNSU = str;
    }

    public Map<String, String> getMapResponse() {
        return this.mapResponse;
    }

    public void setMapResponse(Map<String, String> map) {
        this.mapResponse = map;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String str) {
        this.response = str;
    }

    public String getResponseSignature() {
        return this.responseSignature;
    }

    public void setResponseSignature(String str) {
        this.responseSignature = str;
    }

    public String getResponseCardBrand() {
        return this.responseCardBrand;
    }

    public void setResponseCardBrand(String str) {
        this.responseCardBrand = str;
    }

    public String getResponseExpiryDate() {
        return this.responseExpiryDate;
    }

    public void setResponseExpiryDate(String str) {
        this.responseExpiryDate = str;
    }

    public String toString() {
        return "DatosRespuestaWVPayment{responseAmount='" + this.responseAmount + '\'' + ", responseCurrency='" + this.responseCurrency + '\'' + ", responseOrder='" + this.responseOrder + '\'' + ", responseMerchantCode='" + this.responseMerchantCode + '\'' + ", responseTerminal='" + this.responseTerminal + '\'' + ", responseResponse='" + this.responseResponse + '\'' + ", responseAuthorisationCode='" + this.responseAuthorisationCode + '\'' + ", responseTransactionType='" + this.responseTransactionType + '\'' + ", responseSecurePayment='" + this.responseSecurePayment + '\'' + ", responseLanguage='" + this.responseLanguage + '\'' + ", responseCardNumber='" + this.responseCardNumber + '\'' + ", responseCardType='" + this.responseCardType + '\'' + ", responseMerchantData='" + this.responseMerchantData + '\'' + ", responseCardCountry='" + this.responseCardCountry + '\'' + ", responseDate='" + this.responseDate + '\'' + ", responseHour='" + this.responseHour + '\'' + ", responseIdentifier='" + this.responseIdentifier + '\'' + ", responseNSU='" + this.responseNSU + '\'' + ", responseSignature='" + this.responseSignature + '\'' + ", responseCardBrand='" + this.responseCardBrand + '\'' + ", responseExpiryDate='" + this.responseExpiryDate + '\'' + ", mapResponse=" + this.mapResponse + ", response='" + this.response + '\'' + '}';
    }
}

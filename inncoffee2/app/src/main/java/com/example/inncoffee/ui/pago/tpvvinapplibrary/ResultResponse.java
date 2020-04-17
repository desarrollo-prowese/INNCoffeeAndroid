package com.example.inncoffee.ui.pago.tpvvinapplibrary;

public class ResultResponse {
    private String amount;
    private String authorisationCode;
    private String cardBrand;
    private String cardCountry;
    private String cardNumber;
    private String cardType;
    private String currency;
    private String date;
    private String expiryDate;
    private String hour;
    private String identifier;
    private String language;
    private String merchantCode;
    private String merchantData;
    private String order;
    private String responseCode;
    private String securePayment;
    private String signature;
    private String terminal;
    private String transactionType;

    public ResultResponse() {
    }

    public ResultResponse(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18) {
        this.amount = str;
        this.currency = str2;
        this.order = str3;
        this.terminal = str4;
        this.responseCode = str5;
        this.authorisationCode = str6;
        this.transactionType = str7;
        this.securePayment = str8;
        this.language = str9;
        this.cardNumber = str10;
        this.cardType = str11;
        this.merchantData = str12;
        this.cardCountry = str13;
        this.date = str14;
        this.hour = str15;
        this.identifier = str16;
        this.signature = str17;
        this.expiryDate = str18;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String str) {
        this.order = str;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String str) {
        this.terminal = str;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String str) {
        this.responseCode = str;
    }

    public String getAuthorisationCode() {
        return this.authorisationCode;
    }

    public void setAuthorisationCode(String str) {
        this.authorisationCode = str;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String str) {
        this.transactionType = str;
    }

    public String getSecurePayment() {
        return this.securePayment;
    }

    public void setSecurePayment(String str) {
        this.securePayment = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String str) {
        this.cardNumber = str;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String str) {
        this.cardType = str;
    }

    public String getMerchantData() {
        return this.merchantData;
    }

    public void setMerchantData(String str) {
        this.merchantData = str;
    }

    public String getCardCountry() {
        return this.cardCountry;
    }

    public void setCardCountry(String str) {
        this.cardCountry = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getHour() {
        return this.hour;
    }

    public void setHour(String str) {
        this.hour = str;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getMerchantCode() {
        return this.merchantCode;
    }

    public void setMerchantCode(String str) {
        this.merchantCode = str;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String str) {
        this.expiryDate = str;
    }

    public String getCardBrand() {
        return this.cardBrand;
    }

    public void setCardBrand(String str) {
        this.cardBrand = str;
    }

    public String toString() {
        return "ResultResponse{amount='" + this.amount + '\'' + ", currency='" + this.currency + '\'' + ", order='" + this.order + '\'' + ", merchantCode='" + this.merchantCode + '\'' + ", terminal='" + this.terminal + '\'' + ", responseCode='" + this.responseCode + '\'' + ", authorisationCode='" + this.authorisationCode + '\'' + ", transactionType='" + this.transactionType + '\'' + ", securePayment='" + this.securePayment + '\'' + ", language='" + this.language + '\'' + ", cardNumber='" + this.cardNumber + '\'' + ", cardType='" + this.cardType + '\'' + ", merchantData='" + this.merchantData + '\'' + ", cardCountry='" + this.cardCountry + '\'' + ", date='" + this.date + '\'' + ", hour='" + this.hour + '\'' + ", identifier='" + this.identifier + '\'' + ", signature='" + this.signature + '\'' + ", expiryDate='" + this.expiryDate + '\'' + ", cardBrand='" + this.cardBrand + '\'' + '}';
    }
}

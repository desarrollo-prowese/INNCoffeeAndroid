package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary;

import android.graphics.Bitmap;

public class UIDirectPaymentConfig {
    private static String backgroundColor = null;
    private static String btnBackgroundColor = null;
    private static String btnText = null;
    private static String btnTextColor = null;
    private static String cardHeadText = null;
    private static String cardHeadTextBackgroundColor = null;
    private static String cardHeadTextColor = null;
    private static String cardText = null;
    private static String cardTextError = "Número de tarjeta inválido.";
    private static String cvvText = null;
    private static String cvvTextError = "CVV inválido.";
    private static String expiryText = null;
    private static String expiryTextError = "Fecha inválida";
    private static String infoText;
    private static String infoTextColor;
    private static Bitmap logo;
    private static String progressBarColor;
    private static String topBarColor;

    public static Bitmap getLogo() {
        return logo;
    }

    public static void setLogo(Bitmap bitmap) {
        logo = bitmap;
    }

    public static String getProgressBarColor() {
        return progressBarColor;
    }

    public static void setProgressBarColor(String str) {
        progressBarColor = str;
    }

    public static String getTopBarColor() {
        return topBarColor;
    }

    public static void setTopBarColor(String str) {
        topBarColor = str;
    }

    public static String getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(String str) {
        backgroundColor = str;
    }

    public static String getCardHeadText() {
        return cardHeadText;
    }

    public static void setCardHeadText(String str) {
        cardHeadText = str;
    }

    public static String getCardHeadTextColor() {
        return cardHeadTextColor;
    }

    public static void setCardHeadTextColor(String str) {
        cardHeadTextColor = str;
    }

    public static String getCardText() {
        return cardText;
    }

    public static void setCardText(String str) {
        cardText = str;
    }

    public static String getCardTextError() {
        return cardTextError;
    }

    public static void setGetCardTextError(String str) {
        cardTextError = str;
    }

    public static String getExpiryText() {
        return expiryText;
    }

    public static void setExpiryText(String str) {
        expiryText = str;
    }

    public static String getExpiryTextError() {
        return expiryTextError;
    }

    public static void setExpiryTextError(String str) {
        expiryTextError = str;
    }

    public static String getCvvText() {
        return cvvText;
    }

    public static void setCvvText(String str) {
        cvvText = str;
    }

    public static String getCvvTextError() {
        return cvvTextError;
    }

    public static void setCvvTextError(String str) {
        cvvTextError = str;
    }

    public static String getBtnText() {
        return btnText;
    }

    public static void setBtnText(String str) {
        btnText = str;
    }

    public static String getBtnBackgroundColor() {
        return btnBackgroundColor;
    }

    public static void setBtnBackgroundColor(String str) {
        btnBackgroundColor = str;
    }

    public static String getBtnTextColor() {
        return btnTextColor;
    }

    public static void setBtnTextColor(String str) {
        btnTextColor = str;
    }

    public static String getInfoText() {
        return infoText;
    }

    public static void setInfoText(String str) {
        infoText = str;
    }

    public static String getCardHeadTextBackgroundColor() {
        return cardHeadTextBackgroundColor;
    }

    public static void setCardHeadTextBackgroundColor(String str) {
        cardHeadTextBackgroundColor = str;
    }

    public static void setCardTextError(String str) {
        cardTextError = str;
    }

    public static String getInfoTextColor() {
        return infoTextColor;
    }

    public static void setInfoTextColor(String str) {
        infoTextColor = str;
    }
}

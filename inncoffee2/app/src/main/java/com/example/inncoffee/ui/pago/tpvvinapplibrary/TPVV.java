package com.example.inncoffee.ui.pago.tpvvinapplibrary;

import android.content.Context;
import android.content.Intent;

import com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.WebViewPaymentActivity;

public class TPVV {
    public static IPaymentResult mCallback;


    public static void doWebViewPayment(Context context, String str, Double d, String str2, String str3, String str4, IPaymentResult iPaymentResult) {
        mCallback = iPaymentResult;
        if (validateParams(str, d, str2, str3, str4, context).booleanValue()) {
            Intent intent = new Intent(context, WebViewPaymentActivity.class);
            intent.setFlags(268435456);
            if (str3 != null && !TPVVConstants.REQUEST_REFERENCE.equals(str3)) {
                intent.putExtra(TPVVConstants.WITH_REFERENCE, "TRUE");
            }
            context.startActivity(intent);
        }
    }

    private static Boolean validateParams(String str, Double d, String str2, String str3, String str4, Context context) {
        if (TPVVConfiguration.getPackageName() == null) {
            TPVVConfiguration.setPackageName(context.getPackageName());
        }
        if (str != null) {
            TPVVConfiguration.setOrder(str);
            if (d == null || d.doubleValue() < 0.0d) {
                TPVVConfiguration.setAmount((String) null);
                mCallback.paymentResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "Importe inválido."));
                return false;
            }
            TPVVConfiguration.setAmount(String.format("%.000f", new Object[]{d}));
            if (str2 != null) {
                TPVVConfiguration.setOperationType(str2);
                if (str3 != null) {
                    TPVVConfiguration.setIdentifier(str3);
                } else {
                    TPVVConfiguration.setIdentifier((String) null);
                }
                if (str4 != null) {
                    TPVVConfiguration.setDescription(str4);
                } else {
                    TPVVConfiguration.setDescription((String) null);
                }
                return true;
            }
            TPVVConfiguration.setOperationType((String) null);
            mCallback.paymentResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "Tipo de operación inválido."));
            return false;
        }
        TPVVConfiguration.setOrder((String) null);
        mCallback.paymentResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "Código de pedido inválido."));
        return false;
    }
}

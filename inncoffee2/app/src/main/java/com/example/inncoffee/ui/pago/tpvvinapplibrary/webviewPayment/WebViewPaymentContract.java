package com.example.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment;

import com.example.inncoffee.ui.pago.tpvvinapplibrary.BasePresenter;
import com.example.inncoffee.ui.pago.tpvvinapplibrary.BaseView;

public interface WebViewPaymentContract {

    public interface Presenter extends BasePresenter {
        void closeWebViewActivity();

        void doWebViewSign(String str);
    }

    public interface View extends BaseView {
        void hideProgressBar();

        void loadWebView(TPVVWebViewClient.IWebViewCallback iWebViewCallback, String str, byte[] bArr);

        void loadWebView(String str);

        void showProgressBar();

        void showResultAlert(String str, String str2, int i);

        void showWebView();
    }
}

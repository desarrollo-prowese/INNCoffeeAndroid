package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.rakshasa.inncoffee.R;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.UseCaseHandler;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.TPVVDataSource;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.TPVVRepository;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.TPVVRemoteDataSource;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.TPVVURLConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase.WebViewPaymentUseCase;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase.WebViewSignUseCase;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import androidx.appcompat.app.AppCompatActivity;

public class WebViewPaymentActivity extends AppCompatActivity implements WebViewPaymentContract.View {
    private WebViewPaymentContract.Presenter mPresenter;
    private ProgressBar mProgressBar;
    private WebView webView;

    public void showResult(String str) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view_payment);
        this.webView = (WebView) findViewById(R.id.wv_webviewPayment);
        this.mProgressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        this.mPresenter = new WebViewPaymentPresenter(this, new WebViewPaymentUseCase(), new WebViewSignUseCase(TPVVRepository.getInstance((TPVVDataSource) TPVVRemoteDataSource.getInstance(this))), UseCaseHandler.getInstance());
        this.mPresenter.doWebViewSign(TPVVURLConstants.getUrlWsRealizarpago(TPVVConfiguration.getEnvironment()));
        if (TPVVConfiguration.getProgressBarColor() != null) {
            this.mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(TPVVConfiguration.getProgressBarColor()), PorterDuff.Mode.MULTIPLY);
        }
    }

    public void finishActivity() {
        finish();
    }

    public void loadWebView(TPVVWebViewClient.IWebViewCallback iWebViewCallback, String str, byte[] bArr) {
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setScrollBarStyle(33554432);
        this.webView.setWebViewClient(new TPVVWebViewClient(iWebViewCallback, this));
        this.webView.postUrl(str, bArr);
    }

    public void showProgressBar() {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgressBar() {
        this.mProgressBar.setVisibility(4);
    }

    public void showWebView() {
        this.webView.setVisibility(0);
    }

    public void showResultAlert(String str, String str2, int i) {
        Snackbar.make(findViewById(16908290), (CharSequence) str2, BaseTransientBottomBar.LENGTH_INDEFINITE).setActionTextColor(i).setAction((CharSequence) str, (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                WebViewPaymentActivity.this.onBackPressed();
            }
        }).show();
    }

    public void loadWebView(String str) {
        this.webView.loadUrl(str);
    }

    public void onBackPressed() {
        this.mPresenter.closeWebViewActivity();
    }
}

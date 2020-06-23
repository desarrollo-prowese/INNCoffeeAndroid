package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment;

import android.graphics.Color;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.ResultResponse;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVV;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.UseCase;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.UseCaseHandler;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.TPVVURLConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response.DatosRespuestaWVPayment;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase.WebViewPaymentUseCase;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase.WebViewSignUseCase;


public class WebViewPaymentPresenter implements WebViewPaymentContract.Presenter, TPVVWebViewClient.IWebViewCallback {
    /* access modifiers changed from: private */
    public ErrorResponse error;
    private final UseCaseHandler mUseCaseHandler;
    private final WebViewPaymentUseCase mWebViewPaymentUseCase;
    /* access modifiers changed from: private */
    public final WebViewPaymentContract.View mWebViewPaymentView;
    private final WebViewSignUseCase mWebViewSignUseCase;
    /* access modifiers changed from: private */
    public ResultResponse respuesta;
    /* access modifiers changed from: private */
    public Boolean status_error;
    private final String url_KO;
    private final String url_OK;

    public void start() {
    }

    public WebViewPaymentPresenter(WebViewPaymentContract.View view, WebViewPaymentUseCase webViewPaymentUseCase, WebViewSignUseCase webViewSignUseCase, UseCaseHandler useCaseHandler) {
        this.url_OK = TPVVConfiguration.getUrlOK() != null ? TPVVConfiguration.getUrlOK() : TPVVURLConstants.DEFAULT_URL_OK;
        this.url_KO = TPVVConfiguration.getUrlKO() != null ? TPVVConfiguration.getUrlKO() : TPVVURLConstants.DEFAULT_URL_KO;
        this.error = null;
        this.respuesta = null;
        this.status_error = false;
        this.mWebViewPaymentView = view;
        this.mWebViewPaymentUseCase = webViewPaymentUseCase;
        this.mWebViewSignUseCase = webViewSignUseCase;
        this.mUseCaseHandler = useCaseHandler;
    }

    public void doWebViewSign(final String str) {
        TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
        this.mWebViewPaymentView.showProgressBar();
        this.mUseCaseHandler.execute(this.mWebViewSignUseCase, new WebViewSignUseCase.RequestValues(), new UseCase.UseCaseCallback<WebViewSignUseCase.ResponseValues>() {
            public void onSuccess(WebViewSignUseCase.ResponseValues responseValues) {
                WebViewPaymentPresenter.this.mWebViewPaymentView.loadWebView(WebViewPaymentPresenter.this, str, responseValues.getPostParameters());
            }

            public void onError(WebViewSignUseCase.ResponseValues responseValues) {
                WebViewPaymentPresenter.this.mWebViewPaymentView.hideProgressBar();
                WebViewPaymentPresenter.this.mWebViewPaymentView.finishActivity();
                TPVV.mCallback.paymentResultKO(new ErrorResponse(responseValues.getError().getCode(), responseValues.getError().getDesc()));
            }
        });
    }

    public void onPageLoading(final String str) {
        WebViewPaymentUseCase.RequestValues requestValues = new WebViewPaymentUseCase.RequestValues(str);
        if (str.contains(this.url_OK) || str.contains(this.url_KO)) {
            this.mUseCaseHandler.execute(this.mWebViewPaymentUseCase, requestValues, new UseCase.UseCaseCallback<WebViewPaymentUseCase.ResponseValues>() {
                public void onSuccess(WebViewPaymentUseCase.ResponseValues responseValues) {
                    ResultResponse unused = WebViewPaymentPresenter.this.respuesta = WebViewPaymentPresenter.this.createResultResponse(responseValues.getmResponse());
                    TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
                    if (WebViewPaymentPresenter.this.respuesta == null || TPVVConfiguration.getUrlOK() == null || !TPVVConfiguration.isEnableRedirection()) {
                        TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
                        WebViewPaymentPresenter.this.mWebViewPaymentView.finishActivity();
                        TPVV.mCallback.paymentResultOK(WebViewPaymentPresenter.this.respuesta);
                        return;
                    }
                    TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = false;
                    WebViewPaymentPresenter.this.mWebViewPaymentView.loadWebView(str);
                }

                public void onError(WebViewPaymentUseCase.ResponseValues responseValues) {
                    ErrorResponse unused = WebViewPaymentPresenter.this.error = responseValues.getError();
                    TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
                    if (WebViewPaymentPresenter.this.error == null || TPVVConfiguration.getUrlKO() == null || !TPVVConfiguration.isEnableRedirection()) {
                        TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = true;
                        WebViewPaymentPresenter.this.mWebViewPaymentView.finishActivity();
                        TPVV.mCallback.paymentResultKO(responseValues.getError());
                        return;
                    }
                    TPVVConstants.FLAG_OVERRIDE_URL_WEBVIEW = false;
                    WebViewPaymentPresenter.this.mWebViewPaymentView.loadWebView(str);
                }
            });
        } else {
            this.mWebViewPaymentView.loadWebView(str);
        }
    }

    public void onErrorPageLoading(String str) {
        this.mWebViewPaymentView.finishActivity();
        TPVV.mCallback.paymentResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, str));
    }

    public void showAlertResult(String str) {
        if (!TPVVConfiguration.isEnableResultAlert().booleanValue()) {
            return;
        }
        if (this.respuesta != null) {
            this.mWebViewPaymentView.showResultAlert(TPVVConfiguration.getResultAlertTextButtonOk(), TPVVConfiguration.getResultAlertTextOk(), Color.parseColor(TPVVConfiguration.getResultAlertButtonColor()));
        } else if (this.error != null) {
            this.mWebViewPaymentView.showResultAlert(TPVVConfiguration.getResultAlertTextButtonKo(), TPVVConfiguration.getResultAlertTextKo(), Color.parseColor(TPVVConfiguration.getResultAlertButtonColor()));
        }
    }

    public void closeWebViewActivity() {
        if (this.respuesta != null) {
            this.mWebViewPaymentView.finishActivity();
            TPVV.mCallback.paymentResultOK(this.respuesta);
        }
        if (this.error != null) {
            this.mWebViewPaymentView.finishActivity();
            TPVV.mCallback.paymentResultKO(this.error);
        }
        if (this.status_error.booleanValue()) {
            this.mWebViewPaymentView.finishActivity();
            TPVV.mCallback.paymentResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "No se pudo realizar la operación: Error en datos enviados."));
        }
    }

    public void onPageLoadFinished(String str, WebView webView) {
        this.mWebViewPaymentView.hideProgressBar();
        this.mWebViewPaymentView.showWebView();
        if (TPVVConfiguration.isEnableResultAlert().booleanValue()) {
            if (this.respuesta != null) {
                this.mWebViewPaymentView.showResultAlert(TPVVConfiguration.getResultAlertTextButtonOk(), TPVVConfiguration.getResultAlertTextOk(), Color.parseColor(TPVVConfiguration.getResultAlertButtonColor()));
            } else if (this.error != null) {
                this.mWebViewPaymentView.showResultAlert(TPVVConfiguration.getResultAlertTextButtonKo(), TPVVConfiguration.getResultAlertTextKo(), Color.parseColor(TPVVConfiguration.getResultAlertButtonColor()));
            }
        }
        webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();", new ValueCallback<String>() {
            public void onReceiveValue(String str) {
                Log.d("HTML", str);
                if (str.contains("Error en datos enviados. Contacte con su comercio.")) {
                    Boolean unused = WebViewPaymentPresenter.this.status_error = true;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public ResultResponse createResultResponse(DatosRespuestaWVPayment datosRespuestaWVPayment) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setDate(datosRespuestaWVPayment.getResponseDate());
        resultResponse.setHour(datosRespuestaWVPayment.getResponseHour());
        resultResponse.setSecurePayment(datosRespuestaWVPayment.getResponseSecurePayment());
        resultResponse.setAmount(datosRespuestaWVPayment.getResponseAmount());
        resultResponse.setCurrency(datosRespuestaWVPayment.getResponseCurrency());
        resultResponse.setOrder(datosRespuestaWVPayment.getResponseOrder());
        resultResponse.setMerchantCode(datosRespuestaWVPayment.getResponseMerchantCode());
        resultResponse.setTerminal(datosRespuestaWVPayment.getResponseTerminal());
        resultResponse.setResponseCode(datosRespuestaWVPayment.getResponseResponse());
        resultResponse.setTransactionType(datosRespuestaWVPayment.getResponseTransactionType());
        resultResponse.setMerchantData(datosRespuestaWVPayment.getResponseMerchantData());
        resultResponse.setAuthorisationCode(datosRespuestaWVPayment.getResponseAuthorisationCode());
        resultResponse.setCardNumber(datosRespuestaWVPayment.getResponseCardNumber());
        resultResponse.setExpiryDate(datosRespuestaWVPayment.getResponseExpiryDate());
        resultResponse.setIdentifier(datosRespuestaWVPayment.getResponseIdentifier());
        resultResponse.setLanguage(datosRespuestaWVPayment.getResponseLanguage());
        resultResponse.setCardCountry(datosRespuestaWVPayment.getResponseCardCountry());
        resultResponse.setCardBrand(datosRespuestaWVPayment.getResponseCardBrand());
        resultResponse.setSignature(datosRespuestaWVPayment.getResponseSignature());
        return resultResponse;
    }
}

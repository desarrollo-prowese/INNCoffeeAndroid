package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase;

import android.net.Uri;
import android.util.Base64;

import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.UseCase;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.mapping.TPVVMapper;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.TPVVURLConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response.DatosRespuestaWVPayment;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewPaymentUseCase extends UseCase<WebViewPaymentUseCase.RequestValues, WebViewPaymentUseCase.ResponseValues> {
    private String url_KO = null;
    private String url_OK = null;

    /* access modifiers changed from: protected */
    public void executeUseCase(RequestValues requestValues) {
        this.url_OK = TPVVConfiguration.getUrlOK() != null ? TPVVConfiguration.getUrlOK() : TPVVURLConstants.DEFAULT_URL_OK;
        this.url_KO = TPVVConfiguration.getUrlKO() != null ? TPVVConfiguration.getUrlKO() : TPVVURLConstants.DEFAULT_URL_KO;
        if (requestValues.getUrl().contains(this.url_OK) || requestValues.getUrl().contains(this.url_KO)) {
            try {
                String queryParameter = Uri.parse(requestValues.getUrl()).getQueryParameter("Ds_MerchantParameters");
                DatosRespuestaWVPayment datosRespuestaWVPayment = (DatosRespuestaWVPayment) new TPVVMapper(DatosRespuestaWVPayment.class).toModel(new JSONObject(new String(Base64.decode(queryParameter.getBytes(), 8))));
                if (datosRespuestaWVPayment == null || datosRespuestaWVPayment.getResponseResponse() == null || queryParameter == null) {
                    getUseCaseCallback().onError(new ResponseValues(new ErrorResponse(TPVVConstants.CODE_ERROR_NO_FIELDS_IN_RESPONSE, "NO_FIELDS_IN_RESPONSE")));
                } else if (isValidResponseCode(datosRespuestaWVPayment.getResponseResponse()).booleanValue()) {
                    getUseCaseCallback().onSuccess(new ResponseValues(datosRespuestaWVPayment));
                } else {
                    getUseCaseCallback().onError(new ResponseValues(new ErrorResponse(TPVVConstants.CODE_SIS_ERROR, datosRespuestaWVPayment.getResponseResponse())));
                }
            } catch (JSONException unused) {
                getUseCaseCallback().onError(new ResponseValues(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "Mapping Error")));
            } catch (NullPointerException unused2) {
                getUseCaseCallback().onError(new ResponseValues(new ErrorResponse(TPVVConstants.CODE_ERROR_NO_FIELDS_IN_RESPONSE, "NO_FIELDS_IN_RESPONSE")));
            }
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String url;

        public RequestValues(String str) {
            this.url = str;
        }

        public String getUrl() {
            return this.url;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        ErrorResponse error;
        DatosRespuestaWVPayment mResponse;

        public ResponseValues(DatosRespuestaWVPayment datosRespuestaWVPayment) {
            this.mResponse = datosRespuestaWVPayment;
        }

        public ResponseValues(ErrorResponse errorResponse) {
            this.error = errorResponse;
        }

        public DatosRespuestaWVPayment getmResponse() {
            return this.mResponse;
        }

        public ErrorResponse getError() {
            return this.error;
        }
    }

    private Boolean isValidResponseCode(String str) {
        if (str == null) {
            return false;
        }
        int parseInt = Integer.parseInt(str);
        if (parseInt < 0 || parseInt > 99) {
            return false;
        }
        return true;
    }
}

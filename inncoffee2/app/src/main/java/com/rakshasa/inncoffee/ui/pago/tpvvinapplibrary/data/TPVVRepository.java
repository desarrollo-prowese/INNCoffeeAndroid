package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data;

import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.model.SignedResponseDto;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.mapping.TPVVMapper;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.TPVVURLConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.petition.GenericPetitionWVSign;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response.GenericResponseWV;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

public class TPVVRepository {
    private static TPVVRepository mInstance;
    private final TPVVDataSource mTPVVRemoteDataSource;

    public interface DirectPaymentDomainCallback<T, E> {
        void directPaymentResultKO(E e);

        void directPaymentResultOK(T t);
    }

    public interface WebViewSignDomainCallback<T, E> {
        void webviewSignResultKO(E e);

        void webviewSignResultOK(T t);
    }

    private TPVVRepository(TPVVDataSource tPVVDataSource) {
        this.mTPVVRemoteDataSource = tPVVDataSource;
    }

    public static TPVVRepository getInstance(TPVVDataSource tPVVDataSource) {
        if (mInstance == null) {
            mInstance = new TPVVRepository(tPVVDataSource);
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

    public void sendPetitionWebViewSign(GenericPetitionWVSign genericPetitionWVSign, final WebViewSignDomainCallback<GenericResponseWV, ErrorResponse> webViewSignDomainCallback) {
        JSONObject jSONObject;
        try {
            jSONObject = new TPVVMapper(GenericPetitionWVSign.class).toJsonSignedPetition(genericPetitionWVSign);
        } catch (JSONException unused) {
            webViewSignDomainCallback.webviewSignResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "MAPPING_ERROR"));
            jSONObject = null;
            this.mTPVVRemoteDataSource.sendPetition(TPVVURLConstants.getUrlWsNuevaFirma(TPVVConfiguration.getEnvironment()), jSONObject, new TPVVDataSource.SendPetitionRepositoryCallback<SignedResponseDto, ErrorResponse>() {
                public void onSendPetitionOK(SignedResponseDto signedResponseDto) {
                    GenericResponseWV genericResponseWV;
                    GenericResponseWV genericResponseWV2 = new GenericResponseWV();
                    try {
                        genericResponseWV = (GenericResponseWV) new TPVVMapper(GenericResponseWV.class).toModel(new JSONObject(signedResponseDto.getMensaje().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        webViewSignDomainCallback.webviewSignResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "MAPPING_ERROR"));
                        genericResponseWV = genericResponseWV2;
                    }
                    webViewSignDomainCallback.webviewSignResultOK(genericResponseWV);
                }

                public void onSendPetitionKO(ErrorResponse errorResponse) {
                    webViewSignDomainCallback.webviewSignResultKO(errorResponse);
                }
            });
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused2) {
            webViewSignDomainCallback.webviewSignResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "ENCRYPTION_SIGNATURE_ERROR"));
            jSONObject = null;
            this.mTPVVRemoteDataSource.sendPetition(TPVVURLConstants.getUrlWsNuevaFirma(TPVVConfiguration.getEnvironment()), jSONObject, new TPVVDataSource.SendPetitionRepositoryCallback<SignedResponseDto, ErrorResponse>() {
                public void onSendPetitionOK(SignedResponseDto signedResponseDto) {
                    GenericResponseWV genericResponseWV;
                    GenericResponseWV genericResponseWV2 = new GenericResponseWV();
                    try {
                        genericResponseWV = (GenericResponseWV) new TPVVMapper(GenericResponseWV.class).toModel(new JSONObject(signedResponseDto.getMensaje().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        webViewSignDomainCallback.webviewSignResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "MAPPING_ERROR"));
                        genericResponseWV = genericResponseWV2;
                    }
                    webViewSignDomainCallback.webviewSignResultOK(genericResponseWV);
                }

                public void onSendPetitionKO(ErrorResponse errorResponse) {
                    webViewSignDomainCallback.webviewSignResultKO(errorResponse);
                }
            });
        }
        this.mTPVVRemoteDataSource.sendPetition(TPVVURLConstants.getUrlWsNuevaFirma(TPVVConfiguration.getEnvironment()), jSONObject, new TPVVDataSource.SendPetitionRepositoryCallback<SignedResponseDto, ErrorResponse>() {
            public void onSendPetitionOK(SignedResponseDto signedResponseDto) {
                GenericResponseWV genericResponseWV;
                GenericResponseWV genericResponseWV2 = new GenericResponseWV();
                try {
                    genericResponseWV = (GenericResponseWV) new TPVVMapper(GenericResponseWV.class).toModel(new JSONObject(signedResponseDto.getMensaje().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    webViewSignDomainCallback.webviewSignResultKO(new ErrorResponse(TPVVConstants.CODE_GENERIC_ERROR, "MAPPING_ERROR"));
                    genericResponseWV = genericResponseWV2;
                }
                webViewSignDomainCallback.webviewSignResultOK(genericResponseWV);
            }

            public void onSendPetitionKO(ErrorResponse errorResponse) {
                webViewSignDomainCallback.webviewSignResultKO(errorResponse);
            }
        });
    }
}

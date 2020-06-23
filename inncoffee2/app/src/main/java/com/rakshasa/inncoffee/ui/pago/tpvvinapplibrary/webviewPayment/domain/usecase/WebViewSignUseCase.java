package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.usecase;

import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.ErrorResponse;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.UseCase;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.TPVVRepository;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.TPVVURLConstants;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.petition.DatosPeticionWVSign;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.petition.GenericPetitionWVSign;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.webviewPayment.domain.model.response.GenericResponseWV;

public class WebViewSignUseCase extends UseCase<WebViewSignUseCase.RequestValues, WebViewSignUseCase.ResponseValues> {
    private final TPVVRepository mTPVVRepository;

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public WebViewSignUseCase(TPVVRepository tPVVRepository) {
        this.mTPVVRepository = tPVVRepository;
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(RequestValues requestValues) {
        this.mTPVVRepository.sendPetitionWebViewSign(createGenericPetition(), new TPVVRepository.WebViewSignDomainCallback<GenericResponseWV, ErrorResponse>() {
            public void webviewSignResultOK(GenericResponseWV genericResponseWV) {
                if (WebViewSignUseCase.this.isOperationOk(genericResponseWV.getCode())) {
                    WebViewSignUseCase.this.getUseCaseCallback().onSuccess(new ResponseValues(genericResponseWV.getDatosPeticion().getPostData()));
                    return;
                }
                WebViewSignUseCase.this.getUseCaseCallback().onError(new ResponseValues(new ErrorResponse(genericResponseWV.getCode(), genericResponseWV.getDesc())));
            }

            public void webviewSignResultKO(ErrorResponse errorResponse) {
                WebViewSignUseCase.this.getUseCaseCallback().onError(new ResponseValues(errorResponse));
            }
        });
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        ErrorResponse error;
        byte[] postParameters;

        public ResponseValues(byte[] bArr) {
            this.postParameters = bArr;
        }

        public ResponseValues(ErrorResponse errorResponse) {
            this.error = errorResponse;
        }

        public byte[] getPostParameters() {
            return this.postParameters;
        }

        public ErrorResponse getError() {
            return this.error;
        }
    }

    private GenericPetitionWVSign createGenericPetition() {
        DatosPeticionWVSign datosPeticionWVSign = new DatosPeticionWVSign();
        datosPeticionWVSign.setDs_Merchant_MerchantCode(TPVVConfiguration.getFuc());
        datosPeticionWVSign.setDs_Merchant_TransactionType(TPVVConfiguration.getOperationType());
        datosPeticionWVSign.setDs_Merchant_Terminal(TPVVConfiguration.getTerminal());
        datosPeticionWVSign.setDs_Merchant_UrlOK(TPVVConfiguration.getUrlOK() != null ? TPVVConfiguration.getUrlOK() : TPVVURLConstants.DEFAULT_URL_OK);
        datosPeticionWVSign.setDs_Merchant_UrlKO(TPVVConfiguration.getUrlKO() != null ? TPVVConfiguration.getUrlKO() : TPVVURLConstants.DEFAULT_URL_KO);
        datosPeticionWVSign.setDs_Merchant_Amount(TPVVConfiguration.getAmount());
        datosPeticionWVSign.setDs_Merchant_Currency(TPVVConfiguration.getCurrency());
        datosPeticionWVSign.setDs_Merchant_Order(TPVVConfiguration.getOrder());
        datosPeticionWVSign.setDs_Merchant_DirectPayment("false");
        if (TPVVConfiguration.getLanguage() != null) {
            datosPeticionWVSign.setDs_Merchant_ConsumerLanguage(TPVVConfiguration.getLanguage());
        }
        if (TPVVConfiguration.getMerchantUrl() != null) {
            datosPeticionWVSign.setDs_Merchant_MerchantURL(TPVVConfiguration.getMerchantUrl());
        }
        if (TPVVConfiguration.getMerchantDescriptor() != null) {
            datosPeticionWVSign.setDs_merchant_MerchantDescriptor(TPVVConfiguration.getMerchantDescriptor());
        }
        if (TPVVConfiguration.getGroup() != null) {
            datosPeticionWVSign.setDs_Merchant_Group(TPVVConfiguration.getGroup());
        }
        if (TPVVConfiguration.getIdentifier() != null) {
            datosPeticionWVSign.setDs_Merchant_Identifier(TPVVConfiguration.getIdentifier());
        }
        if (TPVVConfiguration.getPaymentMethods() != null) {
            datosPeticionWVSign.setDs_Merchant_PayMethods(TPVVConfiguration.getPaymentMethods());
        }
        if (TPVVConfiguration.getMerchantName() != null) {
            datosPeticionWVSign.setDs_Merchant_MerchantName(TPVVConfiguration.getMerchantName());
        }
        if (TPVVConfiguration.getMerchantData() != null) {
            datosPeticionWVSign.setDs_Merchant_MerchantData(TPVVConfiguration.getMerchantData());
        }
        if (TPVVConfiguration.getTitular() != null) {
            datosPeticionWVSign.setDs_Merchant_Titular(TPVVConfiguration.getTitular());
        }
        if (TPVVConfiguration.getDescription() != null) {
            datosPeticionWVSign.setDs_Merchant_ProductDescription(TPVVConfiguration.getDescription());
        }
        GenericPetitionWVSign genericPetitionWVSign = new GenericPetitionWVSign();
        genericPetitionWVSign.setParametros(datosPeticionWVSign);
        genericPetitionWVSign.setTerminal(TPVVConfiguration.getTerminal());
        genericPetitionWVSign.setFuc(TPVVConfiguration.getFuc());
        genericPetitionWVSign.setBundle(TPVVConfiguration.getPackageName());
        return genericPetitionWVSign;
    }

    /* access modifiers changed from: private */
    public Boolean isOperationOk(String str) {
        Integer.parseInt(str);
        if (str.equals("0")) {
            return true;
        }
        return false;
    }
}

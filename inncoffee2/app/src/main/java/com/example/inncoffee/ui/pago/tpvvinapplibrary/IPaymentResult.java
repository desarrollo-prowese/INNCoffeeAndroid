package com.example.inncoffee.ui.pago.tpvvinapplibrary;

public interface IPaymentResult {
    void paymentResultKO(ErrorResponse errorResponse);

    void paymentResultOK(ResultResponse resultResponse);
}

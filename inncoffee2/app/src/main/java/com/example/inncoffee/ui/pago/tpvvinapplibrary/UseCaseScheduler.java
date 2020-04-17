package com.example.inncoffee.ui.pago.tpvvinapplibrary;


public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <V extends UseCase.ResponseValues> void notifyResponse(V v, UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValues> void onError(V v, UseCase.UseCaseCallback<V> useCaseCallback);
}

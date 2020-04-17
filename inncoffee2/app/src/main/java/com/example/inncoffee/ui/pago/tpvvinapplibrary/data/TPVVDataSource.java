package com.example.inncoffee.ui.pago.tpvvinapplibrary.data;

public interface TPVVDataSource<S, T, R, E> {

    public interface SendPetitionRepositoryCallback<V, E> {
        void onSendPetitionKO(E e);

        void onSendPetitionOK(V v);
    }

    void sendPetition(S s, T t, SendPetitionRepositoryCallback<R, E> sendPetitionRepositoryCallback);
}

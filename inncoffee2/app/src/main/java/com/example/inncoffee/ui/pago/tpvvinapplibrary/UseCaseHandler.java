package com.example.inncoffee.ui.pago.tpvvinapplibrary;

public class UseCaseHandler {
    private static UseCaseHandler INSTANCE;
    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(final UseCase<T, R> useCase, T t, UseCase.UseCaseCallback<R> useCaseCallback) {
        useCase.setRequestValues(t);
        useCase.setUseCaseCallback(new UiCallbackWrapper(useCaseCallback, this));
        this.mUseCaseScheduler.execute(new Runnable() {
            public void run() {
                useCase.run();
            }
        });
    }

    public <V extends UseCase.ResponseValues> void notifyResponse(V v, UseCase.UseCaseCallback<V> useCaseCallback) {
        this.mUseCaseScheduler.notifyResponse(v, useCaseCallback);
    }

    /* access modifiers changed from: private */
    public <V extends UseCase.ResponseValues> void notifyError(V v, UseCase.UseCaseCallback<V> useCaseCallback) {
        this.mUseCaseScheduler.onError(v, useCaseCallback);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> useCaseCallback, UseCaseHandler useCaseHandler) {
            this.mCallback = useCaseCallback;
            this.mUseCaseHandler = useCaseHandler;
        }

        public void onSuccess(V v) {
            this.mUseCaseHandler.notifyResponse(v, this.mCallback);
        }

        public void onError(V v) {
            this.mUseCaseHandler.notifyError(v, this.mCallback);
        }
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }
}

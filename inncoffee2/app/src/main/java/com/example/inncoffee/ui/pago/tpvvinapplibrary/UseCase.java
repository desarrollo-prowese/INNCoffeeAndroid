package com.example.inncoffee.ui.pago.tpvvinapplibrary;


public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {
    private Q mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface UseCaseCallback<R> {
        void onError(R r);

        void onSuccess(R r);
    }

    /* access modifiers changed from: protected */
    public abstract void executeUseCase(Q q);

    public void setRequestValues(Q q) {
        this.mRequestValues = q;
    }

    public Q getRequestValues() {
        return this.mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return this.mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    /* access modifiers changed from: package-private */
    public void run() {
        executeUseCase(this.mRequestValues);
    }
}

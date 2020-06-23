package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary;

import android.os.Handler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {
    public static final int MAX_POOL_SIZE = 1;
    public static final int POOL_SIZE = 1;
    public static final int TIMEOUT = 30;
    private final Handler mHandler = new Handler();
    ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(1));

    public void execute(Runnable runnable) {
        this.mThreadPoolExecutor.execute(runnable);
    }

    public <V extends UseCase.ResponseValues> void notifyResponse(final V v, final UseCase.UseCaseCallback<V> useCaseCallback) {
        this.mHandler.post(new Runnable() {
            public void run() {
                useCaseCallback.onSuccess(v);
            }
        });
    }

    public <V extends UseCase.ResponseValues> void onError(final V v, final UseCase.UseCaseCallback<V> useCaseCallback) {
        this.mHandler.post(new Runnable() {
            public void run() {
                useCaseCallback.onError(v);
            }
        });
    }
}

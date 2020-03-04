package com.android.basics.core;

/**
 * A scheduler that executes synchronously, for testing purposes.
 */
public class TestUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <R extends UseCase.Response> void notifyResponse(R response,
                                                            UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }

    @Override
    public <R extends UseCase.Response> void onError(Error error, UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseCallback.onError(error);
    }

}
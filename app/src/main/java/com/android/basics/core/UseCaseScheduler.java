package com.android.basics.core;

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <R extends UseCase.Response> void notifyResponse(final R response,
                                                     final UseCase.UseCaseCallback<R> useCaseCallback);

    <R extends UseCase.Response> void onError(final Error error,
            final UseCase.UseCaseCallback<R> useCaseCallback);
}

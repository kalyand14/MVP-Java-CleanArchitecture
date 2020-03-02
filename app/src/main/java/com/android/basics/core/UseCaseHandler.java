package com.android.basics.core;

import com.android.basics.core.utils.EspressoIdlingResource;

public class UseCaseHandler {

    private static UseCaseHandler INSTANCE;

    private final UseCaseScheduler useCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }

    private static final class UiCallbackWrapper<R extends UseCase.Response> implements
            UseCase.UseCaseCallback<R> {
        private final UseCase.UseCaseCallback<R> callback;
        private final UseCaseHandler useCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<R> callback,
                                 UseCaseHandler useCaseHandler) {
            this.callback = callback;
            this.useCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(R response) {
            useCaseHandler.notifyResponse(response, callback);
        }

        @Override
        public void onError(Error error) {
            useCaseHandler.notifyError(error, callback);
        }
    }

    public <R extends UseCase.Response> void notifyResponse(final R response,
                                                            final UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    private <R extends UseCase.Response> void notifyError(final Error error,
            final UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseScheduler.onError(error, useCaseCallback);
    }

    public <T extends UseCase.Request, R extends UseCase.Response> void execute(
            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequest(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        // The network request might be handled in a different thread so make sure
        // Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        useCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {

                useCase.run();
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
            }
        });
    }

}

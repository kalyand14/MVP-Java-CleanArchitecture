package com.android.basics.core;

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <P> the request type
 * @param <R> the response type
 */
public abstract class UseCase<P extends UseCase.Request, R extends UseCase.Response> {

    private P request;

    private boolean disposed;

    private UseCaseCallback<R> useCaseCallback;

    public void setRequest(P request) {
        this.request = request;
    }

    public P getRequest() {
        return this.request;
    }

    public UseCaseCallback<R> getUseCaseCallback() {
        return useCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<R> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    void run() {
        disposed = false;
        executeUseCase(request);
    }

    protected abstract void executeUseCase(P request);

    /**
     * Data passed to a request.
     */
    public interface Request {
    }

    /**
     * Data received from a request.
     */
    public interface Response {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);

        void onError(Error error);
    }

    public void dispose() {
        disposed = true;
    }

    public boolean isNotDisposed() {
        return !disposed;
    }

    public boolean isDisposed() {
        return disposed;
    }
}
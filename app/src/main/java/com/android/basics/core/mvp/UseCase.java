package com.android.basics.core.mvp;

import com.android.basics.core.Callback;

public abstract class UseCase<P, T> {

    private boolean disposed;

    public void execute(P param, Callback<T> callback) {
        disposed = false;
        executeTask(param, callback);
    }

    protected abstract void executeTask(P param, Callback<T> callback);

    public void dispose() {
        disposed = true;
    }

    public boolean isDisposed() {
        return disposed;
    }
}

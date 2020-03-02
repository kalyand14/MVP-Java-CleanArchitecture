package com.android.basics.core;

public interface Callback<T> {
    void onResponse(T response);

    void onError(Error todoError);
}

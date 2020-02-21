package com.android.basics.data.component;

public interface DaoCallback<T> {
    T doAsync();

    void onComplete(T response);
}

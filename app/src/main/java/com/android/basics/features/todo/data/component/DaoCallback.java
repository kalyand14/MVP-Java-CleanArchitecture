package com.android.basics.features.todo.data.component;

public interface DaoCallback<T> {
    T doAsync();

    void onComplete(T response);
}

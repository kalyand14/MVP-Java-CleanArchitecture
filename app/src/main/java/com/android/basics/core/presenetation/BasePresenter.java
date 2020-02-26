package com.android.basics.core.presenetation;

public interface BasePresenter<V> {

    void attach(V view);

    void detach();
}

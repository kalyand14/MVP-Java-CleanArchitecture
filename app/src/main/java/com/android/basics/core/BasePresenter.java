package com.android.basics.core;

public interface BasePresenter<V> {

    void attach(V view);

    void detach();
}

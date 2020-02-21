package com.android.basics.core.mvp;

public interface BasePresenter<V> {

    void attach(V view);

    void detach();
}

package com.android.basics.core.domain;

public interface Callback<T> {
    void onResponse(T response);

    void onError(String errorcode, String errorResponse);
}

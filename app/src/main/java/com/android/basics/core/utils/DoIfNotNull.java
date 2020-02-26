package com.android.basics.core.utils;

public class DoIfNotNull {
    public interface Callback<T> {
        void exec(T o);
    }

    public static <T> void let(T o, Callback<T> callback) {
        if (o != null) {
            callback.exec(o);
        }
    }
}

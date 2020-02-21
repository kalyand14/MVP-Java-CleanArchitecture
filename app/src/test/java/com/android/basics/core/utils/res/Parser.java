package com.android.basics.core.utils.res;

import java.util.Map;

public interface Parser<K, V> {

    interface onFileParsedListener<K, V> {
        void onFileParsedSuccessfully(Map<? extends K, ? extends V> m);
    }

    void parse(onFileParsedListener<K, V> onFileParsedListener);
}

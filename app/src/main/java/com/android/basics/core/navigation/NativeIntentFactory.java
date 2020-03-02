package com.android.basics.core.navigation;

import android.content.Context;
import android.content.Intent;

public class NativeIntentFactory implements IntentFactory {

    @Override
    public Intent create(Context context, Class<? extends Context> clazz) {
        return new Intent(context, clazz);
    }

    @Override
    public Intent create(String action) {
        return new Intent(action);
    }

    @Override
    public Intent create() {
        return new Intent();
    }
}

package com.android.basics.core.presenetation.navigation;

import android.content.Context;
import android.content.Intent;

public interface IntentFactory {
    Intent create(Context context, Class<? extends Context> clazz);

    Intent create(String action);

    Intent create();
}

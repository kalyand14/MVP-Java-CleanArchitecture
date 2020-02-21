package com.android.basics.core.utils;

import android.util.DisplayMetrics;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

public interface ResourceManager {

    String getString(@StringRes int resourceId);

    String getString(@StringRes int resourceId, Object... formatArgs);

    String[] getStringArray(@ArrayRes int resourceId);

    int getColor(@ColorRes int resourceId);

    String getQuantityString(@PluralsRes int resourceId, int quantity);

    String getQuantityString(@PluralsRes int resourceId, int quantity, Object... formatArgs);

    int getInteger(@IntegerRes int resourceId);

    DisplayMetrics getDisplayMetrics();
}

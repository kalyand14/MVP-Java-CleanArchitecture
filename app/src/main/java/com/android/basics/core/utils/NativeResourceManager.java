package com.android.basics.core.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.IntegerRes;
import androidx.annotation.PluralsRes;

public class NativeResourceManager implements ResourceManager {

    private final Resources resource;

    public NativeResourceManager(Resources resource) {
        this.resource = resource;
    }

    @Override
    public String getString(int resourceId) {
        return resource.getString(resourceId);
    }

    @Override
    public String getString(int resourceId, Object... formatArgs) {
        return resource.getString(resourceId, formatArgs);
    }

    @Override
    public String[] getStringArray(int resourceId) {
        return resource.getStringArray(resourceId);
    }

    @Override
    public int getColor(int resourceId) {
        return resource.getColor(resourceId);
    }

    @Override
    public String getQuantityString(@PluralsRes int resourceId, int quantity) {
        return resource.getQuantityString(resourceId, quantity);
    }

    @Override
    public String getQuantityString(@PluralsRes int resourceId, int quantity, Object... formatArgs) {
        return resource.getQuantityString(resourceId, quantity, formatArgs);
    }

    @Override
    public int getInteger(@IntegerRes int resourceId) {
        return resource.getInteger(resourceId);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        return resource.getDisplayMetrics();
    }
}

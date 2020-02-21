package com.android.basics.core.navigation;

import android.os.Bundle;

public class NativeBundleFactory implements BundleFactory {
    @Override
    public Bundle create() {
        return new Bundle();
    }
}

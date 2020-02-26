package com.android.basics.core.presenetation.navigation;

import android.os.Bundle;

public class NativeBundleFactory implements BundleFactory {
    @Override
    public Bundle create() {
        return new Bundle();
    }
}

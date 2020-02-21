package com.android.basics.core;

import android.app.Application;

import com.android.basics.di.ApplicationModule;
import com.android.basics.di.ApplicationScope;

public class TodoApplication extends Application {


    private ApplicationScope applicationScope;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationScope = ApplicationScope.getInstance();
        applicationScope.setModule(new ApplicationModule(this));

    }

    public ApplicationScope getApplicationScope() {
        return applicationScope;
    }

}

package com.android.basics.presentation.splash;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationScope;
import com.android.basics.presentation.TodoNavigator;

public class SplashInjector {

    private ApplicationScope applicationScope;
    private static SplashInjector instance = null;

    private SplashInjector() {
    }

    public static SplashInjector getInstance() {
        if (instance == null) {
            instance = new SplashInjector();
        }
        return instance;
    }

    public void inject(SplashActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectObject(activity);
    }

    private void injectObject(SplashActivity activity) {
        activity.presenter = new SplashPresenter(provideNavigator(activity));
    }

    private SplashContract.Navigator provideNavigator(SplashActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    public void destroy() {
        instance = null;
    }

}

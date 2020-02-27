package com.android.basics.features.todo.presentation.splash;

import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.presentation.TodoNavigator;

public class SplashInjector {

    private ApplicationScope applicationScope;

    public SplashInjector(ApplicationScope applicationScope) {
        this.applicationScope = applicationScope;
    }

    public void inject(SplashActivity activity) {
        injectObject(activity);
    }

    private void injectObject(SplashActivity activity) {
        activity.presenter = new SplashPresenter(provideNavigator(activity));
    }

    private SplashContract.Navigator provideNavigator(SplashActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }


}

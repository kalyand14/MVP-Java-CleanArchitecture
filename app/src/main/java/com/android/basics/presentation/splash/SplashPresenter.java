package com.android.basics.presentation.splash;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.Navigator navigator;
    private SplashContract.View view;

    public SplashPresenter(SplashContract.Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void navigate() {
        navigator.goToLoginScreen();
    }

    @Override
    public void attach(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}

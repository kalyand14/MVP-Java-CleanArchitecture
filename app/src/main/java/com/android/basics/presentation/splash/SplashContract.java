package com.android.basics.presentation.splash;

import com.android.basics.core.mvp.BasePresenter;

public interface SplashContract {

    interface View {
    }

    interface Presenter extends BasePresenter<View> {
        void navigate();
    }

    interface Navigator {
        void goToLoginScreen();
    }
}

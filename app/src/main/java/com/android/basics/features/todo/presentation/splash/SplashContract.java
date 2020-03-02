package com.android.basics.features.todo.presentation.splash;

import com.android.basics.core.BasePresenter;

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

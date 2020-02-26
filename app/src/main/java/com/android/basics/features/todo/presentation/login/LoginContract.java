package com.android.basics.features.todo.presentation.login;

import com.android.basics.core.presenetation.BasePresenter;

public interface LoginContract {
    interface View {

        void showProgressDialog();

        void dismissProgressDialog();

        void showAuthenticationError();

    }

    interface Presenter extends BasePresenter<View> {
        void OnLoginClick(String userName, String password);

        void onRegisterClick();
    }

    interface Navigator {
        void goToLoginScreen();

        void goToRegisterScreen();

        void goToHomeScreen();
    }
}

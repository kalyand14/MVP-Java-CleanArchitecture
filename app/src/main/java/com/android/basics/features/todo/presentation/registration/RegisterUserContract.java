package com.android.basics.features.todo.presentation.registration;

import com.android.basics.core.BasePresenter;

public interface RegisterUserContract {

    interface View {
        void showProgressDialog();

        void dismissProgressDialog();

        void showRegistrationError();

        void showRegistrationSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void onRegisterClick(String userName, String password);

        void onLoginClick();

        void onRegistrationSuccess();
    }

    interface Navigator {
        void goToLoginScreen();

        void goToHomeScreen();
    }
}

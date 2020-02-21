package com.android.basics.presentation.registration;

import com.android.basics.core.Callback;
import com.android.basics.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.components.UserSession;

public class RegisterUserPresenter implements RegisterUserContract.Presenter {

    private RegisterUserInteractor registerUserInteractor;

    private RegisterUserContract.View view;

    private RegisterUserContract.Navigator navigator;

    private UserSession session;

    public RegisterUserPresenter(RegisterUserContract.Navigator navigator, RegisterUserInteractor registerUserInteractor, UserSession session) {
        this.registerUserInteractor = registerUserInteractor;
        this.navigator = navigator;
        this.session = session;
    }

    @Override
    public void onRegisterClick(String userName, String password) {

        view.showProgressDialog();

        registerUserInteractor.execute(RegisterUserInteractor.Params.forUser(userName, password), new Callback<User>() {
            @Override
            public void onResponse(User response) {

                session.setUser(response);

                view.dismissProgressDialog();

                view.showRegistrationSuccess();
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                view.dismissProgressDialog();
                view.showRegistrationError();
            }
        });

    }

    @Override
    public void onLoginClick() {
        navigator.goToLoginScreen();
    }

    @Override
    public void onRegistrationSuccess() {
        navigator.goToHomeScreen();
    }

    @Override
    public void attach(RegisterUserContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}

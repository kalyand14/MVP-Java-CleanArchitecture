package com.android.basics.presentation.login;

import com.android.basics.core.Callback;
import com.android.basics.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.components.UserSession;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.Navigator navigator;

    private AuthenticateUserInteractor authenticateUserInteractor;

    private LoginContract.View view;

    private UserSession session;

    public LoginPresenter(LoginContract.Navigator navigator, AuthenticateUserInteractor authenticateUserInteractor, UserSession session) {
        this.navigator = navigator;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.session = session;
    }

    @Override
    public void OnLoginClick(String userName, String password) {
        view.showProgressDialog();

        authenticateUserInteractor.execute(AuthenticateUserInteractor.Params.forUser(userName, password), new Callback<User>() {
            @Override
            public void onResponse(User response) {

                session.setUser(response);

                view.dismissProgressDialog();
                if (response.getUserId() != 0) {
                    navigator.goToHomeScreen();
                } else {
                    view.showAuthenticationError();
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                view.dismissProgressDialog();
                view.showAuthenticationError();
            }
        });

    }

    @Override
    public void onRegisterClick() {
        navigator.goToRegisterScreen();
    }

    @Override
    public void attach(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}

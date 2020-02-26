package com.android.basics.features.todo.presentation.login;

import com.android.basics.core.domain.Callback;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.presentation.components.UserSession;

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
                DoIfNotNull.let(view, LoginContract.View::dismissProgressDialog);
                if (response != null) {
                    session.setUser(response);
                    navigator.goToHomeScreen();
                } else {
                    DoIfNotNull.let(view, LoginContract.View::showAuthenticationError);
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                DoIfNotNull.let(view, view -> {
                    view.dismissProgressDialog();
                    view.showAuthenticationError();
                });

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
        authenticateUserInteractor.dispose();
        this.view = null;
    }
}

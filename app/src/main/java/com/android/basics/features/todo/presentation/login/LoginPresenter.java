package com.android.basics.features.todo.presentation.login;

import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.features.todo.presentation.components.UserSession;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.Navigator navigator;

    private AuthenticateUserInteractor authenticateUserInteractor;

    private LoginContract.View view;

    private UserSession session;

    private final UseCaseHandler useCaseHandler;

    public LoginPresenter(LoginContract.Navigator navigator, AuthenticateUserInteractor authenticateUserInteractor, UserSession session, UseCaseHandler useCaseHandler) {
        this.navigator = navigator;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.session = session;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void OnLoginClick(String userName, String password) {
        view.showProgressDialog();
        useCaseHandler.execute(authenticateUserInteractor, new AuthenticateUserInteractor.Request(userName, password), new UseCase.UseCaseCallback<AuthenticateUserInteractor.Response>() {
            @Override
            public void onSuccess(AuthenticateUserInteractor.Response response) {
                DoIfNotNull.let(view, LoginContract.View::dismissProgressDialog);
                if (response.getUser() != null) {
                    session.setUser(response.getUser());
                    navigator.goToHomeScreen();
                } else {
                    DoIfNotNull.let(view, LoginContract.View::showAuthenticationError);
                }
            }

            @Override
            public void onError(Error error) {
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

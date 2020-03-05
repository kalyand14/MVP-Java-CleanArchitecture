package com.android.basics.features.todo.presentation.registration;

import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.features.todo.presentation.components.UserSession;

public class RegisterUserPresenter implements RegisterUserContract.Presenter {

    private RegisterUserInteractor registerUserInteractor;

    private RegisterUserContract.View view;

    private RegisterUserContract.Navigator navigator;

    private UserSession session;

    private final UseCaseHandler useCaseHandler;

    public RegisterUserPresenter(RegisterUserContract.Navigator navigator, RegisterUserInteractor registerUserInteractor, UserSession session, UseCaseHandler useCaseHandler) {
        this.registerUserInteractor = registerUserInteractor;
        this.navigator = navigator;
        this.session = session;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void onRegisterClick(String userName, String password) {

        if (userName.isEmpty() || password.isEmpty()) {
            view.showValidationError();
        } else {
            view.showProgressDialog();

            useCaseHandler.execute(registerUserInteractor, new RegisterUserInteractor.Request(userName, password), new UseCase.UseCaseCallback<RegisterUserInteractor.Response>() {
                @Override
                public void onSuccess(RegisterUserInteractor.Response response) {
                    session.setUser(response.getUser());

                    DoIfNotNull.let(view, view -> {
                        view.dismissProgressDialog();
                        view.showRegistrationSuccess();
                    });
                }

                @Override
                public void onError(Error error) {
                    DoIfNotNull.let(view, view -> {
                        view.dismissProgressDialog();
                        view.showRegistrationError();
                    });
                }
            });
        }
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
        registerUserInteractor.dispose();
        this.view = null;
    }
}

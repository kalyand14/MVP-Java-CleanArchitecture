package com.android.basics.features.todo.presentation.login;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.features.todo.presentation.TodoNavigator;
import com.android.basics.features.todo.presentation.components.UserSession;

public class LoginInjector {

    private ApplicationScope applicationScope;

    private static LoginInjector instance = null;

    private LoginInjector() {
    }

    public static LoginInjector getInstance() {
        if (instance == null) {
            instance = new LoginInjector();
        }
        return instance;
    }

    public void inject(LoginActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(LoginActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
        activity.progressDialog.setMessage("Logging in");
    }

    private void injectObject(LoginActivity activity) {
        activity.presenter = new LoginPresenter(provideNavigator(activity), provideAuthenticator(), UserSession.getInstance(),  UseCaseHandler.getInstance());
    }

    private LoginContract.Navigator provideNavigator(LoginActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    private AuthenticateUserInteractor provideAuthenticator() {
        return new AuthenticateUserInteractor(applicationScope.userRepository());
    }

    public void destroy() {
        instance = null;

    }
}

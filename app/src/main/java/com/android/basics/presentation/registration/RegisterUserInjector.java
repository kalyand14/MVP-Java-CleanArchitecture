package com.android.basics.presentation.registration;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationScope;
import com.android.basics.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.presentation.TodoNavigator;
import com.android.basics.presentation.components.UserSession;

public class RegisterUserInjector {

    private ApplicationScope applicationScope;

    private static RegisterUserInjector instance = null;

    private RegisterUserInjector() {
    }

    public static RegisterUserInjector getInstance() {
        if (instance == null) {
            instance = new RegisterUserInjector();
        }
        return instance;
    }

    public void inject(RegisterUserActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(RegisterUserActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(RegisterUserActivity activity) {
        activity.presenter = new RegisterUserPresenter(provideNavigator(activity), provideRegisterUser(), UserSession.getInstance());
    }

    private RegisterUserContract.Navigator provideNavigator(RegisterUserActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    private RegisterUserInteractor provideRegisterUser() {
        return new RegisterUserInteractor(applicationScope.userRepository());
    }

    public void destroy() {
        instance = null;
    }
}

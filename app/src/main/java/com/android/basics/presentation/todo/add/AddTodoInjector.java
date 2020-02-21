package com.android.basics.presentation.todo.add;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationScope;
import com.android.basics.domain.interactor.todo.AddTodoInteractor;
import com.android.basics.presentation.TodoNavigator;
import com.android.basics.presentation.components.UserSession;

public class AddTodoInjector {

    private ApplicationScope applicationScope;
    private static AddTodoInjector instance = null;

    private AddTodoInjector() {
    }

    public static AddTodoInjector getInstance() {
        if (instance == null) {
            instance = new AddTodoInjector();
        }
        return instance;
    }

    public void inject(AddTodoActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(AddTodoActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(AddTodoActivity activity) {
        activity.presenter = new AddTodoPresenter(provideNavigator(activity), provideAddTodo(), UserSession.getInstance());
    }

    private AddTodoContract.Navigator provideNavigator(AddTodoActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    private AddTodoInteractor provideAddTodo() {
        return new AddTodoInteractor(applicationScope.todoRepository());
    }

    public void destroy() {
        instance = null;
    }

}

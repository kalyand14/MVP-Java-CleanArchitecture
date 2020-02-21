package com.android.basics.presentation.todo.edit;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationScope;
import com.android.basics.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.presentation.TodoNavigator;
import com.android.basics.presentation.components.TodoSession;

public class EditTodoInjector {

    private ApplicationScope applicationScope;
    private static EditTodoInjector instance = null;

    private EditTodoInjector() {
    }

    public static EditTodoInjector getInstance() {
        if (instance == null) {
            instance = new EditTodoInjector();
        }
        return instance;
    }

    public void inject(EditTodoActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(EditTodoActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(EditTodoActivity activity) {
        activity.presenter = new EditTodoPresenter(provideEditTodoInteractor(), provideDeleteTodoInteractor(), provideNavigator(activity), TodoSession.getInstance());
    }

    private EditTodoContract.Navigator provideNavigator(EditTodoActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    private EditTodoInteractor provideEditTodoInteractor() {
        return new EditTodoInteractor(applicationScope.todoRepository());
    }

    private DeleteTodoInteractor provideDeleteTodoInteractor() {
        return new DeleteTodoInteractor(applicationScope.todoRepository());
    }

    public void destroy() {
        instance = null;
    }

}

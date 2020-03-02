package com.android.basics.features.todo.presentation.todo.edit;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.features.todo.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.features.todo.presentation.TodoNavigator;
import com.android.basics.features.todo.presentation.components.TodoSession;

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
        activity.presenter = new EditTodoPresenter(provideEditTodoInteractor(), provideDeleteTodoInteractor(), provideNavigator(activity), TodoSession.getInstance(), UseCaseHandler.getInstance());
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

package com.android.basics.presentation.home;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationScope;
import com.android.basics.di.TodoScope;
import com.android.basics.di.UserScope;
import com.android.basics.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.presentation.TodoNavigator;
import com.android.basics.presentation.components.TodoSession;
import com.android.basics.presentation.components.UserSession;
import com.android.basics.presentation.home.components.TodoListAdapter;

import java.util.ArrayList;

public class HomeScreenInjector {

    private ApplicationScope applicationScope;

    private static HomeScreenInjector instance = null;

    private HomeScreenInjector() {
    }

    public static HomeScreenInjector getInstance() {
        if (instance == null) {
            instance = new HomeScreenInjector();
        }
        return instance;
    }

    public void inject(HomeActivity activity) {
        applicationScope = ((TodoApplication) activity.getApplication()).getApplicationScope();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(HomeActivity activity) {
        activity.todoListAdapter = provideTodoListAdapter(activity);
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
        activity.progressDialog.setMessage("Logging in");
    }

    private void injectObject(HomeActivity activity) {
        activity.user = UserSession.getInstance().getUser();
        activity.presenter = new HomeScreenPresenter(provideGetTodoList(), UserSession.getInstance(), UserScope.getInstance(), provideNavigator(activity));
    }

    private HomeScreenContract.Navigator provideNavigator(HomeActivity activity) {
        return new TodoNavigator(applicationScope.navigator(activity));
    }

    private TodoListAdapter provideTodoListAdapter(HomeActivity activity) {
        return new TodoListAdapter(new ArrayList<>(), provideNavigator(activity), TodoSession.getInstance(), TodoScope.getInstance());
    }

    private GetTodoListInteractor provideGetTodoList() {
        return new GetTodoListInteractor(applicationScope.todoRepository());
    }


    public void destroy() {
        instance = null;
    }
}

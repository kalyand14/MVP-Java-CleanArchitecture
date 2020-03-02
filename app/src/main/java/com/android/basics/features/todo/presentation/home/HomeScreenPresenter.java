package com.android.basics.features.todo.presentation.home;

import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.di.UserScope;
import com.android.basics.features.todo.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.features.todo.presentation.components.UserSession;

public class HomeScreenPresenter implements HomeScreenContract.Presenter {

    private GetTodoListInteractor getTodoListInteractor;

    private HomeScreenContract.View view;

    private HomeScreenContract.Navigator navigator;

    private UserSession session;

    private UserScope userScope;

    private final UseCaseHandler useCaseHandler;

    public HomeScreenPresenter(GetTodoListInteractor getTodoListInteractor,
                               UserSession session,
                               UserScope userScope,
                               HomeScreenContract.Navigator navigator,
                               UseCaseHandler useCaseHandler) {
        this.getTodoListInteractor = getTodoListInteractor;
        this.navigator = navigator;
        this.session = session;
        this.userScope = userScope;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void onLoadTodoList(String userId) {

        view.setWelcomeMessage("Welcome " + session.getUser().getUserName());

        view.showProgressDialog();

        useCaseHandler.execute(getTodoListInteractor, new GetTodoListInteractor.Request(userId), new UseCase.UseCaseCallback<GetTodoListInteractor.Response>() {
            @Override
            public void onSuccess(GetTodoListInteractor.Response response) {
                DoIfNotNull.let(view, view -> {
                    if (response.isValid()) {
                        session.setTodoList(response.getTodoList());
                        view.showList(true);
                        view.showErrorLayout(false);
                        view.loadTodoList(response.getTodoList());
                        view.dismissProgressDialog();
                    } else {
                        showError();
                    }
                });
            }

            @Override
            public void onError(Error error) {
                DoIfNotNull.let(view, view -> {
                    showError();
                });
            }
        });

    }

    private void showError() {
        view.showList(false);
        view.showErrorLayout(true);
        view.dismissProgressDialog();
    }

    @Override
    public void onLogout() {
        view.showLogoutConfirmationDialog();
    }

    @Override
    public void logout() {

        userScope.end();

        navigator.goToLoginScreen();
    }

    @Override
    public void onAddTodo() {
        navigator.gotoAddTodoScreen();
    }

    @Override
    public void attach(HomeScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        getTodoListInteractor.dispose();
        view = null;
    }
}

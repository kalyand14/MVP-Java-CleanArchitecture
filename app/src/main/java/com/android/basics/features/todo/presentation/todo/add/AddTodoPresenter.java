package com.android.basics.features.todo.presentation.todo.add;

import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.domain.interactor.todo.AddTodoInteractor;
import com.android.basics.features.todo.presentation.components.UserSession;

public class AddTodoPresenter implements AddTodoContract.Presenter {

    private AddTodoContract.View view;

    private AddTodoContract.Navigator navigator;

    private AddTodoInteractor addTodoInteractor;

    private UserSession session;

    private final UseCaseHandler useCaseHandler;

    public AddTodoPresenter(AddTodoContract.Navigator navigator, AddTodoInteractor addTodoInteractor, UserSession session, UseCaseHandler useCaseHandler) {
        this.navigator = navigator;
        this.addTodoInteractor = addTodoInteractor;
        this.session = session;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void onSubmit(String name, String desc, String date) {
        view.showProgressDialog();

        useCaseHandler.execute(
                addTodoInteractor,
                new AddTodoInteractor.Request(
                        session.getUser().getUserId(),
                        name,
                        desc,
                        date
                ),
                new UseCase.UseCaseCallback<AddTodoInteractor.Response>() {
                    @Override
                    public void onSuccess(AddTodoInteractor.Response response) {
                        DoIfNotNull.let(view, view -> {
                            view.dismissProgressDialog();
                            if (response.isSuccess()) {
                                view.showSuccessDialog();
                            } else {
                                view.showErrorDialog();
                            }
                        });
                    }

                    @Override
                    public void onError(Error error) {
                        DoIfNotNull.let(view, view -> {
                            view.dismissProgressDialog();
                            view.showErrorDialog();
                        });
                    }
                });


    }

    @Override
    public void navigateToViewTodoList() {
        navigator.goToHomeScreen();
    }

    @Override
    public void OnCancel() {
        navigator.goToHomeScreen();
    }

    @Override
    public void onSelectDate() {
        view.showDatePickerDialog();
    }

    @Override
    public void attach(AddTodoContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        addTodoInteractor.dispose();
        view = null;
    }
}

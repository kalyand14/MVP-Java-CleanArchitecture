package com.android.basics.features.todo.presentation.todo.edit;

import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.core.utils.DoIfNotNull;
import com.android.basics.features.todo.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.features.todo.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.presentation.components.TodoSession;

public class EditTodoPresenter implements EditTodoContract.Presenter {

    private EditTodoInteractor editTodoInteractor;
    private DeleteTodoInteractor deleteTodoInteractor;
    private EditTodoContract.Navigator navigator;
    private EditTodoContract.View view;
    private TodoSession session;
    private final UseCaseHandler useCaseHandler;

    public EditTodoPresenter(
            EditTodoInteractor editTodoInteractor,
            DeleteTodoInteractor deleteTodoInteractor,
            EditTodoContract.Navigator navigator,
            TodoSession session,
            UseCaseHandler useCaseHandler) {
        this.editTodoInteractor = editTodoInteractor;
        this.deleteTodoInteractor = deleteTodoInteractor;
        this.navigator = navigator;
        this.session = session;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void loadTodo() {
        Todo todo = session.getTodo();
        view.setName(todo.getName());
        view.setDescription(todo.getDescription());
        view.setDate(todo.getDueDate());
    }

    @Override
    public void onSubmit(String name, String desc, String date) {

        if (!name.isEmpty() && !desc.isEmpty() && !date.isEmpty()) {
            view.showProgressDialog("Updating todo");

            useCaseHandler.execute(
                    editTodoInteractor,
                    new EditTodoInteractor.Request(session.getTodo().getTodoId(), session.getTodo().getUserId(), name, desc, date),
                    new UseCase.UseCaseCallback<EditTodoInteractor.Response>() {
                        @Override
                        public void onSuccess(EditTodoInteractor.Response response) {
                            DoIfNotNull.let(view, view -> {
                                view.dismissProgressDialog();
                                if (response.isSuccess()) {
                                    view.showSuccessDialog("Record successfully updated.");
                                } else {
                                    view.showErrorDialog("There was a problem. could not able to update the record.");
                                }
                            });
                        }

                        @Override
                        public void onError(Error error) {
                            DoIfNotNull.let(view, view -> {
                                view.dismissProgressDialog();
                                view.showErrorDialog("There was a problem. could not able to update the record.");
                            });
                        }
                    });
        }
        else{
            view.showValidationErrorDialog();
        }
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
    public void onDelete() {
        view.showProgressDialog("Deleting todo");

        useCaseHandler.execute(deleteTodoInteractor, new DeleteTodoInteractor.Request(session.getTodo().getTodoId()), new UseCase.UseCaseCallback<DeleteTodoInteractor.Response>() {
            @Override
            public void onSuccess(DeleteTodoInteractor.Response response) {
                DoIfNotNull.let(view, view -> {
                    view.dismissProgressDialog();
                    if (response.isSuccess()) {
                        view.showSuccessDialog("Record successfully deleted.");
                    } else {
                        view.showErrorDialog("Error deleting todo");
                    }
                });
            }

            @Override
            public void onError(Error error) {
                DoIfNotNull.let(view, view -> {
                    view.dismissProgressDialog();
                    view.showErrorDialog("Error deleting todo");
                });
            }
        });
    }

    @Override
    public void onSelectDate() {
        view.showDatePickerDialog();
    }

    @Override
    public void attach(EditTodoContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        editTodoInteractor.dispose();
        deleteTodoInteractor.dispose();
        this.view = null;
    }
}

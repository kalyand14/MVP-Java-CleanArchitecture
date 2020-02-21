package com.android.basics.presentation.todo.edit;

import com.android.basics.core.Callback;
import com.android.basics.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.domain.model.Todo;
import com.android.basics.presentation.components.TodoSession;

public class EditTodoPresenter implements EditTodoContract.Presenter {

    private EditTodoInteractor editTodoInteractor;
    private DeleteTodoInteractor deleteTodoInteractor;
    private EditTodoContract.Navigator navigator;
    private EditTodoContract.View view;
    private TodoSession session;

    public EditTodoPresenter(
            EditTodoInteractor editTodoInteractor,
            DeleteTodoInteractor deleteTodoInteractor,
            EditTodoContract.Navigator navigator,
            TodoSession session) {
        this.editTodoInteractor = editTodoInteractor;
        this.deleteTodoInteractor = deleteTodoInteractor;
        this.navigator = navigator;
        this.session = session;
    }

    @Override
    public void loadTodo() {
        Todo todo = TodoSession.getInstance().getTodo();
        view.setName(todo.getName());
        view.setDescription(todo.getDescription());
        view.setDate(todo.getDueDate());
    }

    @Override
    public void onSubmit(String name, String desc, String date) {
        view.showProgressDialog("Updating todo");
        this.editTodoInteractor.execute(EditTodoInteractor.Params.forTodo(session.getTodo().getTodoId(), name, desc, date), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                view.dismissProgressDialog();
                if (response) {
                    view.showSuccessDialog("Record successfully updated.");
                } else {
                    view.showErrorDialog("There was a problem. could not able to update the record.");
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                view.dismissProgressDialog();
                view.showErrorDialog("There was a problem. could not able to delete the record.");
            }
        });
    }

    @Override
    public void navigate() {
        navigator.goToHomeScreen();
    }

    @Override
    public void OnCancel() {
        navigator.goToHomeScreen();
    }

    @Override
    public void onDelete() {
        view.showProgressDialog("Deleting todo");
        this.deleteTodoInteractor.execute(session.getTodo().getTodoId(), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                view.dismissProgressDialog();
                if (response) {
                    view.showSuccessDialog("Record successfully deleted.");
                } else {
                    view.showErrorDialog("Error deleting todo");
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                view.dismissProgressDialog();
                view.showErrorDialog("Error deleting todo");
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
        this.view = null;
    }
}

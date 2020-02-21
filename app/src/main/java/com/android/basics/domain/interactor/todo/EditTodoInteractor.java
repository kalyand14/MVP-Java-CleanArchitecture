package com.android.basics.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.repository.TodoRepository;

public class EditTodoInteractor extends UseCase<EditTodoInteractor.Params, Boolean> {

    private TodoRepository todoRepository;

    public EditTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeTask(EditTodoInteractor.Params param, final Callback<Boolean> callback) {
        todoRepository.editTodo(param.todoId, param.name, param.description, param.date, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (!isDisposed()) {
                    callback.onResponse(response);
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                if (!isDisposed()) {
                    callback.onError(errorcode, errorResponse);
                }
            }
        });
    }

    public static final class Params {
        private int todoId;
        private String name;
        private String description;
        private String date;

        private Params(int todoId, String name, String description, String date) {
            this.todoId = todoId;
            this.name = name;
            this.description = description;
            this.date = date;
        }

        public static EditTodoInteractor.Params forTodo(int todoId, String name, String description, String date) {
            return new EditTodoInteractor.Params(todoId, name, description, date);
        }
    }
}

package com.android.basics.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.repository.TodoRepository;

public class AddTodoInteractor extends UseCase<AddTodoInteractor.Params, Boolean> {

    private TodoRepository todoRepository;

    public AddTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeTask(AddTodoInteractor.Params param, final Callback<Boolean> callback) {
        todoRepository.addTodo(param.userId, param.name, param.description, param.date, new Callback<Boolean>() {
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
        private int userId;
        private String name;
        private String description;
        private String date;

        private Params(int userId, String name, String description, String date) {
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.date = date;
        }

        public static AddTodoInteractor.Params forTodo(int userId, String name, String description, String date) {
            return new AddTodoInteractor.Params(userId, name, description, date);
        }
    }
}

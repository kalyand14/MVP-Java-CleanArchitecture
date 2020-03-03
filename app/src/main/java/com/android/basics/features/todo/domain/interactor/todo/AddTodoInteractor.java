package com.android.basics.features.todo.domain.interactor.todo;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

public class AddTodoInteractor extends UseCase<AddTodoInteractor.Request, AddTodoInteractor.Response> {

    private TodoRepository todoRepository;

    public AddTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        this.todoRepository.addTodo(params.getTodo(), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (isNotDisposed()) {
                    getUseCaseCallback().onSuccess(new Response(response));
                }
            }

            @Override
            public void onError(Error todoError) {
                if (isNotDisposed()) {
                    getUseCaseCallback().onError(todoError);
                }
            }
        });
    }

    public static class Request implements UseCase.Request {
        private String userId;
        private String name;
        private String description;
        private String date;

        public Request(String userId, String name, String description, String date) {
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.date = date;
        }

        public Todo getTodo() {
            return new Todo(userId, name, description, date);
        }
    }

    public static class Response implements UseCase.Response {

        private final boolean isSuccess;

        public Response(@NonNull boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}

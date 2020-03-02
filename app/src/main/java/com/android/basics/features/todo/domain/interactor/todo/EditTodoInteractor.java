package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

public class EditTodoInteractor extends UseCase<EditTodoInteractor.Request, EditTodoInteractor.Response> {

    private TodoRepository todoRepository;

    public EditTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        todoRepository.editTodo(params.getTodo(), new Callback<Boolean>() {
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
        private String todoId;
        private String userId;
        private String name;
        private String description;
        private String date;

        public Request(String todoId, String userId, String name, String description, String date) {
            this.todoId = todoId;
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.date = date;
        }

        public Todo getTodo() {
            return new Todo(todoId, userId, name, description, date, false);
        }
    }


    public static final class Response implements UseCase.Response {

        private final boolean isSuccess;

        public Response(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}

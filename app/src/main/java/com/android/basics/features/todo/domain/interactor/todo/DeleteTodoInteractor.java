package com.android.basics.features.todo.domain.interactor.todo;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.repository.TodoRepository;

public class DeleteTodoInteractor extends UseCase<DeleteTodoInteractor.Request, DeleteTodoInteractor.Response> {

    private TodoRepository todoRepository;

    public DeleteTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        todoRepository.deleteTodo(params.getTodoId(), new Callback<Boolean>() {
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

    public static final class Request implements UseCase.Request {

        private final String todoId;

        public Request(@NonNull String todoId) {
            this.todoId = todoId;
        }

        public String getTodoId() {
            return todoId;
        }
    }

    public static final class Response implements UseCase.Response {

        private final boolean isSuccess;

        public Response(@NonNull boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }

}

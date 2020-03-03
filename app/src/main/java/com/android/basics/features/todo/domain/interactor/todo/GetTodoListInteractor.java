package com.android.basics.features.todo.domain.interactor.todo;

import androidx.annotation.VisibleForTesting;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import java.util.List;

public class GetTodoListInteractor extends UseCase<GetTodoListInteractor.Request, GetTodoListInteractor.Response> {

    private TodoRepository todoRepository;

    public GetTodoListInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeUseCase(Request request) {
        todoRepository.getTodoList(request.getUserId(), new Callback<List<Todo>>() {
            @Override
            public void onResponse(List<Todo> response) {
                if (isNotDisposed()) {
                    getUseCaseCallback().onSuccess(createResponse(response));
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

    @VisibleForTesting
    public Response createResponse(List<Todo> payLoad) {
        return new Response(payLoad);
    }

    public static class Request implements UseCase.Request {

        private final String userId;

        public Request(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }
    }

    public static class Response implements UseCase.Response {

        private final List<Todo> todoList;

        public Response(List<Todo> todoList) {
            this.todoList = todoList;
        }

        public List<Todo> getTodoList() {
            return todoList;
        }

        public boolean isValid() {
            return todoList != null && todoList.size() > 1;
        }
    }
}

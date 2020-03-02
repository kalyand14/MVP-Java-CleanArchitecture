package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

public class GetTodoInteractor extends UseCase<GetTodoInteractor.Request, GetTodoInteractor.Response> {

    private TodoRepository todoRepository;

    public GetTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        todoRepository.getTodo(params.getTodoId(), new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
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

        public Request(String todoId) {
            this.todoId = todoId;
        }

        public String getTodoId() {
            return todoId;
        }
    }

    public static final class Response implements UseCase.Response {

        private final Todo todo;

        public Response(Todo todo) {
            this.todo = todo;
        }

        public Todo getTodo() {
            return todo;
        }
    }

}

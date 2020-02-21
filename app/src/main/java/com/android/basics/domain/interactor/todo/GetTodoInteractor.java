package com.android.basics.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

public class GetTodoInteractor extends UseCase<Integer, Todo> {

    private TodoRepository todoRepository;

    public GetTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeTask(Integer todoId, final Callback<Todo> callback) {
        todoRepository.getTodo(todoId, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
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


}

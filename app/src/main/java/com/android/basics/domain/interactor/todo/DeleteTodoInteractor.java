package com.android.basics.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.repository.TodoRepository;

public class DeleteTodoInteractor extends UseCase<Integer, Boolean> {

    private TodoRepository todoRepository;

    public DeleteTodoInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeTask(Integer todoId, final Callback<Boolean> callback) {
        todoRepository.deleteTodo(todoId, new Callback<Boolean>() {
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


}

package com.android.basics.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import java.util.List;

public class GetTodoListInteractor extends UseCase<GetTodoListInteractor.Params, List<Todo>> {

    private TodoRepository todoRepository;

    public GetTodoListInteractor(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    protected void executeTask(Params params, final Callback<List<Todo>> callback) {
        todoRepository.getTodoList(params.userId, new Callback<List<Todo>>() {
            @Override
            public void onResponse(List<Todo> response) {
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

        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}

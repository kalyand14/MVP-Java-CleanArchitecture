package com.android.basics.features.todo.data.repository;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.features.todo.data.source.TodoDataSource;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import java.util.List;

public class TodoDataRepository implements TodoRepository {

    private static TodoDataRepository INSTANCE = null;

    private final TodoDataSource todoLocalDataSource;
    private final TodoDataSource todoRemoteDataSource;

    private TodoDataRepository(TodoDataSource todoLocalDataSource, TodoDataSource todoRemoteDataSource) {
        this.todoLocalDataSource = todoLocalDataSource;
        this.todoRemoteDataSource = todoRemoteDataSource;
    }

    public static TodoDataRepository getInstance(TodoDataSource todoLocalDataSource, TodoDataSource todoRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TodoDataRepository(todoLocalDataSource, todoRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getTodoList(String userId, Callback<List<Todo>> callback) {
        todoLocalDataSource.getTodoList(userId, new Callback<List<Todo>>() {
            @Override
            public void onResponse(List<Todo> response) {
                callback.onResponse(response);
            }

            @Override
            public void onError(Error todoError) {
                todoRemoteDataSource.getTodoList(userId, new Callback<List<Todo>>() {
                    @Override
                    public void onResponse(List<Todo> response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }
        });
    }

    @Override
    public void getTodo(String todoId, Callback<Todo> callback) {
        todoLocalDataSource.getTodo(todoId, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                callback.onResponse(response);
            }

            @Override
            public void onError(Error todoError) {
                todoRemoteDataSource.getTodo(todoId, new Callback<Todo>() {
                    @Override
                    public void onResponse(Todo response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });

            }
        });
    }

    @Override
    public void editTodo(Todo todo, Callback<Boolean> callback) {

        todoRemoteDataSource.editTodo(todo, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                todoLocalDataSource.editTodo(todo, new Callback<Boolean>() {
                    @Override
                    public void onResponse(Boolean response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }

            @Override
            public void onError(Error todoError) {
                callback.onError(todoError);
            }
        });

    }

    @Override
    public void addTodo(Todo todo, Callback<Boolean> callback) {
        todoRemoteDataSource.addTodo(todo, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                todoLocalDataSource.addTodo(response, new Callback<Todo>() {
                    @Override
                    public void onResponse(Todo response) {
                        callback.onResponse(response != null);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }

            @Override
            public void onError(Error todoError) {
                callback.onError(todoError);
            }
        });
    }

    @Override
    public void deleteTodo(String todoId, Callback<Boolean> callback) {
        todoRemoteDataSource.deleteTodo(todoId, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                todoLocalDataSource.deleteTodo(todoId, new Callback<Boolean>() {
                    @Override
                    public void onResponse(Boolean response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }

            @Override
            public void onError(Error todoError) {
                callback.onError(todoError);
            }
        });
    }

    @Override
    public void deleteAllTodo() {
        todoRemoteDataSource.deleteAllTodo();
        todoLocalDataSource.deleteAllTodo();
    }
}

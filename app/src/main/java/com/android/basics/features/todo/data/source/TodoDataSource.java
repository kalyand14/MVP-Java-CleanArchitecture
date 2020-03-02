package com.android.basics.features.todo.data.source;

import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.model.Todo;

import java.util.List;

public interface TodoDataSource {

    void getTodoList(String userId, Callback<List<Todo>> callback);

    void getTodo(String todoId, Callback<Todo> callback);

    void editTodo(Todo todo, Callback<Boolean> callback);

    void addTodo(Todo todo, Callback<Todo> callback);

    void deleteTodo(String todoId, Callback<Boolean> callback);
}

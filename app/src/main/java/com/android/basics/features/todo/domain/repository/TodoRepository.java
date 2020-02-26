package com.android.basics.features.todo.domain.repository;

import com.android.basics.core.domain.Callback;
import com.android.basics.features.todo.domain.model.Todo;

import java.util.List;

public interface TodoRepository {
    void getTodoList(int userId, Callback<List<Todo>> callback);

    void getTodo(int todoId, Callback<Todo> callback);

    void editTodo(int todoId, String name, String desctiption, String date, Callback<Boolean> callback);

    void addTodo(int userId, String name, String desctiption, String date, Callback<Boolean> callback);

    void deleteTodo(int todoId, Callback<Boolean> callback);
}

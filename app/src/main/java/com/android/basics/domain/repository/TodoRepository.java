package com.android.basics.domain.repository;

import com.android.basics.core.Callback;
import com.android.basics.domain.model.Todo;

import java.util.List;

public interface TodoRepository {
    void getTodoList(int userId, Callback<List<Todo>> callback);

    void getTodo(int todoId, Callback<Todo> callback);

    void editTodo(int todoId, String name, String desctiption, String date, Callback<Boolean> callback);

    void addTodo(int userId, String name, String desctiption, String date, Callback<Boolean> callback);

    void deleteTodo(int todoId, Callback<Boolean> callback);
}

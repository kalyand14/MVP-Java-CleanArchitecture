package com.android.basics.features.todo.data.source.remote;

import android.os.Handler;
import android.os.Looper;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.RepoUtils;
import com.android.basics.features.todo.data.source.TodoDataSource;
import com.android.basics.features.todo.domain.model.Todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TodoRemoteDataSource implements TodoDataSource {

    private final Error noDataAvailableError = new Error(new Exception("No data available"));

    private final int SERVICE_LATENCY_IN_MILLIS = 5000;
    private final Map<String, Todo> TODO_SERVICE_DATA;

    public TodoRemoteDataSource() {
        TODO_SERVICE_DATA = new LinkedHashMap<>(2);
    }

    @Override
    public void getTodoList(String userId, Callback<List<Todo>> callback) {

        final List<Todo> todoList = new ArrayList<>(TODO_SERVICE_DATA.values());
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (RepoUtils.isNotNullNotEmpty(todoList)) {
                callback.onResponse(todoList);
            } else {
                callback.onError(noDataAvailableError);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getTodo(String todoId, Callback<Todo> callback) {
        final Todo todo = TODO_SERVICE_DATA.get(todoId);
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (todo != null) {
                callback.onResponse(todo);
            } else {
                callback.onError(noDataAvailableError);
            }
        }, SERVICE_LATENCY_IN_MILLIS);

    }

    @Override
    public void editTodo(Todo todo, Callback<Boolean> callback) {
        Todo editedTodo = new Todo(todo.getTodoId(), todo.getUserId(), todo.getName(), todo.getDescription(), todo.getDueDate(), todo.isCompleted());
        TODO_SERVICE_DATA.put(todo.getTodoId(), editedTodo);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> callback.onResponse(true),
                SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void addTodo(Todo todo, Callback<Todo> callback) {
        Todo addedTodo = new Todo(UUID.randomUUID().toString(), todo.getUserId(), todo.getName(), todo.getDescription(), todo.getDueDate(), todo.isCompleted());
        TODO_SERVICE_DATA.put(addedTodo.getTodoId(), addedTodo);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> callback.onResponse(addedTodo),
                SERVICE_LATENCY_IN_MILLIS);

        //callback.onResponse(addedTodo);
    }

    @Override
    public void deleteTodo(String todoId, Callback<Boolean> callback) {
        Iterator<Map.Entry<String, Todo>> it = TODO_SERVICE_DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Todo> entry = it.next();
            if (todoId.equalsIgnoreCase(entry.getValue().getTodoId())) {
                it.remove();
            }
        }
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> callback.onResponse(true),
                SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void deleteAllTodo() {
        TODO_SERVICE_DATA.clear();
    }
}

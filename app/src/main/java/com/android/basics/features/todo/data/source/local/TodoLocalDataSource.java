package com.android.basics.features.todo.data.source.local;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.AppExecutors;
import com.android.basics.features.todo.data.source.TodoDataSource;
import com.android.basics.features.todo.data.source.local.dao.TodoDao;
import com.android.basics.features.todo.data.source.local.entity.TodoTbl;
import com.android.basics.features.todo.data.source.local.mapper.TodoListMapper;
import com.android.basics.features.todo.data.source.local.mapper.TodoMapper;
import com.android.basics.features.todo.domain.model.Todo;

import java.util.List;

public class TodoLocalDataSource implements TodoDataSource {

    private final TodoDao todoDao;
    private final AppExecutors appExecutors;
    private TodoListMapper todoListMapper;
    private TodoMapper todoMapper;

    private final Error noDataAvailableError = new Error(new Exception("No data available"));
    private final Error operationFailed = new Error(new Exception("Operation failed"));

    public TodoLocalDataSource(
            @NonNull TodoDao todoDao,
            @NonNull AppExecutors appExecutors,
            @NonNull TodoListMapper todoListMapper,
            @NonNull TodoMapper todoMapper
    ) {
        this.todoDao = todoDao;
        this.appExecutors = appExecutors;
        this.todoListMapper = todoListMapper;
        this.todoMapper = todoMapper;
    }

    @Override
    public void getTodoList(String userId, Callback<List<Todo>> callback) {
        appExecutors.diskIO().execute(() -> {
            final List<Todo> todoList = todoListMapper.convert(todoDao.getAllTodo(userId));
            appExecutors.mainThread().execute(() -> {
                if (todoList.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onError(noDataAvailableError);
                } else {
                    callback.onResponse(todoList);
                }
            });
        });
    }

    @Override
    public void getTodo(String todoId, Callback<Todo> callback) {
        appExecutors.diskIO().execute(() -> {
            Todo todo = todoMapper.convert(todoDao.getTodo(todoId));
            appExecutors.mainThread().execute(() -> {
                if (todo == null) {
                    callback.onError(noDataAvailableError);
                } else {
                    callback.onResponse(todo);
                }
            });
        });
    }

    @Override
    public void editTodo(Todo in, Callback<Boolean> callback) {
        appExecutors.diskIO().execute(() -> {
            TodoTbl tbl = todoDao.getTodo(in.getTodoId());
            tbl.setTodoId(in.getTodoId());
            tbl.setDescription(in.getDescription());
            tbl.setName(in.getName());
            tbl.setDueDate(in.getDueDate());
            final int result = todoDao.update(tbl);
            appExecutors.mainThread().execute(() -> {
                if (result == 1) {
                    callback.onResponse(true);
                } else {
                    callback.onError(operationFailed);
                }
            });

        });
    }

    @Override
    public void addTodo(Todo in, Callback<Todo> callback) {
        appExecutors.diskIO().execute(() -> {
            final long result = todoDao.insert(in.getTodoId(), in.getUserId(), in.getName(), in.getDescription(), in.getDueDate(), false);
            appExecutors.mainThread().execute(() -> {
                if (result != -1) {
                    callback.onResponse(in);
                } else {
                    callback.onError(operationFailed);
                }

            });
        });
    }

    @Override
    public void deleteTodo(String todoId, Callback<Boolean> callback) {
        appExecutors.diskIO().execute(() -> {
            final int result = todoDao.delete(todoId);
            appExecutors.mainThread().execute(() -> {
                if (result == 1) {
                    callback.onResponse(true);
                } else {
                    callback.onError(operationFailed);
                }
            });
        });
    }

    @Override
    public void deleteAllTodo() {
        appExecutors.diskIO().execute(todoDao::deleteAllTodo);
    }
}

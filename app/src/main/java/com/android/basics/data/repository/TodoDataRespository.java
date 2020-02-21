package com.android.basics.data.repository;

import com.android.basics.core.Callback;
import com.android.basics.data.component.DaoCallback;
import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.TodoListMapper;
import com.android.basics.data.mapper.TodoMapper;
import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import java.util.List;

public class TodoDataRespository implements TodoRepository {

    private TodoDao todoDao;
    private DaoExecutor daoExecutor;
    private TodoListMapper todoListMapper;
    private TodoMapper todoMapper;

    public TodoDataRespository(DaoExecutor daoExecutor, TodoDao todoDao, TodoListMapper todoListMapper, TodoMapper todoMapper) {
        this.todoDao = todoDao;
        this.daoExecutor = daoExecutor;
        this.todoListMapper = todoListMapper;
        this.todoMapper = todoMapper;
    }

    @Override
    public void getTodoList(int userId, Callback<List<Todo>> callback) {

        DaoCallback<List<Todo>> daoCallback = new DaoCallback<List<Todo>>() {
            @Override
            public List<Todo> doAsync() {
                return todoListMapper.convert(todoDao.getAllTodo(userId));
            }

            @Override
            public void onComplete(List<Todo> response) {
                if (response != null) {
                    callback.onResponse(response);
                } else {
                    callback.onError("00002", "No data available");
                }
            }
        };
        daoExecutor.start(daoCallback);
    }

    @Override
    public void getTodo(int todoId, Callback<Todo> callback) {
        DaoCallback<Todo> daoCallback = new DaoCallback<Todo>() {
            @Override
            public Todo doAsync() {
                return todoMapper.convert(todoDao.getTodo(todoId));
            }

            @Override
            public void onComplete(Todo response) {
                if (response != null) {
                    callback.onResponse(response);
                } else {
                    callback.onError("00002", "No data available");
                }
            }
        };
        daoExecutor.start(daoCallback);

    }

    @Override
    public void editTodo(int todoId, String name, String description, String date, Callback<Boolean> callback) {
        DaoCallback<Integer> daoCallback = new DaoCallback<Integer>() {
            @Override
            public Integer doAsync() {
                TodoTbl tbl = todoDao.getTodo(todoId);
                tbl.setTodoId(todoId);
                tbl.setDescription(description);
                tbl.setName(name);
                tbl.setDueDate(date);
                return todoDao.update(tbl);
            }

            @Override
            public void onComplete(Integer response) {
                if (response == 1) {
                    callback.onResponse(true);
                } else {
                    callback.onError("00002", "Update failed");
                }
            }
        };
        daoExecutor.start(daoCallback);
    }

    @Override
    public void addTodo(int userId, String name, String description, String date, Callback<Boolean> callback) {
        DaoCallback<Long> daoCallback = new DaoCallback<Long>() {
            @Override
            public Long doAsync() {
                return todoDao.insert(userId, name, description, date, false);
            }

            @Override
            public void onComplete(Long response) {
                if (response != -1) {
                    callback.onResponse(true);
                } else {
                    callback.onError("00002", "insert failed");
                }
            }
        };
        daoExecutor.start(daoCallback);

    }

    @Override
    public void deleteTodo(int todoId, Callback<Boolean> callback) {
        DaoCallback<Integer> daoCallback = new DaoCallback<Integer>() {
            @Override
            public Integer doAsync() {
                return todoDao.delete(todoId);
            }

            @Override
            public void onComplete(Integer response) {
                if (response == 1) {
                    callback.onResponse(true);
                } else {
                    callback.onError("00002", "Update failed");
                }
            }
        };
        daoExecutor.start(daoCallback);
    }
}

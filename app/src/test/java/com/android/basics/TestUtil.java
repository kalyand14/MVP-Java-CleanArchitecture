package com.android.basics;

import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.data.source.entity.UserTbl;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TestUtil {
    public static final String USER_NAME = "Kalyan";
    public static final String PASSWORD = "password";

    public static final String ERROR_CODE = "00002";
    public static final String ERROR_MESSAGE = "No data available";
    public static final String EDIT_ERROR_MESSAGE = "Update failed";
    public static final String INSERT_ERROR_MESSAGE = "insert failed";
    public static final String DELETE_ERROR_MESSAGE = "Delete failed";


    public static final int USER_ID = 1;
    public static final int TODO_ID = 1;
    public static final long TODO_ID_LONG = 1;
    public static final long TODO_ID_LONG_NULL = -1;
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    public static List<Todo> buildMockTodList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(buildMockTodo(1));
        todoList.add(buildMockTodo(2));
        return todoList;
    }

    public static Todo buildMockTodo(int todoId) {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.getTodoId()).thenReturn(todoId);
        return todo;
    }

    public static List<TodoTbl> buildMockTodoTblList() {
        List<TodoTbl> todoTblList = new ArrayList<>();
        todoTblList.add(buildMockTodoTbl(1));
        todoTblList.add(buildMockTodoTbl(2));
        return todoTblList;
    }

    public static TodoTbl buildMockTodoTbl(int todoId) {
        TodoTbl todoTbl = Mockito.mock(TodoTbl.class);
        when(todoTbl.getTodoId()).thenReturn(todoId);
        return todoTbl;
    }

    public static User buildMockUser(int userId, String name, String password) {
        User user = Mockito.mock(User.class);
        when(user.getUserId()).thenReturn(userId);
        when(user.getUserName()).thenReturn(name);
        when(user.getPassword()).thenReturn(password);
        return user;
    }

    public static UserTbl buildMockUserTbl(int userId, String name, String password) {
        UserTbl userTbl = Mockito.mock(UserTbl.class);
        when(userTbl.getUserId()).thenReturn(userId);
        when(userTbl.getUserName()).thenReturn(name);
        when(userTbl.getPassword()).thenReturn(password);
        return userTbl;
    }
}

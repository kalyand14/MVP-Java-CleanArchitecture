package com.android.basics;

import com.android.basics.domain.model.Todo;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TestConstants {
    public static final String USER_NAME = "Kalyan";
    public static final String PASSWORD = "password";

    public static final String ERROR_CODE = "errorcode";
    public static final String ERROR_MESSAGE = "errormessage";


    public static final int USER_ID = 1;
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    public static final int TODO_ID = 1;

    public static List<Todo> buildMockTodList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(buildMockTodo(1));
        todoList.add(buildMockTodo(2));
        return todoList;
    }

    private static Todo buildMockTodo(int todoId) {
        Todo todo = Mockito.mock(Todo.class);
        when(todo.getTodoId()).thenReturn(todoId);
        return todo;
    }
}

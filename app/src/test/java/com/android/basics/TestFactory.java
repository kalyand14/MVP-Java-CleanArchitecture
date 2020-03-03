package com.android.basics;

import com.android.basics.features.todo.data.source.local.entity.TodoTbl;
import com.android.basics.features.todo.data.source.local.entity.UserTbl;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.model.User;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TestFactory {
    public static List<Todo> buildMockTodList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(buildMockTodo("1"));
        todoList.add(buildMockTodo("2"));
        return todoList;
    }

    public static Todo buildMockTodo(String todoId) {
        Todo todo = Mockito.mock(Todo.class);
        Mockito.when(todo.getTodoId()).thenReturn(todoId);
        return todo;
    }

    public static List<TodoTbl> buildMockTodoTblList() {
        List<TodoTbl> todoTblList = new ArrayList<>();
        todoTblList.add(buildMockTodoTbl("1"));
        todoTblList.add(buildMockTodoTbl("2"));
        return todoTblList;
    }

    public static TodoTbl buildMockTodoTbl(String todoId) {
        TodoTbl todoTbl = Mockito.mock(TodoTbl.class);
        Mockito.when(todoTbl.getTodoId()).thenReturn(todoId);
        return todoTbl;
    }

    public static User buildMockUser(String userId, String name, String password) {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getUserId()).thenReturn(userId);
        Mockito.when(user.getUserName()).thenReturn(name);
        Mockito.when(user.getPassword()).thenReturn(password);
        return user;
    }

    public static UserTbl buildMockUserTbl(String userId, String name, String password) {
        UserTbl userTbl = Mockito.mock(UserTbl.class);
        Mockito.when(userTbl.getUserId()).thenReturn(userId);
        Mockito.when(userTbl.getUserName()).thenReturn(name);
        Mockito.when(userTbl.getPassword()).thenReturn(password);
        return userTbl;
    }
}

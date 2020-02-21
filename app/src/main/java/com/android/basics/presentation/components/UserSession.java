package com.android.basics.presentation.components;

import com.android.basics.core.di.ScopeObserver;
import com.android.basics.di.UserScope;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;

import java.util.List;

public class UserSession implements ScopeObserver {

    private User user;

    private List<Todo> todoList;

    public static UserSession getInstance() {
        if (!UserScope.getInstance().getContainer().has(UserSession.class)) {
            UserScope.getInstance().getContainer().register(UserSession.class, new UserSession());
        }
        return UserScope.getInstance().getContainer().get(UserSession.class);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public void onScopeEnded() {
        user = null;
    }
}

package com.android.basics.features.todo.presentation.components;

import com.android.basics.core.di.ScopeObserver;
import com.android.basics.features.todo.di.TodoScope;
import com.android.basics.features.todo.domain.model.Todo;

public class TodoSession implements ScopeObserver {

    private Todo todo;

    public static TodoSession getInstance() {
        if (!TodoScope.getInstance().getContainer().has(TodoSession.class)) {
            TodoScope.getInstance().getContainer().register(TodoSession.class, new TodoSession());
        }
        return TodoScope.getInstance().getContainer().get(TodoSession.class);
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public void onScopeEnded() {
        todo = null;
    }
}

package com.android.basics.features.todo.presentation.components;

import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class UserSessionTest {

    @InjectMocks
    private UserSession session;
    @Mock
    private User user;
    @Mock
    private List<Todo> todoList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_user_getter_Setter() {
        session.setUser(user);
        Assert.assertNotNull(session.getUser());
    }

    @Test
    public void test_todoList_getter_Setter() {
        session.setTodoList(todoList);
        Assert.assertNotNull(session.getTodoList());
    }

    @Test
    public void test_getter_Setter_scope_end() {
        session.setUser(user);
        session.setTodoList(todoList);
        session.onScopeEnded();
        Assert.assertNull(session.getUser());
        Assert.assertNull(session.getTodoList());
    }

}
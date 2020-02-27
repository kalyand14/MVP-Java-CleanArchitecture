package com.android.basics.features.todo.presentation.components;

import com.android.basics.features.todo.domain.model.Todo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TodoSessionTest {

    @InjectMocks
    private TodoSession session;

    @Mock
    private Todo todo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getter_Setter() {
        session.setTodo(todo);
        Assert.assertNotNull(session.getTodo());
    }

    @Test
    public void test_getter_Setter_scope_end() {
        session.setTodo(todo);
        session.onScopeEnded();
        Assert.assertNull(session.getTodo());
    }


}
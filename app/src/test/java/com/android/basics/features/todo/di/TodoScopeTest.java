package com.android.basics.features.todo.di;

import com.android.basics.core.di.InstanceContainer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TodoScopeTest {

    @InjectMocks
    private TodoScope todoScope;

    @Mock
    private InstanceContainer container;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        todoScope.setContainer(container);
    }

    @Test
    public void test_end() {
        todoScope.end();
        verify(container).end();
    }

}
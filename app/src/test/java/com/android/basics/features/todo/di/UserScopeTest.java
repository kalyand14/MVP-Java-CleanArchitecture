package com.android.basics.features.todo.di;

import com.android.basics.core.di.InstanceContainer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class UserScopeTest {

    @InjectMocks
    private UserScope userScope;

    @Mock
    private InstanceContainer container;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userScope.setContainer(container);
    }

    @Test
    public void test_end() {
        userScope.end();
        verify(container).end();
    }
}
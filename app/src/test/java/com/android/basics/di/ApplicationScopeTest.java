package com.android.basics.di;

import com.android.basics.core.di.InstanceContainer;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ApplicationScopeTest {

    @InjectMocks
    private ApplicationScope applicationScope;

    @Mock
    private InstanceContainer container;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        applicationScope.setContainer(container);
    }


}
package com.android.basics.features.todo.presentation.splash;

import com.android.basics.di.ApplicationScope;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class SplashInjectorTest {

    @Mock
    private ApplicationScope applicationScope;

    @Mock
    private SplashActivity activity;

    @InjectMocks
    private SplashInjector injector;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_inject() {
        //Arrange

        //Act
        injector.inject(activity);

        //Assert
        verify(activity).presenter = any(SplashPresenter.class);
    }

}
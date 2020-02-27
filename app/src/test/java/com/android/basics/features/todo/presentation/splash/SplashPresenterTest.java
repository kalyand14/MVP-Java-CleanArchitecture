package com.android.basics.features.todo.presentation.splash;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class SplashPresenterTest {

    @Mock
    private SplashContract.Navigator navigator;
    @Mock
    private SplashContract.View view;

    @InjectMocks
    private SplashPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter.attach(view);
    }

    @Test
    public void test_navigate() {
        presenter.navigate();
        verify(navigator).goToLoginScreen();
    }

}
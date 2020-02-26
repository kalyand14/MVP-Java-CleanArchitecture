package com.android.basics.features.todo.presentation;

import android.content.Intent;

import com.android.basics.core.presenetation.navigation.Navigator;
import com.android.basics.features.todo.presentation.home.HomeActivity;
import com.android.basics.features.todo.presentation.login.LoginActivity;
import com.android.basics.features.todo.presentation.registration.RegisterUserActivity;
import com.android.basics.features.todo.presentation.todo.add.AddTodoActivity;
import com.android.basics.features.todo.presentation.todo.edit.EditTodoActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoNavigatorTest {

    @Mock
    private Navigator navigator;

    @Mock
    private Intent intent;

    @InjectMocks
    private TodoNavigator todoNavigator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_goToEditTodoScreen() {
        when(navigator.createIntent(EditTodoActivity.class)).thenReturn(intent);
        todoNavigator.goToEditTodoScreen();
        verify(navigator).launchActivity(intent);
    }

    @Test
    public void test_gotoAddTodoScreen() {
        when(navigator.createIntent(AddTodoActivity.class)).thenReturn(intent);
        todoNavigator.gotoAddTodoScreen();
        verify(navigator).launchActivity(intent);
    }

    @Test
    public void test_goToLoginScreen() {
        when(navigator.createIntent(LoginActivity.class)).thenReturn(intent);
        todoNavigator.goToLoginScreen();
        verify(navigator).launchActivity(intent);
        verify(navigator).finishActivity();
    }


    @Test
    public void test_goToRegisterScreen() {
        when(navigator.createIntent(RegisterUserActivity.class)).thenReturn(intent);
        todoNavigator.goToRegisterScreen();
        verify(navigator).launchActivity(intent);
    }

    @Test
    public void test_goToHomeScreen() {
        when(navigator.createIntent(HomeActivity.class)).thenReturn(intent);
        todoNavigator.goToHomeScreen();
        verify(navigator).launchActivity(intent);
        verify(navigator).finishActivity();
    }
}
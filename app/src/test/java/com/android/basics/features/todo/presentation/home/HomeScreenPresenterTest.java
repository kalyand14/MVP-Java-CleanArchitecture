package com.android.basics.features.todo.presentation.home;

import com.android.basics.features.todo.di.UserScope;
import com.android.basics.features.todo.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class HomeScreenPresenterTest {

    @Mock
    private GetTodoListInteractor getTodoListInteractor;
    @Mock
    private HomeScreenContract.View view;
    @Mock
    private HomeScreenContract.Navigator navigator;
    @Mock
    private UserSession session;
    @Mock
    private UserScope userScope;
    @InjectMocks
    private HomeScreenPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter.attach(view);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        verify(getTodoListInteractor).dispose();
    }

    @Test
    public void test_onAddTodo() {
        presenter.onAddTodo();
        verify(navigator).gotoAddTodoScreen();
    }

    @Test
    public void test_logout() {
        presenter.logout();
        verify(userScope).end();
        verify(navigator).goToLoginScreen();
    }

    @Test
    public void test_onLogout() {
        presenter.onLogout();
        verify(view).showLogoutConfirmationDialog();
    }
}
package com.android.basics.features.todo.presentation.home;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.features.todo.di.UserScope;
import com.android.basics.features.todo.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @Mock
    private User user;
    @InjectMocks
    private HomeScreenPresenter presenter;
    @Captor
    private ArgumentCaptor<Callback<List<Todo>>> todoListCallbackCaptor;

    private List<Todo> todoList;

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

    private void testonLoadTodoList_arrange_act_init() {

        //Arrange
        when(session.getUser()).thenReturn(user);
        when(user.getUserName()).thenReturn(TestUtil.USER_NAME);
        todoList = TestUtil.buildMockTodList();

        //Act
        presenter.onLoadTodoList(TestUtil.USER_ID);

        //Assert
        verify(view).setWelcomeMessage("Welcome " + TestUtil.USER_NAME);
        verify(view).showProgressDialog();
        verify(getTodoListInteractor).execute(any(GetTodoListInteractor.Params.class), todoListCallbackCaptor.capture());
    }

    @Test
    public void testonLoadTodoList_success() {

        testonLoadTodoList_arrange_act_init();

        todoListCallbackCaptor.getValue().onResponse(todoList);
        verify(session).setTodoList(todoList);
        verify(view).showList(true);
        verify(view).showErrorLayout(false);
        verify(view).loadTodoList(todoList);
        verify(view).dismissProgressDialog();
    }

    @Test
    public void testonLoadTodoList_error() {

        testonLoadTodoList_arrange_act_init();

        todoListCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).showList(false);
        verify(view).showErrorLayout(true);
        verify(view).dismissProgressDialog();
    }

    @Test
    public void testonLoadTodoList_failure() {

        testonLoadTodoList_arrange_act_init();

        todoListCallbackCaptor.getValue().onResponse(null);
        verify(view).showList(false);
        verify(view).showErrorLayout(true);
        verify(view).dismissProgressDialog();
    }


}
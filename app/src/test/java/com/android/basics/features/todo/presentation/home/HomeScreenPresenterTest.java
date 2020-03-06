package com.android.basics.features.todo.presentation.home;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.TestUseCaseScheduler;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.features.todo.di.UserScope;
import com.android.basics.features.todo.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.TodoRepository;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

public class HomeScreenPresenterTest {


    @Mock
    private TodoRepository todoRepository;
    @Mock
    private HomeScreenContract.View view;
    @Mock
    private HomeScreenContract.Navigator navigator;
    @Mock
    private UserScope userScope;
    @Captor
    private ArgumentCaptor<Callback<List<Todo>>> todoListCallbackCaptor;

    @Captor
    private ArgumentCaptor<List<Todo>> todoListCaptor;

    private GetTodoListInteractor getTodoListInteractor;
    private UserSession session;

    private User user;
    private HomeScreenPresenter presenter;
    private List<Todo> todoList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = providePresenter();
        presenter.attach(view);
    }

    private HomeScreenPresenter providePresenter() {
        session = UserSession.getInstance();
        session.setUser(new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD));
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        getTodoListInteractor = new GetTodoListInteractor(todoRepository);
        return new HomeScreenPresenter(getTodoListInteractor, session, userScope, navigator, useCaseHandler);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        Assert.assertTrue(getTodoListInteractor.isDisposed());
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

    @Test
    public void testonLoadTodoList_success() {
        //Arrange
        todoList = TestUtil.buildFakeTodList();

        //Act
        presenter.onLoadTodoList(TestUtil.USER_ID);

        //verify and Mock the response
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoListCallbackCaptor.capture());
        todoListCallbackCaptor.getValue().onResponse(todoList);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setWelcomeMessage("Welcome " + TestUtil.USER_NAME);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).showList(true);
        inOrder.verify(view).showErrorLayout(false);
        inOrder.verify(view).loadTodoList(todoListCaptor.capture());
        inOrder.verify(view).dismissProgressDialog();

        Assert.assertEquals(2, session.getTodoList().size());
        Assert.assertEquals(2, todoListCaptor.getValue().size());
    }

    @Test
    public void testonLoadTodoList_error() {

        //Arrange
        //Nothing

        //Act
        presenter.onLoadTodoList(TestUtil.USER_ID);

        //verify and Mock the response
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoListCallbackCaptor.capture());
        todoListCallbackCaptor.getValue().onError(TestUtil.ERROR);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showList(false);
        inOrder.verify(view).showErrorLayout(true);
        inOrder.verify(view).dismissProgressDialog();
    }

    @Test
    public void testonLoadTodoList_failure() {

        //Arrange
        //Nothing

        //Act
        presenter.onLoadTodoList(TestUtil.USER_ID);

        //verify and Mock the response
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoListCallbackCaptor.capture());
        todoListCallbackCaptor.getValue().onResponse(null);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showList(false);
        inOrder.verify(view).showErrorLayout(true);
        inOrder.verify(view).dismissProgressDialog();
    }


}
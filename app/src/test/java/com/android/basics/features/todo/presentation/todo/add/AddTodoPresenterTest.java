package com.android.basics.features.todo.presentation.todo.add;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.TestUseCaseScheduler;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.features.todo.domain.interactor.todo.AddTodoInteractor;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

public class AddTodoPresenterTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private AddTodoContract.View view;
    @Mock
    private AddTodoContract.Navigator navigator;
    @Captor
    private ArgumentCaptor<Callback<Boolean>> addTodoCallbackCaptor;

    private AddTodoPresenter presenter;
    private AddTodoInteractor addTodoInteractor;
    private UserSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = providePresenter();
        presenter.attach(view);
    }

    private AddTodoPresenter providePresenter() {
        session = UserSession.getInstance();
        session.setUser(new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD));
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        addTodoInteractor = new AddTodoInteractor(todoRepository);
        return new AddTodoPresenter(navigator, addTodoInteractor, session, useCaseHandler);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        Assert.assertTrue(addTodoInteractor.isDisposed());
    }

    @Test
    public void test_navigate() {
        presenter.navigateToViewTodoList();
        verify(navigator).goToHomeScreen();
    }

    @Test
    public void test_OnCancel() {
        presenter.OnCancel();
        verify(navigator).goToHomeScreen();
    }

    @Test
    public void test_onSelectDate() {
        presenter.onSelectDate();
        verify(view).showDatePickerDialog();
    }

    @Test
    public void test_onSubmit_success() {
        //Assume
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the call back
        verify(todoRepository).addTodo(any(Todo.class), addTodoCallbackCaptor.capture());
        addTodoCallbackCaptor.getValue().onResponse(true);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showSuccessDialog();
    }

    @Test
    public void test_onSubmit_error() {
        //Assume
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the call back
        verify(todoRepository).addTodo(any(Todo.class), addTodoCallbackCaptor.capture());
        addTodoCallbackCaptor.getValue().onError(TestUtil.ERROR);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog();
    }

    @Test
    public void test_onSubmit_failure() {
        //Assume
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the call back
        verify(todoRepository).addTodo(any(Todo.class), addTodoCallbackCaptor.capture());
        addTodoCallbackCaptor.getValue().onResponse(false);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog();
    }

}
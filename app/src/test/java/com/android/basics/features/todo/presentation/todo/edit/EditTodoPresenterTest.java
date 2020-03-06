package com.android.basics.features.todo.presentation.todo.edit;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.TestUseCaseScheduler;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.features.todo.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.features.todo.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;
import com.android.basics.features.todo.presentation.components.TodoSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

public class EditTodoPresenterTest {
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private EditTodoContract.Navigator navigator;
    @Mock
    private EditTodoContract.View view;
    @Captor
    private ArgumentCaptor<Callback<Boolean>> addEditTodoCallbackCaptor;

    private EditTodoPresenter presenter;
    private EditTodoInteractor editTodoInteractor;
    private DeleteTodoInteractor deleteTodoInteractor;
    private TodoSession session;
    private Todo todo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = providePresenter();
        presenter.attach(view);
    }

    private EditTodoPresenter providePresenter() {
        session = TodoSession.getInstance();
        todo = new Todo(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.EDIT_DATE, false);
        session.setTodo(todo);
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        editTodoInteractor = new EditTodoInteractor(todoRepository);
        deleteTodoInteractor = new DeleteTodoInteractor(todoRepository);
        return new EditTodoPresenter(editTodoInteractor, deleteTodoInteractor, navigator, session, useCaseHandler);
    }


    @Test
    public void test_detach() {
        presenter.detach();
        Assert.assertTrue(editTodoInteractor.isDisposed());
        Assert.assertTrue(deleteTodoInteractor.isDisposed());
    }

    @Test
    public void test_loadTodo() {
        //Arrange
        //Nothing

        //Act
        presenter.loadTodo();

        //Assert

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setName(todo.getName());
        inOrder.verify(view).setDescription(todo.getDescription());
        inOrder.verify(view).setDate(todo.getDueDate());
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
    public void test_onDelete_success() {
        //Arrange
        //Nothing

        //Act
        presenter.onDelete();

        //Mock the callback
        verify(todoRepository).deleteTodo(eq(TestUtil.TODO_ID), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onResponse(true);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Deleting todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showSuccessDialog("Record successfully deleted.");
    }

    @Test
    public void test_onDelete_error() {
        //Arrange
        //Nothing

        //Act
        presenter.onDelete();

        //Mock the callback
        verify(todoRepository).deleteTodo(eq(TestUtil.TODO_ID), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onError(TestUtil.ERROR);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Deleting todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog("Error deleting todo");
    }

    @Test
    public void test_onDelete_failure() {
        //Arrange
        //Nothing

        //Act
        presenter.onDelete();

        //Mock the callback
        verify(todoRepository).deleteTodo(eq(TestUtil.TODO_ID), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onResponse(false);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Deleting todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog("Error deleting todo");
    }

    @Test
    public void test_onSubmit_success() {
        //Arrange
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the callback
        verify(todoRepository).editTodo(any(Todo.class), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onResponse(true);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Updating todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showSuccessDialog("Record successfully updated.");
    }

    @Test
    public void test_onSubmit_error() {
        //Arrange
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the callback
        verify(todoRepository).editTodo(any(Todo.class), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onError(TestUtil.ERROR);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Updating todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog("There was a problem. could not able to update the record.");
    }

    @Test
    public void test_onSubmit_failure() {
        //Arrange
        //Nothing

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Mock the callback
        verify(todoRepository).editTodo(any(Todo.class), addEditTodoCallbackCaptor.capture());
        addEditTodoCallbackCaptor.getValue().onResponse(false);

        //Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog("Updating todo");
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showErrorDialog("There was a problem. could not able to update the record.");
    }

}


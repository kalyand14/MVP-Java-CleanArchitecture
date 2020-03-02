package com.android.basics.features.todo.presentation.todo.edit;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.features.todo.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.presentation.components.TodoSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditTodoPresenterTest {

    @Mock
    private EditTodoInteractor editTodoInteractor;
    @Mock
    private DeleteTodoInteractor deleteTodoInteractor;
    @Mock
    private EditTodoContract.Navigator navigator;
    @Mock
    private EditTodoContract.View view;
    @Mock
    private TodoSession session;
    @InjectMocks
    private EditTodoPresenter presenter;
    @Mock
    private Todo todo;
    @Captor
    private ArgumentCaptor<Callback<Boolean>> addEditTodoCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter.attach(view);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        verify(editTodoInteractor).dispose();
        verify(deleteTodoInteractor).dispose();
    }

    @Test
    public void test_loadTodo() {
        //Arrange
        when(session.getTodo()).thenReturn(todo);

        //Act
        presenter.loadTodo();

        //Assert
        verify(view).setName(todo.getName());
        verify(view).setDescription(todo.getDescription());
        verify(view).setDate(todo.getDueDate());
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

    private void test_onDelete_arrange_act_init() {

        //Arrange
        when(session.getTodo()).thenReturn(todo);
        when(todo.getTodoId()).thenReturn(TestUtil.TODO_ID);

        //Act
        presenter.onDelete();

        //Assert
        verify(view).showProgressDialog("Deleting todo");
        verify(deleteTodoInteractor).execute(eq(TestUtil.TODO_ID), addEditTodoCallbackCaptor.capture());
    }

    @Test
    public void test_onDelete_success() {
        test_onDelete_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onResponse(true);
        verify(view).dismissProgressDialog();
        verify(view).showSuccessDialog("Record successfully deleted.");
    }

    @Test
    public void test_onDelete_error() {
        test_onDelete_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog("Error deleting todo");
    }

    @Test
    public void test_onDelete_failure() {
        test_onDelete_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onResponse(false);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog("Error deleting todo");
    }

    private void test_onSubmit_arrange_act_init() {

        //Arrange
        when(session.getTodo()).thenReturn(todo);
        when(todo.getTodoId()).thenReturn(TestUtil.TODO_ID);

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Assert
        verify(view).showProgressDialog("Updating todo");
        verify(editTodoInteractor).execute(any(EditTodoInteractor.Request.class), addEditTodoCallbackCaptor.capture());
    }

    @Test
    public void test_onSubmit_success() {
        test_onSubmit_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onResponse(true);
        verify(view).dismissProgressDialog();
        verify(view).showSuccessDialog("Record successfully updated.");
    }

    @Test
    public void test_onSubmit_error() {
        test_onSubmit_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog("There was a problem. could not able to update the record.");
    }

    @Test
    public void test_onSubmit_failure() {
        test_onSubmit_arrange_act_init();
        addEditTodoCallbackCaptor.getValue().onResponse(false);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog("There was a problem. could not able to update the record.");
    }

}
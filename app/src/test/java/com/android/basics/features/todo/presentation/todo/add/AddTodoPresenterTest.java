package com.android.basics.features.todo.presentation.todo.add;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.interactor.todo.AddTodoInteractor;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddTodoPresenterTest {

    @Mock
    private AddTodoContract.View view;
    @Mock
    private AddTodoContract.Navigator navigator;
    @Mock
    private AddTodoInteractor addTodoInteractor;
    @Mock
    private UserSession session;
    @InjectMocks
    private AddTodoPresenter presenter;
    @Captor
    private ArgumentCaptor<Callback<Boolean>> addTodoCallbackCaptor;
    @Mock
    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter.attach(view);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        verify(addTodoInteractor).dispose();
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

    private void test_onSubmit_arrange_act_init() {

        //Arrange
        when(session.getUser()).thenReturn(user);
        when(user.getUserId()).thenReturn(TestUtil.USER_ID);

        //Act
        presenter.onSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE);

        //Assert
        verify(view).showProgressDialog();
        verify(addTodoInteractor).execute(any(AddTodoInteractor.Request.class), addTodoCallbackCaptor.capture());
    }

    @Test
    public void test_onSubmit_success() {
        test_onSubmit_arrange_act_init();
        addTodoCallbackCaptor.getValue().onResponse(true);
        verify(view).dismissProgressDialog();
        verify(view).showSuccessDialog();
    }

    @Test
    public void test_onSubmit_error() {
        test_onSubmit_arrange_act_init();
        addTodoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog();
    }

    @Test
    public void test_onSubmit_failure() {
        test_onSubmit_arrange_act_init();
        addTodoCallbackCaptor.getValue().onResponse(false);
        verify(view).dismissProgressDialog();
        verify(view).showErrorDialog();
    }

}
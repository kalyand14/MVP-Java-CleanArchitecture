package com.android.basics.features.todo.presentation.registration;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.interactor.user.RegisterUserInteractor;
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

public class RegisterUserPresenterTest {
    @Mock
    private RegisterUserInteractor registerUserInteractor;
    @Mock
    private RegisterUserContract.View view;
    @Mock
    private RegisterUserContract.Navigator navigator;
    @Mock
    private UserSession session;
    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;
    @Mock
    private User user;

    @InjectMocks
    private RegisterUserPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter.attach(view);
    }


    @Test
    public void test_onLoginClick() {
        presenter.onLoginClick();
        verify(navigator).goToLoginScreen();
    }

    @Test
    public void test_onRegistrationSuccess() {
        presenter.onRegistrationSuccess();
        verify(navigator).goToHomeScreen();
    }

    @Test
    public void test_detach() {
        presenter.detach();
        verify(registerUserInteractor).dispose();
    }

    @Test
    public void test_onRegisterClick_success() {
        presenter.onRegisterClick(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(view).showProgressDialog();
        verify(registerUserInteractor).execute(any(RegisterUserInteractor.Params.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(session).setUser(user);
        verify(view).dismissProgressDialog();
        verify(view).showRegistrationSuccess();
    }

    @Test
    public void test_onRegisterClick_error() {
        presenter.onRegisterClick(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(view).showProgressDialog();
        verify(registerUserInteractor).execute(any(RegisterUserInteractor.Params.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).dismissProgressDialog();
        verify(view).showRegistrationError();
    }


}
package com.android.basics.features.todo.presentation.login;

import com.android.basics.TestUtil;
import com.android.basics.core.domain.Callback;
import com.android.basics.features.todo.domain.interactor.user.AuthenticateUserInteractor;
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

public class LoginPresenterTest {
    @Mock
    private LoginContract.Navigator navigator;
    @Mock
    private AuthenticateUserInteractor authenticateUserInteractor;
    @Mock
    private LoginContract.View view;
    @Mock
    private UserSession session;
    @InjectMocks
    private LoginPresenter presenter;
    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;
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
        verify(authenticateUserInteractor).dispose();
    }

    @Test
    public void test_onRegisterClick() {
        presenter.onRegisterClick();
        verify(navigator).goToRegisterScreen();
    }

    @Test
    public void test_OnLoginClick_success() {
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(view).showProgressDialog();
        verify(authenticateUserInteractor).execute(any(AuthenticateUserInteractor.Params.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(view).dismissProgressDialog();
        verify(session).setUser(user);
        verify(navigator).goToHomeScreen();
    }

    @Test
    public void test_OnLoginClick_failure() {
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(view).showProgressDialog();
        verify(authenticateUserInteractor).execute(any(AuthenticateUserInteractor.Params.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(null);
        verify(view).dismissProgressDialog();
        verify(view).showAuthenticationError();
    }

    @Test
    public void test_OnLoginClick_error() {
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(view).showProgressDialog();
        verify(authenticateUserInteractor).execute(any(AuthenticateUserInteractor.Params.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(view).dismissProgressDialog();
        verify(view).showAuthenticationError();
    }
}
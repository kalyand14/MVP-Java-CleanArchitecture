package com.android.basics.features.todo.presentation.login;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.TestUseCaseScheduler;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.features.todo.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;


public class LoginPresenterTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoginContract.Navigator navigator;
    @Mock
    private LoginContract.View view;
    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private User user;
    private AuthenticateUserInteractor authenticateUserInteractor;
    private LoginPresenter presenter;
    private UserSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = providePresenter();
        presenter.attach(view);
    }

    private LoginPresenter providePresenter() {
        session = UserSession.getInstance();
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        authenticateUserInteractor = new AuthenticateUserInteractor(userRepository);
        return new LoginPresenter(navigator, authenticateUserInteractor, session, useCaseHandler);
    }

    @Test
    public void test_detach() {
        presenter.detach();
        Assert.assertTrue(authenticateUserInteractor.isDisposed());
    }

    @Test
    public void test_onRegisterClick() {
        presenter.onRegisterClick();
        verify(navigator).goToRegisterScreen();
    }

    @Test
    public void test_OnLoginClick_success() {
        //Assume
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Act
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);

        // Callback is captured and invoked with Fake user
        verify(userRepository).authenticate(userArgumentCaptor.capture(), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);

        //Assert
        Assert.assertNull(userArgumentCaptor.getValue().getUserId());

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();

        Assert.assertThat(user.getUserId(), Matchers.is(session.getUser().getUserId()));
        verify(navigator).goToHomeScreen();
    }

    @Test
    public void test_OnLoginClick_failure() {
        //Assume
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Act
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);

        // Callback is captured and invoked with Fake user
        verify(userRepository).authenticate(userArgumentCaptor.capture(), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(null);

        //Assert
        Assert.assertNull(userArgumentCaptor.getValue().getUserId());

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showAuthenticationError();

        Assert.assertNull(session.getUser());
    }

    @Test
    public void test_OnLoginClick_error() {
        //Assume
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Act
        presenter.OnLoginClick(TestUtil.USER_NAME, TestUtil.PASSWORD);

        // Callback is captured and invoked with Fake user
        verify(userRepository).authenticate(userArgumentCaptor.capture(), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestUtil.ERROR);

        //Assert
        Assert.assertNull(userArgumentCaptor.getValue().getUserId());

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showProgressDialog();
        inOrder.verify(view).dismissProgressDialog();
        inOrder.verify(view).showAuthenticationError();

        Assert.assertNull(session.getUser());
    }
}


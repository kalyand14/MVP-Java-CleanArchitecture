package com.android.basics.features.todo.presentation.registration;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.TestUseCaseScheduler;
import com.android.basics.core.UseCaseHandler;
import com.android.basics.features.todo.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;
import com.android.basics.features.todo.presentation.components.UserSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


public class RegisterUserPresenterTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RegisterUserContract.View view;
    @Mock
    private RegisterUserContract.Navigator navigator;
    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;

    private User user;
    private UserSession session;
    private RegisterUserInteractor registerUserInteractor;
    private RegisterUserPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = providePresenter();
        presenter.attach(view);
    }

    private RegisterUserPresenter providePresenter() {
        session = UserSession.getInstance();
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        registerUserInteractor = new RegisterUserInteractor(userRepository);
        return new RegisterUserPresenter(navigator, registerUserInteractor, session, useCaseHandler);
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
        Assert.assertTrue(registerUserInteractor.isDisposed());
    }

    @Test
    public void test_onRegisterClick_success() {
        //Assume
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Act
        presenter.onRegisterClick(TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Assert
        verify(view).showProgressDialog();
        verify(userRepository).register(any(User.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        Assert.assertEquals(user.getUserId(), session.getUser().getUserId());
        verify(view).dismissProgressDialog();
        verify(view).showRegistrationSuccess();
    }

    @Test
    public void test_onRegisterClick_error() {
        //Assume
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);
        Error error = TestUtil.ERROR;

        //Act
        presenter.onRegisterClick(TestUtil.USER_NAME, TestUtil.PASSWORD);

        //Assert
        verify(view).showProgressDialog();
        verify(view).showProgressDialog();
        verify(userRepository).register(any(User.class), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(error);
        verify(view).dismissProgressDialog();
        verify(view).showRegistrationError();
    }


}
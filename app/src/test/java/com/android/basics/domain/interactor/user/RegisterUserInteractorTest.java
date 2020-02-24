package com.android.basics.domain.interactor.user;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
import com.android.basics.domain.model.User;
import com.android.basics.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserInteractorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegisterUserInteractor.Params params;

    @InjectMocks
    private RegisterUserInteractor interactor;

    @Mock
    private Callback<User> userCallback;

    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;

    @Mock
    private User user;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        params = RegisterUserInteractor.Params.forUser(TestConstants.USER_NAME, TestConstants.PASSWORD);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(params, userCallback);
        verify(userRepository).register(eq(TestConstants.USER_NAME), eq(TestConstants.PASSWORD), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(userCallback).onResponse(user);
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(params, userCallback);
        verify(userRepository).register(eq(TestConstants.USER_NAME), eq(TestConstants.PASSWORD), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(userCallback, never()).onResponse(user);
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(params, userCallback);
        verify(userRepository).register(eq(TestConstants.USER_NAME), eq(TestConstants.PASSWORD), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(userCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

    @Test
    public void testExecute_forfailure_dispose() {
        interactor.dispose();
        interactor.executeTask(params, userCallback);
        verify(userRepository).register(eq(TestConstants.USER_NAME), eq(TestConstants.PASSWORD), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(userCallback, never()).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }
}
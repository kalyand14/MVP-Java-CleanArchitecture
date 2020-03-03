package com.android.basics.features.todo.domain.interactor.user;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserInteractorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegisterUserInteractor.Request request;

    @InjectMocks
    private RegisterUserInteractor interactor;

    @Mock
    private UseCase.UseCaseCallback<RegisterUserInteractor.Response> userCallback;

    @Captor
    private ArgumentCaptor<Callback<User>> userCallbackCaptor;

    @Mock
    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getUser()).thenReturn(user);
        interactor.setUseCaseCallback(userCallback);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeUseCase(request);
        verify(userRepository).register(ArgumentMatchers.eq(user), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(userCallback).onSuccess(any(RegisterUserInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(userRepository).register(ArgumentMatchers.eq(user), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onResponse(user);
        verify(userCallback, never()).onSuccess(any(RegisterUserInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(userRepository).register(ArgumentMatchers.eq(user), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestUtil.ERROR);
        verify(userCallback).onError(TestUtil.ERROR);
    }

    @Test
    public void testExecute_forfailure_dispose() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(userRepository).register(ArgumentMatchers.eq(user), userCallbackCaptor.capture());
        userCallbackCaptor.getValue().onError(TestUtil.ERROR);
        verify(userCallback, never()).onError(TestUtil.ERROR);
    }
}

package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddTodoInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private AddTodoInteractor.Request request;

    @InjectMocks
    private AddTodoInteractor interactor;

    @Mock
    private UseCase.UseCaseCallback<AddTodoInteractor.Response> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Boolean>> todoCallbackCaptor;

    @Mock
    private Todo todo;

    @Mock
    private Error error;

    private static final boolean IS_ADDED = true;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(request.getTodo()).thenReturn(todo);
        interactor.setUseCaseCallback(todoCallback);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeUseCase(request);
        verify(todoRepository).addTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_ADDED);
        verify(todoCallback).onSuccess(any(AddTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).addTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_ADDED);
        verify(todoCallback, Mockito.never()).onSuccess(any(AddTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(todoRepository).addTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback).onError(error);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).addTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback, never()).onError(error);
    }

}
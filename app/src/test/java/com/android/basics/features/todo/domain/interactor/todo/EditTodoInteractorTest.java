package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class EditTodoInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private EditTodoInteractor.Request request;

    @InjectMocks
    private EditTodoInteractor interactor;

    @Mock
    private UseCase.UseCaseCallback<EditTodoInteractor.Response> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Boolean>> todoCallbackCaptor;

    private static final boolean IS_UPDATED = true;

    @Mock
    private Todo todo;

    @Mock
    private Error error;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(request.getTodo()).thenReturn(todo);
        interactor.setUseCaseCallback(todoCallback);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeUseCase(request);
        verify(todoRepository).editTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback).onSuccess(any(EditTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).editTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback, never()).onSuccess(any(EditTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(todoRepository).editTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback).onError(error);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).editTodo(eq(todo), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback, never()).onError(error);
    }
}
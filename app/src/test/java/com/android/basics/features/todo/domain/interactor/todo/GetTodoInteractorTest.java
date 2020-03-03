package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.TestUtil;
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
public class GetTodoInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private GetTodoInteractor interactor;

    @Mock
    private UseCase.UseCaseCallback<GetTodoInteractor.Response> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Todo>> todoCallbackCaptor;

    @Mock
    private GetTodoInteractor.Request request;

    @Mock
    private Todo todo;

    @Mock
    private Error error;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(request.getTodoId()).thenReturn(TestUtil.TODO_ID);
        interactor.setUseCaseCallback(todoCallback);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeUseCase(request);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(todo);
        verify(todoCallback).onSuccess(any(GetTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(todo);
        verify(todoCallback, never()).onSuccess(any(GetTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback).onError(error);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback, never()).onError(error);
    }
}
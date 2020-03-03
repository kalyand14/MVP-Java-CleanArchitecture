package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTodoInteractorTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private DeleteTodoInteractor interactor;

    @Mock
    private DeleteTodoInteractor.Request request;

    @Mock
    private UseCase.UseCaseCallback<DeleteTodoInteractor.Response> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Boolean>> todoCallbackCaptor;

    private static final boolean IS_DELETED = true;

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
        verify(todoRepository).deleteTodo(ArgumentMatchers.eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_DELETED);
        verify(todoCallback).onSuccess(any(DeleteTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).deleteTodo(ArgumentMatchers.eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(IS_DELETED);
        verify(todoCallback, never()).onSuccess(any(DeleteTodoInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(todoRepository).deleteTodo(ArgumentMatchers.eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback).onError(error);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).deleteTodo(ArgumentMatchers.eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback, never()).onError(error);
    }
}

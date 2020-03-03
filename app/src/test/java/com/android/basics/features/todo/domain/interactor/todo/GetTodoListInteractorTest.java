package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.TestFactory;
import com.android.basics.TestUtil;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class GetTodoListInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private GetTodoListInteractor interactor;

    @Mock
    private UseCase.UseCaseCallback<GetTodoListInteractor.Response> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<List<Todo>>> todoCallbackCaptor;

    @Mock
    private GetTodoListInteractor.Request request;

    @Mock
    private List<Todo> todoList;

    @Mock
    private Error error;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(request.getUserId()).thenReturn(TestUtil.USER_ID);
        interactor.setUseCaseCallback(todoCallback);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeUseCase(request);
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(TestFactory.buildMockTodList());
        verify(todoCallback).onSuccess(any(GetTodoListInteractor.Response.class));
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(TestFactory.buildMockTodList());
        verify(todoCallback, never()).onSuccess(any(GetTodoListInteractor.Response.class));
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeUseCase(request);
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback).onError(error);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeUseCase(request);
        verify(todoRepository).getTodoList(eq(TestUtil.USER_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(error);
        verify(todoCallback, never()).onError(error);
    }

}

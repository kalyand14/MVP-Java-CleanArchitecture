package com.android.basics.domain.interactor.todo;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class GetTodoListInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private GetTodoListInteractor interactor;

    @Mock
    private Callback<List<Todo>> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<List<Todo>>> todoCallbackCaptor;

    @Mock
    private GetTodoListInteractor.Params params;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        params = GetTodoListInteractor.Params.forUser(TestConstants.USER_ID);

    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).getTodoList(eq(TestConstants.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(TestConstants.buildMockTodList());
        verify(todoCallback).onResponse(Mockito.<Todo>anyList());
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).getTodoList(eq(TestConstants.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(TestConstants.buildMockTodList());
        verify(todoCallback, never()).onResponse(Mockito.<Todo>anyList());
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).getTodoList(eq(TestConstants.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(todoCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).getTodoList(eq(TestConstants.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(todoCallback, never()).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

}
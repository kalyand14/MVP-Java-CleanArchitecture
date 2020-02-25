package com.android.basics.domain.interactor.todo;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
    private Callback<Todo> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Todo>> todoCallbackCaptor;

    @Mock
    private Todo todo;

    private static final boolean IS_DELETED = true;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(TestUtil.TODO_ID, todoCallback);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(todo);
        verify(todoCallback).onResponse(todo);
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(TestUtil.TODO_ID, todoCallback);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onResponse(todo);
        verify(todoCallback, never()).onResponse(todo);
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(TestUtil.TODO_ID, todoCallback);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(todoCallback).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeTask(TestUtil.TODO_ID, todoCallback);
        verify(todoRepository).getTodo(eq(TestUtil.TODO_ID), todoCallbackCaptor.capture());
        todoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(todoCallback, never()).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }
}
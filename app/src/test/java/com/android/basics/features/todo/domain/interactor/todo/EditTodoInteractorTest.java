package com.android.basics.features.todo.domain.interactor.todo;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.repository.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private Callback<Boolean> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Boolean>> todoCallbackCaptor;

    private static final boolean IS_UPDATED = true;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = EditTodoInteractor.Request.forTodo(TestUtil.TODO_ID, TestUtil.USER_NAME, TestUtil.DESCRIPTION, TestUtil.DATE);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(request, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestUtil.TODO_ID),
                eq(TestUtil.USER_NAME),
                eq(TestUtil.DESCRIPTION),
                eq(TestUtil.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback).onResponse(IS_UPDATED);
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(request, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestUtil.TODO_ID),
                eq(TestUtil.USER_NAME),
                eq(TestUtil.DESCRIPTION),
                eq(TestUtil.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback, never()).onResponse(IS_UPDATED);
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(request, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestUtil.TODO_ID),
                eq(TestUtil.USER_NAME),
                eq(TestUtil.DESCRIPTION),
                eq(TestUtil.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(todoCallback).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeTask(request, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestUtil.TODO_ID),
                eq(TestUtil.USER_NAME),
                eq(TestUtil.DESCRIPTION),
                eq(TestUtil.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
        verify(todoCallback, never()).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }
}
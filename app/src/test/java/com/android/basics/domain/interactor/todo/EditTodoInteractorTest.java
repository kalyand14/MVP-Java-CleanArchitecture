package com.android.basics.domain.interactor.todo;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
import com.android.basics.domain.repository.TodoRepository;

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
    private EditTodoInteractor.Params params;

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
        params = EditTodoInteractor.Params.forTodo(TestConstants.TODO_ID, TestConstants.USER_NAME, TestConstants.DESCRIPTION, TestConstants.DATE);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestConstants.TODO_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback).onResponse(IS_UPDATED);
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestConstants.TODO_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_UPDATED);
        verify(todoCallback, never()).onResponse(IS_UPDATED);
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestConstants.TODO_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(todoCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

    @Test
    public void testExecute_forfailure_disposed() {
        interactor.dispose();
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).editTodo(
                eq(TestConstants.TODO_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(todoCallback, never()).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }
}
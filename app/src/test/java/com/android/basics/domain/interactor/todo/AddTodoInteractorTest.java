package com.android.basics.domain.interactor.todo;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
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
public class AddTodoInteractorTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private AddTodoInteractor.Params params;

    @InjectMocks
    private AddTodoInteractor interactor;

    @Mock
    private Callback<Boolean> todoCallback;

    @Captor
    private ArgumentCaptor<Callback<Boolean>> todoCallbackCaptor;

    private static final boolean IS_ADDED = true;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        params = AddTodoInteractor.Params.forTodo(TestConstants.USER_ID, TestConstants.USER_NAME, TestConstants.DESCRIPTION, TestConstants.DATE);
    }

    @Test
    public void testExecute_for_success() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).addTodo(
                eq(TestConstants.USER_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_ADDED);
        verify(todoCallback).onResponse(IS_ADDED);
    }

    @Test
    public void testExecute_for_success_diposed() {
        interactor.dispose();
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).addTodo(
                eq(TestConstants.USER_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onResponse(IS_ADDED);
        verify(todoCallback, never()).onResponse(IS_ADDED);
    }

    @Test
    public void testExecute_forfailure() {
        interactor.executeTask(params, todoCallback);
        verify(todoRepository).addTodo(
                eq(TestConstants.USER_ID),
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
        verify(todoRepository).addTodo(
                eq(TestConstants.USER_ID),
                eq(TestConstants.USER_NAME),
                eq(TestConstants.DESCRIPTION),
                eq(TestConstants.DATE),
                todoCallbackCaptor.capture()
        );
        todoCallbackCaptor.getValue().onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
        verify(todoCallback, never()).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }
}
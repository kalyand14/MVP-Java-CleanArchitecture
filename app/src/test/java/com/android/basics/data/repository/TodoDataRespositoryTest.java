package com.android.basics.data.repository;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
import com.android.basics.data.component.DaoCallback;
import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.TodoListMapper;
import com.android.basics.data.mapper.TodoMapper;
import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.domain.model.Todo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoDataRespositoryTest {

    @Mock
    private TodoDao todoDao;
    @Mock
    private TodoMapper todoMapper;
    @Mock
    private TodoListMapper todoListMapper;
    @Mock
    private DaoExecutor daoExecutor;
    @InjectMocks
    private TodoDataRespository todoDataRespository;
    @Captor
    private ArgumentCaptor<DaoCallback<Todo>> callbackArgumentCaptor;
    @Mock
    private Callback<Todo> todoCallback;
    @Mock
    private TodoTbl todoTbl;
    @Mock
    private Todo todo;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void tes_getTodo_doAsync_success() {
        when(todoDao.getTodo(TestConstants.TODO_ID)).thenReturn(todoTbl);
        todoDataRespository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(todoMapper).convert(todoTbl);
    }

    @Test
    public void tes_getTodo_onComplete_success() {
        todoDataRespository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(todo);
        verify(todoCallback).onResponse(todo);
    }

    @Test
    public void tes_getTodo_onComplete_error() {
        todoDataRespository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(todoCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

  
}
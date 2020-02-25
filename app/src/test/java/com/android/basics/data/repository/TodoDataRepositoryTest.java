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

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoDataRepositoryTest {

    @Mock
    private TodoDao todoDao;
    @Mock
    private TodoMapper todoMapper;
    @Mock
    private TodoListMapper todoListMapper;
    @Mock
    private DaoExecutor daoExecutor;
    @InjectMocks
    private TodoDataRepository todoDataRepository;
    @Captor
    private ArgumentCaptor<DaoCallback<Todo>> callbackArgumentCaptor;
    @Mock
    private Callback<Todo> todoCallback;
    @Mock
    private TodoTbl todoTbl;
    @Mock
    private Todo todo;
    @Captor
    private ArgumentCaptor<DaoCallback<List<Todo>>> todoListCallbackArgumentCaptor;
    @Mock
    private Callback<List<Todo>> todoListCallback;
    @Mock
    private List<TodoTbl> todoTblList;
    @Mock
    private List<Todo> todoList;


    @Captor
    private ArgumentCaptor<DaoCallback<Integer>> cudCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<DaoCallback<Long>> addTodoCallbackArgumentCaptor;

    @Mock
    private Callback<Boolean> cudCallback;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getTodo_doAsync_success() {
        when(todoDao.getTodo(TestConstants.TODO_ID)).thenReturn(todoTbl);
        todoDataRepository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(todoMapper).convert(todoTbl);
    }

    @Test
    public void test_getTodo_onComplete_success() {
        todoDataRepository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(todo);
        verify(todoCallback).onResponse(todo);
    }

    @Test
    public void test_getTodo_onComplete_error() {
        todoDataRepository.getTodo(TestConstants.TODO_ID, todoCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(todoCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

    @Test
    public void test_getTodoList_doAsync_success() {
        when(todoDao.getAllTodo(TestConstants.USER_ID)).thenReturn(todoTblList);
        todoDataRepository.getTodoList(TestConstants.USER_ID, todoListCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(todoListMapper).convert(todoTblList);
    }

    @Test
    public void test_getTodoList_onComplete_success() {
        todoDataRepository.getTodoList(TestConstants.USER_ID, todoListCallback);
        verify(daoExecutor).start(todoListCallbackArgumentCaptor.capture());
        todoListCallbackArgumentCaptor.getValue().onComplete(todoList);
        verify(todoListCallback).onResponse(todoList);
    }

    @Test
    public void test_getTodoList_onComplete_error() {
        todoDataRepository.getTodoList(TestConstants.USER_ID, todoListCallback);
        verify(daoExecutor).start(todoListCallbackArgumentCaptor.capture());
        todoListCallbackArgumentCaptor.getValue().onComplete(null);
        verify(todoListCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }


    @Test
    public void test_edit_doAsync_success() {
        when(todoDao.getTodo(TestConstants.TODO_ID)).thenReturn(todoTbl);
        todoDataRepository.editTodo(TestConstants.TODO_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().doAsync();
        verify(todoDao).update(todoTbl);
    }

    @Test
    public void test_edit_complete_success() {
        when(todoDao.getTodo(TestConstants.TODO_ID)).thenReturn(todoTbl);
        todoDataRepository.editTodo(TestConstants.TODO_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().onComplete(1);
        verify(cudCallback).onResponse(true);
    }

    @Test
    public void test_edit_complete_error() {
        when(todoDao.getTodo(TestConstants.TODO_ID)).thenReturn(todoTbl);
        todoDataRepository.editTodo(TestConstants.TODO_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().onComplete(-1);
        verify(cudCallback).onError(TestConstants.ERROR_CODE, TestConstants.EDIT_ERROR_MESSAGE);
    }

    @Test
    public void test_add_doAsync_success() {
        todoDataRepository.addTodo(TestConstants.USER_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(addTodoCallbackArgumentCaptor.capture());
        addTodoCallbackArgumentCaptor.getValue().doAsync();
        verify(todoDao).insert(TestConstants.USER_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, false);
    }

    @Test
    public void test_add_complete_success() {
        todoDataRepository.addTodo(TestConstants.USER_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(addTodoCallbackArgumentCaptor.capture());
        addTodoCallbackArgumentCaptor.getValue().onComplete(TestConstants.TODO_ID_LONG);
        verify(cudCallback).onResponse(true);
    }

    @Test
    public void test_add_complete_error() {
        todoDataRepository.addTodo(TestConstants.USER_ID, TestConstants.NAME, TestConstants.DESCRIPTION, TestConstants.DATE, cudCallback);
        verify(daoExecutor).start(addTodoCallbackArgumentCaptor.capture());
        addTodoCallbackArgumentCaptor.getValue().onComplete(TestConstants.TODO_ID_LONG_NULL);
        verify(cudCallback).onError(TestConstants.ERROR_CODE, TestConstants.INSERT_ERROR_MESSAGE);
    }

    @Test
    public void test_delete_doAsync_success() {
        todoDataRepository.deleteTodo(TestConstants.TODO_ID, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().doAsync();
        verify(todoDao).delete(TestConstants.TODO_ID);
    }

    @Test
    public void test_delete_complete_success() {
        todoDataRepository.deleteTodo(TestConstants.TODO_ID, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().onComplete(TestConstants.TODO_ID);
        verify(cudCallback).onResponse(true);
    }

    @Test
    public void test_delete_complete_error() {
        todoDataRepository.deleteTodo(TestConstants.TODO_ID, cudCallback);
        verify(daoExecutor).start(cudCallbackArgumentCaptor.capture());
        cudCallbackArgumentCaptor.getValue().onComplete(-1);
        verify(cudCallback).onError(TestConstants.ERROR_CODE, TestConstants.DELETE_ERROR_MESSAGE);
    }
}
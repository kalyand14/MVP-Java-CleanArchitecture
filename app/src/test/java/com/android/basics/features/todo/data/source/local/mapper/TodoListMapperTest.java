package com.android.basics.features.todo.data.source.local.mapper;

import com.android.basics.TestUtil;
import com.android.basics.features.todo.data.source.local.entity.TodoTbl;
import com.android.basics.features.todo.domain.model.Todo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TodoListMapperTest {

    private TodoListMapper todoListMapper;

    @Before
    public void setUp() {
        todoListMapper = new TodoListMapper();
    }

    @Test
    public void test_convert_success() {
        List<TodoTbl> todoTblList = TestUtil.buildMockTodoTblList();
        List<Todo> todoList = todoListMapper.convert(todoTblList);
        Assert.assertEquals(todoList.size(), todoTblList.size());
    }

    @Test
    public void test_convert_error() {
        List<Todo> todoList = todoListMapper.convert(null);
        Assert.assertNull(todoList);

    }
}
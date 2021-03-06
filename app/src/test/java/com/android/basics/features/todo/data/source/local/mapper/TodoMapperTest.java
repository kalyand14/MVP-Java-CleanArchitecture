package com.android.basics.features.todo.data.source.local.mapper;

import com.android.basics.TestFactory;
import com.android.basics.TestUtil;
import com.android.basics.features.todo.domain.model.Todo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TodoMapperTest {

    private TodoMapper todoMapper;

    @Before
    public void setUp() {
        todoMapper = new TodoMapper();
    }

    @Test
    public void test_convert_success() {
        Todo todo = todoMapper.convert(TestFactory.buildMockTodoTbl(TestUtil.TODO_ID));
        Assert.assertEquals(TestUtil.USER_ID, todo.getTodoId());
    }

    @Test
    public void test_convert_error() {
        Todo todo = todoMapper.convert(null);
        Assert.assertNull(todo);

    }

}
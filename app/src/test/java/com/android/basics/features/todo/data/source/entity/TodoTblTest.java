package com.android.basics.features.todo.data.source.entity;

import com.android.basics.TestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TodoTblTest {

    private TodoTbl todoTbl;

    @Before
    public void setUp() {
        todoTbl = new TodoTbl(TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestUtil.USER_ID, todoTbl.getUserId());
        Assert.assertEquals(TestUtil.NAME, todoTbl.getName());
        Assert.assertEquals(TestUtil.DESCRIPTION, todoTbl.getDescription());
        Assert.assertEquals(TestUtil.DATE, todoTbl.getDueDate());
        Assert.assertFalse(todoTbl.isCompleted());
    }

    @Test
    public void test_setter() {
        todoTbl.setTodoId(2);
        Assert.assertEquals(2, todoTbl.getTodoId());
        todoTbl.setUserId(3);
        Assert.assertEquals(3, todoTbl.getUserId());
        todoTbl.setName("test name");
        Assert.assertEquals("test name", todoTbl.getName());
        todoTbl.setDescription("test description");
        Assert.assertEquals("test description", todoTbl.getDescription());
        todoTbl.setDueDate("test date");
        Assert.assertEquals("test date", todoTbl.getDueDate());
        todoTbl.setCompleted(true);
        Assert.assertTrue(todoTbl.isCompleted());
    }

}
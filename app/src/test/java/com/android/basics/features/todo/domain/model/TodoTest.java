package com.android.basics.features.todo.domain.model;

import com.android.basics.TestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TodoTest {

    private Todo todo;

    @Before
    public void setUp() {
        todo = new Todo(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestUtil.TODO_ID, todo.getTodoId());
        Assert.assertEquals(TestUtil.USER_ID, todo.getUserId());
        Assert.assertEquals(TestUtil.USER_NAME, todo.getName());
        Assert.assertEquals(TestUtil.DESCRIPTION, todo.getDescription());
        Assert.assertEquals(TestUtil.DATE, todo.getDueDate());
        Assert.assertFalse(todo.isCompleted());
    }

    @Test
    public void test_setter() {
        todo.setTodoId(2);
        Assert.assertEquals(2, todo.getTodoId());
        todo.setUserId(3);
        Assert.assertEquals(3, todo.getUserId());
        todo.setName("test name");
        Assert.assertEquals("test name", todo.getName());
        todo.setDescription("test description");
        Assert.assertEquals("test description", todo.getDescription());
        todo.setDueDate("test date");
        Assert.assertEquals("test date", todo.getDueDate());
        todo.setCompleted(true);
        Assert.assertTrue(todo.isCompleted());
    }

}
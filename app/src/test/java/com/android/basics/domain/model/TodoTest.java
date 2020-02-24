package com.android.basics.domain.model;

import com.android.basics.TestConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TodoTest {

    private Todo todo;

    @Before
    public void setUp() {
        todo = new Todo(TestConstants.TODO_ID, TestConstants.USER_ID, TestConstants.USER_NAME, TestConstants.DESCRIPTION, TestConstants.DATE, false);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestConstants.TODO_ID, todo.getTodoId());
        Assert.assertEquals(TestConstants.USER_ID, todo.getUserId());
        Assert.assertEquals(TestConstants.USER_NAME, todo.getName());
        Assert.assertEquals(TestConstants.DESCRIPTION, todo.getDescription());
        Assert.assertEquals(TestConstants.DATE, todo.getDueDate());
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
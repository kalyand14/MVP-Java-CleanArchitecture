package com.android.basics.featutes.todo.source.local.dao;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.basics.TestUtil;
import com.android.basics.features.todo.data.source.local.entity.TodoTbl;
import com.android.basics.featutes.todo.source.TodoDatabaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TodoDaoTest extends TodoDatabaseTest {

    private static final String TEST_TITLE = "This is a test title";
    private static final String TEST_CONTENT = "This is some test content";
    private static final String TEST_TIMESTAMP = "27/02/2020";


    @Test
    public void insertReadDelete() {

        // insert
        getTodoDao().insert(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);

        //read
        TodoTbl todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID);

        Assert.assertNotNull(todoTbl);

        Assert.assertEquals(TestUtil.TODO_ID, todoTbl.getTodoId());
        Assert.assertEquals(TestUtil.NAME, todoTbl.getName());
        Assert.assertEquals(TestUtil.DESCRIPTION, todoTbl.getDescription());
        Assert.assertEquals(TestUtil.DATE, todoTbl.getDueDate());

        // delete
        getTodoDao().delete(TestUtil.TODO_ID);

        // confirm the database is empty
        todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID);
        Assert.assertNull(todoTbl);
    }

    @Test
    public void insertReadUpdateReadDelete() throws Exception {
        // insert
        getTodoDao().insert(TestUtil.TODO_ID_2, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);

        //read
        TodoTbl todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID_2);

        Assert.assertNotNull(todoTbl);

        Assert.assertEquals(TestUtil.TODO_ID_2, todoTbl.getTodoId());
        Assert.assertEquals(TestUtil.NAME, todoTbl.getName());
        Assert.assertEquals(TestUtil.DESCRIPTION, todoTbl.getDescription());
        Assert.assertEquals(TestUtil.DATE, todoTbl.getDueDate());

        // update
        todoTbl.setName(TEST_TITLE);
        todoTbl.setDescription(TEST_CONTENT);
        todoTbl.setDueDate(TEST_TIMESTAMP);
        getTodoDao().update(todoTbl);

        //read
        todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID_2);
        Assert.assertEquals(TEST_TITLE, todoTbl.getName());
        Assert.assertEquals(TEST_CONTENT, todoTbl.getDescription());
        Assert.assertEquals(TEST_TIMESTAMP, todoTbl.getDueDate());

        // delete
        getTodoDao().delete(TestUtil.TODO_ID_2);

        // confirm the database is empty
        todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID_2);
        Assert.assertNull(todoTbl);
    }

}
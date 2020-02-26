package com.android.basics.data.source.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.android.basics.TestUtil;
import com.android.basics.data.source.TodoDatabaseTest;
import com.android.basics.data.source.entity.TodoTbl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TodoDaoTest extends TodoDatabaseTest {

    private static final String TEST_TITLE = "This is a test title";
    private static final String TEST_CONTENT = "This is some test content";
    private static final String TEST_TIMESTAMP = "27/02/2020";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void insertReadDelete() {

        // insert
        getTodoDao().insert(TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);

        //read
        TodoTbl todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID);

        assertNotNull(todoTbl);

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
        getTodoDao().insert(TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);

        //read
        TodoTbl todoTbl = getTodoDao().getTodo(TestUtil.TODO_ID_2);

        assertNotNull(todoTbl);

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

    /*
        Insert note with null title, throw exception
     */
   /* @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception {
        // insert
        getTodoDao().insert(TestUtil.USER_ID, null, TestUtil.DESCRIPTION, TestUtil.DATE, false);
    }*/


}
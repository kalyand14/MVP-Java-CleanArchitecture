package com.android.basics.featutes.todo.source.local;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.SingleExecutors;
import com.android.basics.features.todo.data.source.local.TodoLocalDataSource;
import com.android.basics.features.todo.data.source.local.mapper.TodoListMapper;
import com.android.basics.features.todo.data.source.local.mapper.TodoMapper;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.featutes.todo.source.TodoDatabaseTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;


public class TodoLocalDataSourceTest extends TodoDatabaseTest {

    private TodoLocalDataSource localDataSource;

    @Before
    public void setup() {
        // Make sure that we're not keeping a reference to the wrong instance.
        localDataSource = null;
        localDataSource = new TodoLocalDataSource(getTodoDao(), new SingleExecutors(), new TodoListMapper(), new TodoMapper());
    }

    @After
    public void cleanUp() {
        localDataSource = null;
    }

    @Test
    public void testPreConditions() {
        Assert.assertNotNull(localDataSource);
    }

    private void assertInsert(String todoId) {
        final Todo newTodo = new Todo(todoId, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.DATE, false);
        localDataSource.addTodo(newTodo, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                Assert.assertThat(newTodo, is(response));
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
    }

    private void assertSelect(String todoId) {
        localDataSource.getTodo(todoId, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                Assert.assertThat(todoId, is(response.getTodoId()));
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
    }

    private void assertDelete(String todoId) {
        localDataSource.deleteTodo(todoId, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                Assert.assertTrue(response);
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
    }

    @Test
    public void insertReadDelete() {
        assertInsert(TestUtil.TODO_ID);
        assertSelect(TestUtil.TODO_ID);
        assertDelete(TestUtil.TODO_ID);
    }

    @Test
    public void insertReadUpdateReadDelete() {
        assertInsert(TestUtil.TODO_ID_2);
        assertSelect(TestUtil.TODO_ID_2);
        localDataSource.getTodo(TestUtil.TODO_ID_2, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                response.setName(TestUtil.EDIT_NAME);
                response.setDescription(TestUtil.EDIT_DESCRIPTION);
                response.setDueDate(TestUtil.EDIT_DATE);

                localDataSource.editTodo(response, new Callback<Boolean>() {
                    @Override
                    public void onResponse(Boolean response) {
                        Assert.assertTrue(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        Assert.fail("Callback error");
                    }
                });
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
        localDataSource.getTodo(TestUtil.TODO_ID_2, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                Assert.assertThat(TestUtil.EDIT_NAME, is(response.getName()));
                Assert.assertThat(TestUtil.EDIT_DESCRIPTION, is(response.getDescription()));
                Assert.assertThat(TestUtil.EDIT_DATE, is(response.getDueDate()));
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
        assertDelete(TestUtil.TODO_ID_2);
    }


}

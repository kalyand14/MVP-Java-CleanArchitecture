package com.android.basics.featutes.todo.source.local;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.SingleExecutors;
import com.android.basics.features.todo.data.source.local.TodoDatabase;
import com.android.basics.features.todo.data.source.local.TodoLocalDataSource;
import com.android.basics.features.todo.data.source.local.dao.TodoDao;
import com.android.basics.features.todo.data.source.local.mapper.TodoListMapper;
import com.android.basics.features.todo.data.source.local.mapper.TodoMapper;
import com.android.basics.features.todo.domain.model.Todo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;


public class TodoLocalDataSourceTest {

    private TodoDatabase database;

    private TodoLocalDataSource localDataSource;

    private TodoListMapper todoListMapper;

    private TodoMapper todoMapper;

    @Before
    public void setup() {

        todoListMapper = new TodoListMapper();

        todoMapper = new TodoMapper();


        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodoDatabase.class)
                .allowMainThreadQueries()
                .build();

        TodoDao tasksDao = database.todoDao();

        // Make sure that we're not keeping a reference to the wrong instance.
        localDataSource = null;
        localDataSource = new TodoLocalDataSource(tasksDao, new SingleExecutors(), todoListMapper, todoMapper);
    }

    @After
    public void cleanUp() {
        database.close();
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

    @Test
    public void insert() {
        assertInsert(TestUtil.TODO_ID);
        assertSelect(TestUtil.TODO_ID);
    }

    @Test
    public void select() {
        assertInsert(TestUtil.TODO_ID);
        assertInsert(TestUtil.TODO_ID_2);
        assertSelect(TestUtil.TODO_ID);
        assertSelect(TestUtil.TODO_ID_2);
    }

    @Test
    public void delete() {
        assertInsert(TestUtil.TODO_ID);
        assertInsert(TestUtil.TODO_ID_2);

        localDataSource.deleteTodo(TestUtil.TODO_ID_2, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                Assert.assertTrue(response);
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
        localDataSource.getTodo(TestUtil.TODO_ID_2, new Callback<Todo>() {
            @Override
            public void onResponse(Todo response) {
                Assert.fail("Callback error");
            }

            @Override
            public void onError(Error todoError) {
                Assert.assertNotNull(todoError);
            }
        });
    }

    @Test
    public void update() {
        assertInsert(TestUtil.TODO_ID);
        assertInsert(TestUtil.TODO_ID_2);

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

            }
        });

    }
}

package com.android.basics.featutes.todo.source.local;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.SingleExecutors;
import com.android.basics.features.todo.data.source.local.TodoDatabase;
import com.android.basics.features.todo.data.source.local.UserLocalDataSource;
import com.android.basics.features.todo.data.source.local.dao.UserDao;
import com.android.basics.features.todo.data.source.local.mapper.UserMapper;
import com.android.basics.features.todo.domain.model.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserLocalDataSourceTest {

    private TodoDatabase database;

    private UserLocalDataSource localDataSource;

    private UserMapper userMapper;

    @Before
    public void setup() {

        userMapper = new UserMapper();

        // using an in-memory database for testing, since it doesn't survive killing the process
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodoDatabase.class)
                .allowMainThreadQueries()
                .build();

        UserDao userDao = database.userDao();

        // Make sure that we're not keeping a reference to the wrong instance.
        localDataSource = null;
        localDataSource = new UserLocalDataSource(userDao, new SingleExecutors(), userMapper);
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

    private void assertInsert(User newUser) {
        localDataSource.register(newUser, new Callback<User>() {
            @Override
            public void onResponse(User response) {
                Assert.assertThat(newUser.getUserId(), is(response.getUserId()));
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
    }

    private void assertSelect(String expectedUserId, User user) {
        localDataSource.authenticate(user, new Callback<User>() {
            @Override
            public void onResponse(User response) {
                Assert.assertThat(expectedUserId, is(response.getUserId()));
            }

            @Override
            public void onError(Error todoError) {
                Assert.fail("Callback error");
            }
        });
    }


    @Test
    public void select() {
        assertInsert(new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD));
        assertInsert(new User(TestUtil.USER_ID_2, TestUtil.USER_NAME_2, TestUtil.PASSWORD_2));
        assertSelect(TestUtil.USER_ID, new User(TestUtil.USER_NAME, TestUtil.PASSWORD));
        assertSelect(TestUtil.USER_ID_2, new User(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2));

    }
}

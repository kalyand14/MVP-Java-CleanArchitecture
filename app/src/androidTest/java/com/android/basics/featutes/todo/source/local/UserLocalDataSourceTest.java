package com.android.basics.featutes.todo.source.local;

import com.android.basics.TestUtil;
import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.SingleExecutors;
import com.android.basics.features.todo.data.source.local.UserLocalDataSource;
import com.android.basics.features.todo.data.source.local.mapper.UserMapper;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.featutes.todo.source.TodoDatabaseTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class UserLocalDataSourceTest extends TodoDatabaseTest {

    private UserLocalDataSource localDataSource;

    @Before
    public void setup() {
        // Make sure that we're not keeping a reference to the wrong instance.
        localDataSource = null;
        localDataSource = new UserLocalDataSource(getUserDao(), new SingleExecutors(), new UserMapper());
    }

    @After
    public void cleanUp() {
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

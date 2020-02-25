package com.android.basics.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.android.basics.data.source.dao.UserDao;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class TodoDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static TodoDatabase database;
    private UserDao userDao;

    @BeforeClass
    public static void initDb() throws Exception {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodoDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void testUserDao() {
        userDao = database.userDao();
        assertNull(userDao.getUser("kalyan", "password"));
    }

}
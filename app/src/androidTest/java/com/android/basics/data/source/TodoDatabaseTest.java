package com.android.basics.data.source;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.data.source.dao.UserDao;

import org.junit.After;
import org.junit.BeforeClass;

public class TodoDatabaseTest {

    private static TodoDatabase database;

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

    public UserDao getUserDao() {
        return database.userDao();
    }

    public TodoDao getTodoDao() {
        return database.todoDao();
    }
}
package com.android.basics.features.todo.data.source;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.android.basics.features.todo.data.source.local.TodoDatabase;
import com.android.basics.features.todo.data.source.local.dao.TodoDao;
import com.android.basics.features.todo.data.source.local.dao.UserDao;

import org.junit.AfterClass;
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

    @AfterClass
    public static void closeDb() throws Exception {
        database.close();
    }

    public UserDao getUserDao() {
        return database.userDao();
    }

    public TodoDao getTodoDao() {
        return database.todoDao();
    }
}
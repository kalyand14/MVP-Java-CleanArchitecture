package com.android.basics.data.source;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.data.source.dao.UserDao;
import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.data.source.entity.UserTbl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TodoTbl.class, UserTbl.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    public abstract UserDao userDao();

    private static volatile TodoDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDatabase.class, "todo_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

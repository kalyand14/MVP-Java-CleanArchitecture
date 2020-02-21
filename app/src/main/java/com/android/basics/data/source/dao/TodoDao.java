package com.android.basics.data.source.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.android.basics.data.source.entity.TodoTbl;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("INSERT INTO todo (userId, name, description, dueDate, isCompleted) VALUES (:userId, :name, :description, :dueDate, :isCompleted)")
    long insert(int userId, String name, String description, String dueDate, boolean isCompleted);

    @Query("DELETE FROM todo WHERE todoId =:todoId")
    int delete(int todoId);

    @Update
    int update(TodoTbl todoTbl);

    @Query("SELECT * from todo WHERE userId =:userId ORDER BY todoId DESC")
    List<TodoTbl> getAllTodo(int userId);

    @Query("SELECT * from todo WHERE todoId =:todoId")
    TodoTbl getTodo(int todoId);
}

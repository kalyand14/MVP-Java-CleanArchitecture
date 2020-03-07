package com.android.basics.features.todo.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.android.basics.features.todo.data.source.local.entity.TodoTbl;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("INSERT INTO todo (todoId, userId, name, description, dueDate, isCompleted) VALUES (:todoId, :userId, :name, :description, :dueDate, :isCompleted)")
    long insert(String todoId, String userId, String name, String description, String dueDate, boolean isCompleted);

    @Query("DELETE FROM todo WHERE todoId =:todoId")
    int delete(String todoId);

    @Update
    int update(TodoTbl todoTbl);

    @Query("SELECT * from todo WHERE userId =:userId ORDER BY todoId DESC")
    List<TodoTbl> getAllTodo(String userId);

    @Query("SELECT * from todo WHERE todoId =:todoId")
    TodoTbl getTodo(String todoId);

    @Query("DELETE FROM todo")
    void deleteAllTodo();
}

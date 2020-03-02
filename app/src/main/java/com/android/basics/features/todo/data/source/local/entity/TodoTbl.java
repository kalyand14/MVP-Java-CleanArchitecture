package com.android.basics.features.todo.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "todo")
public class TodoTbl {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "todoId")
    private String todoId;

    @NonNull
    @ColumnInfo(name = "userId")
    private String userId;

    @Nullable
    @ColumnInfo(name = "name")
    private String name;

    @Nullable
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "dueDate")
    private String dueDate;

    @ColumnInfo(name = "isCompleted")
    private Boolean isCompleted;

    public TodoTbl(
            @NonNull String userId,
            @Nullable String name,
            @Nullable String description,
            @NonNull String dueDate,
            boolean isCompleted) {
        this.todoId = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setDueDate(@NonNull String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @NonNull
    public String getTodoId() {
        return todoId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getDueDate() {
        return dueDate;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }
}

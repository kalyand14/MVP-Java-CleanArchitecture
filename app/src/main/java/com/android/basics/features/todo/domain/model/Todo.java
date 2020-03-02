package com.android.basics.features.todo.domain.model;

public class Todo {

    private String todoId;
    private String userId;
    private String name;
    private String description;
    private String dueDate;
    private boolean isCompleted;

    public Todo(String todoId, String userId, String name, String description, String dueDate, boolean isCompleted) {
        this.todoId = todoId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public Todo(String userId, String name, String description, String dueDate) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }


}

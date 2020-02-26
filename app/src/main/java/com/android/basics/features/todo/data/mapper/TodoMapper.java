package com.android.basics.features.todo.data.mapper;

import com.android.basics.core.domain.Mapper;
import com.android.basics.features.todo.data.source.entity.TodoTbl;
import com.android.basics.features.todo.domain.model.Todo;

public class TodoMapper implements Mapper<TodoTbl, Todo> {
    @Override
    public Todo convert(TodoTbl fromObj) {
        if (fromObj == null) {
            return null;
        } else {
            return new Todo(fromObj.getTodoId(), fromObj.getUserId(), fromObj.getName(), fromObj.getDescription(), fromObj.getDueDate(), fromObj.isCompleted());
        }
    }

}

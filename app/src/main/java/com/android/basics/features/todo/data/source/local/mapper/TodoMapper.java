package com.android.basics.features.todo.data.source.local.mapper;

import com.android.basics.core.Mapper;
import com.android.basics.features.todo.data.source.local.entity.TodoTbl;
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

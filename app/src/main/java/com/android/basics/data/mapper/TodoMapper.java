package com.android.basics.data.mapper;

import com.android.basics.core.Mapper;
import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.domain.model.Todo;

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

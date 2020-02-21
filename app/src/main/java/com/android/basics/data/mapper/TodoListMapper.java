package com.android.basics.data.mapper;

import com.android.basics.core.Mapper;
import com.android.basics.data.source.entity.TodoTbl;
import com.android.basics.domain.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoListMapper implements Mapper<List<TodoTbl>, List<Todo>> {

    @Override
    public List<Todo> convert(List<TodoTbl> fromObj) {

        if (fromObj == null) {
            return null;
        } else {
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < fromObj.size(); i++) {
                todoList.add(getTodo(fromObj.get(i)));
            }
            return todoList;
        }
    }

    private Todo getTodo(TodoTbl tbl) {
        return new Todo(tbl.getTodoId(), tbl.getUserId(), tbl.getName(), tbl.getDescription(), tbl.getDueDate(), tbl.getCompleted());
    }
}

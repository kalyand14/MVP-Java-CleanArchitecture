package com.android.basics;

import com.android.basics.core.Error;
import com.android.basics.features.todo.domain.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestUtil {


    public static final String ERROR_CODE = "00002";
    public static final String ERROR_MESSAGE = "No data available";
    public static final String EDIT_ERROR_MESSAGE = "Update failed";
    public static final String INSERT_ERROR_MESSAGE = "insert failed";
    public static final String DELETE_ERROR_MESSAGE = "Delete failed";


    public static final String USER_ID = "1";
    public static final String USER_ID_2 = "2";

    public static final String TODO_ID = "1";
    public static final String TODO_ID_2 = "2";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    public static final String EDIT_NAME = "this is edited name";
    public static final String EDIT_DESCRIPTION = "this is edited description";
    public static final String EDIT_DATE = "this is edited date";

    public static final String USER_NAME = "Kalyan";
    public static final String PASSWORD = "password";

    public static final String USER_NAME_2 = "Muthu";
    public static final String PASSWORD_2 = "mylove";

    public static final Error ERROR = new Error(new Exception());

    public static List<Todo> buildFakeTodList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.EDIT_DATE, false));
        todoList.add(new Todo(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.EDIT_DATE, false));
        return todoList;
    }

    public static Integer[] getDateString() {
        Calendar calendar = Calendar.getInstance();
        Integer[] dateString = new Integer[3];
        dateString[0] = calendar.get(Calendar.YEAR);
        dateString[1] = calendar.get(Calendar.MONTH) + 1;
        dateString[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return dateString;
    }

}

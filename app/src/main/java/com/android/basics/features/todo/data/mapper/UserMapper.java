package com.android.basics.features.todo.data.mapper;

import com.android.basics.core.domain.Mapper;
import com.android.basics.features.todo.data.source.entity.UserTbl;
import com.android.basics.features.todo.domain.model.User;

public class UserMapper implements Mapper<UserTbl, User> {
    @Override
    public User convert(UserTbl fromObj) {
        if (fromObj == null) {
            return null;
        } else {
            return new User(fromObj.getUserId(), fromObj.getUserName(), fromObj.getPassword());
        }
    }
}

package com.android.basics.features.todo.data.source.local.mapper;

import com.android.basics.core.Mapper;
import com.android.basics.features.todo.data.source.local.entity.UserTbl;
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

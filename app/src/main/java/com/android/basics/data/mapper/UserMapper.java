package com.android.basics.data.mapper;

import com.android.basics.core.Mapper;
import com.android.basics.data.source.entity.UserTbl;
import com.android.basics.domain.model.User;

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

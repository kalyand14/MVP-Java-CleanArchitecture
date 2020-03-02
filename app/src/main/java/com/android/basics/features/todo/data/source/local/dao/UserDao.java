package com.android.basics.features.todo.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.android.basics.features.todo.data.source.local.entity.UserTbl;

@Dao
public interface UserDao {

    @Query("INSERT INTO user (userId, userName, password) VALUES (:userId, :userName, :password)")
    void insert(String userId, String userName, String password);

    @Query("SELECT * from user WHERE userName =:userName AND password=:passWord")
    UserTbl getUser(String userName, String passWord);
}

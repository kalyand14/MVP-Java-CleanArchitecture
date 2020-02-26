package com.android.basics.features.todo.data.source.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.android.basics.features.todo.data.source.entity.UserTbl;

@Dao
public interface UserDao {

    @Query("INSERT INTO user (userName, password) VALUES (:userName, :password)")
    void insert(String userName, String password);

    @Query("SELECT * from user WHERE userName =:userName AND password=:passWord")
    UserTbl getUser(String userName, String passWord);
}

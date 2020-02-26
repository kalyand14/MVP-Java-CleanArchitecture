package com.android.basics.features.todo.data.source.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserTbl {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int userId;
    private String userName;
    private String password;

    public UserTbl(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}

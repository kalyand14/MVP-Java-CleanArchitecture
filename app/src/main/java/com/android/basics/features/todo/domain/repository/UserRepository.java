package com.android.basics.features.todo.domain.repository;

import com.android.basics.core.domain.Callback;
import com.android.basics.features.todo.domain.model.User;

public interface UserRepository {
    void authenticate(String userName, String password, Callback<User> callback);

    void register(String userName, String password, Callback<User> callback);

}

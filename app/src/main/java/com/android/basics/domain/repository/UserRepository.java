package com.android.basics.domain.repository;

import com.android.basics.core.Callback;
import com.android.basics.domain.model.User;

public interface UserRepository {
    void authenticate(String userName, String password, Callback<User> callback);

    void register(String userName, String password, Callback<User> callback);

}

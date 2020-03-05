package com.android.basics.features.todo.data.source;

import com.android.basics.core.Callback;
import com.android.basics.features.todo.domain.model.User;

public interface UserDataSource {

    void authenticate(User user, Callback<User> callback);

    void register(User user, Callback<User> callback);

    void deleteAllUsers();
}

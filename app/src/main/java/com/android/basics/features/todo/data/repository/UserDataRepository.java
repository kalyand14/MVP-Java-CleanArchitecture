package com.android.basics.features.todo.data.repository;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.features.todo.data.source.UserDataSource;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class UserDataRepository implements UserRepository {

    private static UserDataRepository INSTANCE = null;

    private final UserDataSource userLocalDataSource;
    private final UserDataSource userRemoteLocalSource;


    private UserDataRepository(UserDataSource userLocalDataSource, UserDataSource userRemoteLocalSource) {
        this.userLocalDataSource = userLocalDataSource;
        this.userRemoteLocalSource = userRemoteLocalSource;
    }

    public static UserDataRepository getInstance(UserDataSource userLocalDataSource,
                                                 UserDataSource userRemoteLocalSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository(userLocalDataSource, userRemoteLocalSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void authenticate(User user, Callback<User> callback) {
        userLocalDataSource.authenticate(user, new Callback<User>() {
            @Override
            public void onResponse(User response) {
                callback.onResponse(response);
            }

            @Override
            public void onError(Error todoError) {

                userRemoteLocalSource.authenticate(user, new Callback<User>() {
                    @Override
                    public void onResponse(User response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }
        });
    }

    @Override
    public void register(User user, Callback<User> callback) {
        userRemoteLocalSource.register(user, new Callback<User>() {
            @Override
            public void onResponse(User response) {
                userLocalDataSource.register(response, new Callback<User>() {
                    @Override
                    public void onResponse(User response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onError(Error todoError) {
                        callback.onError(todoError);
                    }
                });
            }

            @Override
            public void onError(Error todoError) {
                callback.onError(todoError);
            }
        });
    }

    @Override
    public void deleteAllUsers() {
        userRemoteLocalSource.deleteAllUsers();
        userLocalDataSource.deleteAllUsers();
    }


}

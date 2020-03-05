package com.android.basics.features.todo.data.source.local;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.utils.AppExecutors;
import com.android.basics.features.todo.data.source.UserDataSource;
import com.android.basics.features.todo.data.source.local.dao.UserDao;
import com.android.basics.features.todo.data.source.local.mapper.UserMapper;
import com.android.basics.features.todo.domain.model.User;

public class UserLocalDataSource implements UserDataSource {

    private final UserDao userDao;
    private final AppExecutors appExecutors;
    private final UserMapper userMapper;

    private final Error noDataAvailableError = new Error(new Exception("No data available"));
    private final Error operationFailed = new Error(new Exception("Operation failed"));

    public UserLocalDataSource(
            @NonNull UserDao userDao,
            @NonNull AppExecutors appExecutors,
            @NonNull UserMapper userMapper
    ) {
        this.userDao = userDao;
        this.appExecutors = appExecutors;
        this.userMapper = userMapper;
    }


    @Override
    public void authenticate(User in, Callback<User> callback) {
        appExecutors.diskIO().execute(() -> {
            User user = userMapper.convert(userDao.getUser(in.getUserName(), in.getPassword()));
            appExecutors.mainThread().execute(() -> {
                if (user != null) {
                    callback.onResponse(user);
                } else {
                    callback.onError(noDataAvailableError);
                }
            });
        });
    }

    @Override
    public void register(User in, Callback<User> callback) {
        appExecutors.diskIO().execute(() -> {
            try {
                userDao.insert(in.getUserId(), in.getUserName(), in.getPassword());
                User user = userMapper.convert(userDao.getUser(in.getUserName(), in.getPassword()));
                appExecutors.mainThread().execute(() -> {
                    if (user != null) {
                        callback.onResponse(user);
                    } else {
                        callback.onError(noDataAvailableError);
                    }
                });

            } catch (Exception e) {
                appExecutors.mainThread().execute(() -> {
                    callback.onError(operationFailed);
                });
            }

        });
    }

    @Override
    public void deleteAllUsers() {
        appExecutors.diskIO().execute(userDao::deleteAllUsers);
    }

}

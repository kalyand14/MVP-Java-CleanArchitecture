package com.android.basics.features.todo.data.repository;

import com.android.basics.core.domain.Callback;
import com.android.basics.features.todo.data.component.DaoCallback;
import com.android.basics.features.todo.data.component.DaoExecutor;
import com.android.basics.features.todo.data.mapper.UserMapper;
import com.android.basics.features.todo.data.source.dao.UserDao;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class UserDataRepository implements UserRepository {

    private UserDao userDao;
    private UserMapper userMapper;
    private DaoExecutor daoExecutor;

    public UserDataRepository(DaoExecutor daoExecutor, UserDao userDao, UserMapper userMapper) {
        this.daoExecutor = daoExecutor;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public void authenticate(String userName, String password, Callback<User> callback) {
        DaoCallback<User> daoCallback = new DaoCallback<User>() {
            @Override
            public User doAsync() {
                return userMapper.convert(userDao.getUser(userName, password));
            }

            @Override
            public void onComplete(User response) {
                if (response != null) {
                    callback.onResponse(response);
                } else {
                    callback.onError("00002", "No data available");
                }
            }
        };
        daoExecutor.start(daoCallback);
    }

    @Override
    public void register(String userName, String password, Callback<User> callback) {
        DaoCallback<User> daoCallback = new DaoCallback<User>() {
            @Override
            public User doAsync() {
                userDao.insert(userName, password);
                return userMapper.convert(userDao.getUser(userName, password));
            }

            @Override
            public void onComplete(User response) {
                if (response != null) {
                    callback.onResponse(response);
                } else {
                    callback.onError("00002", "No data available");
                }
            }
        };
        daoExecutor.start(daoCallback);
    }
}

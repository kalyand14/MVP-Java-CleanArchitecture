package com.android.basics.features.todo.data.source.remote;

import android.os.Handler;
import android.os.Looper;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.features.todo.data.source.UserDataSource;
import com.android.basics.features.todo.domain.model.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class UserRemoteLocalSource implements UserDataSource {

    private final Error noDataAvailableError = new Error(new Exception("No data available"));

    private final int SERVICE_LATENCY_IN_MILLIS = 5000;
    private final Map<String, User> USER_SERVICE_DATA;

    public UserRemoteLocalSource() {
        this.USER_SERVICE_DATA = new LinkedHashMap<>(2);
    }

    @Override
    public void authenticate(User user, Callback<User> callback) {
        final User loggedInUser = USER_SERVICE_DATA.get(user.getUserId());
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (loggedInUser != null) {
                callback.onResponse(loggedInUser);
            } else {
                callback.onError(noDataAvailableError);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void register(User user, Callback<User> callback) {
        User registeredUser = new User(UUID.randomUUID().toString(), user.getUserName(), user.getPassword());
        USER_SERVICE_DATA.put(registeredUser.getUserId(), registeredUser);
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> callback.onResponse(registeredUser),
                SERVICE_LATENCY_IN_MILLIS);


    }
}

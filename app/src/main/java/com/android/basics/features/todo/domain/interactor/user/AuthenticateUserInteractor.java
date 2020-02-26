package com.android.basics.features.todo.domain.interactor.user;

import com.android.basics.core.domain.Callback;
import com.android.basics.core.domain.UseCase;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class AuthenticateUserInteractor extends UseCase<AuthenticateUserInteractor.Params, User> {

    private UserRepository userRepository;

    public AuthenticateUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeTask(Params params, final Callback<User> callback) {
        this.userRepository.authenticate(params.userName, params.password, new Callback<User>() {
            @Override
            public void onResponse(User response) {
                if (!isDisposed()) {
                    callback.onResponse(response);
                }
            }

            @Override
            public void onError(String errorcode, String errorResponse) {
                if (!isDisposed()) {
                    callback.onError(errorcode, errorResponse);
                }
            }
        });

    }

    public static class Params {
        private String userName;
        private String password;

        private Params(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public static Params forUser(String userName, String password) {
            return new Params(userName, password);
        }
    }
}

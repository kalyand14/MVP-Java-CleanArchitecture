package com.android.basics.domain.interactor.user;

import com.android.basics.core.Callback;
import com.android.basics.core.mvp.UseCase;
import com.android.basics.domain.model.User;
import com.android.basics.domain.repository.UserRepository;

public class RegisterUserInteractor extends UseCase<RegisterUserInteractor.Params, User> {

    private UserRepository userRepository;

    public RegisterUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeTask(Params param, final Callback<User> callback) {
        this.userRepository.register(param.userName, param.password, new Callback<User>() {
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

    public static final class Params {
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

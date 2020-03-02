package com.android.basics.features.todo.domain.interactor.user;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class AuthenticateUserInteractor extends UseCase<AuthenticateUserInteractor.Request, AuthenticateUserInteractor.Response> {

    private UserRepository userRepository;

    public AuthenticateUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        this.userRepository.authenticate(params.getUser(), new Callback<User>() {
            @Override
            public void onResponse(User response) {
                if (isNotDisposed()) {
                    getUseCaseCallback().onSuccess(new Response(response));
                }
            }

            @Override
            public void onError(Error todoError) {
                if (isNotDisposed()) {
                    getUseCaseCallback().onError(todoError);
                }
            }
        });
    }

    public static final class Request implements UseCase.Request {
        private final String userName;
        private final String password;

        public Request(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public User getUser() {
            return new User(userName, password);
        }

    }

    public static final class Response implements UseCase.Response {

        private final User user;

        public Response(@NonNull User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}

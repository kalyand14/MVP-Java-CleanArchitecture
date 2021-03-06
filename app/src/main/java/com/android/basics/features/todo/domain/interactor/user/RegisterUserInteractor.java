package com.android.basics.features.todo.domain.interactor.user;

import androidx.annotation.NonNull;

import com.android.basics.core.Callback;
import com.android.basics.core.Error;
import com.android.basics.core.UseCase;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class RegisterUserInteractor extends UseCase<RegisterUserInteractor.Request, RegisterUserInteractor.Response> {

    private UserRepository userRepository;

    public RegisterUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeUseCase(Request params) {
        this.userRepository.register(params.getUser(), new Callback<User>() {
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

    public static class Request implements UseCase.Request {

        private final String userName;
        private final String password;

        public Request(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public User getUser() {
            return new User(userName, password);
        }
    }

    public static class Response implements UseCase.Response {

        private final User user;

        public Response(@NonNull User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}

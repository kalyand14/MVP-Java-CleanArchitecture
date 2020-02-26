package com.android.basics.features.todo.presentation;

import android.content.Intent;

import com.android.basics.core.presenetation.navigation.Navigator;
import com.android.basics.features.todo.presentation.home.HomeActivity;
import com.android.basics.features.todo.presentation.home.HomeScreenContract;
import com.android.basics.features.todo.presentation.login.LoginActivity;
import com.android.basics.features.todo.presentation.login.LoginContract;
import com.android.basics.features.todo.presentation.registration.RegisterUserActivity;
import com.android.basics.features.todo.presentation.registration.RegisterUserContract;
import com.android.basics.features.todo.presentation.splash.SplashContract;
import com.android.basics.features.todo.presentation.todo.add.AddTodoActivity;
import com.android.basics.features.todo.presentation.todo.add.AddTodoContract;
import com.android.basics.features.todo.presentation.todo.edit.EditTodoActivity;
import com.android.basics.features.todo.presentation.todo.edit.EditTodoContract;

public class TodoNavigator implements SplashContract.Navigator,
        LoginContract.Navigator,
        RegisterUserContract.Navigator,
        HomeScreenContract.Navigator,
        AddTodoContract.Navigator,
        EditTodoContract.Navigator {
    private Navigator navigator;

    public TodoNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void goToEditTodoScreen() {
        Intent intent = navigator.createIntent(EditTodoActivity.class);
        navigator.launchActivity(intent);
    }

    @Override
    public void gotoAddTodoScreen() {
        Intent intent = navigator.createIntent(AddTodoActivity.class);
        navigator.launchActivity(intent);
    }

    @Override
    public void goToLoginScreen() {



        Intent intent = navigator.createIntent(LoginActivity.class);
        navigator.launchActivity(intent);
        navigator.finishActivity();
    }

    @Override
    public void goToRegisterScreen() {
        Intent intent = navigator.createIntent(RegisterUserActivity.class);
        navigator.launchActivity(intent);
    }

    @Override
    public void goToHomeScreen() {
        Intent intent = navigator.createIntent(HomeActivity.class);
        navigator.launchActivity(intent);
        navigator.finishActivity();
    }


}

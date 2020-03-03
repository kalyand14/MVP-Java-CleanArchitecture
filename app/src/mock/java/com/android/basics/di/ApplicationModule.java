package com.android.basics.di;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import com.android.basics.core.navigation.NativeBundleFactory;
import com.android.basics.core.navigation.NativeIntentFactory;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.core.utils.AppExecutors;
import com.android.basics.features.todo.data.repository.TodoDataRepository;
import com.android.basics.features.todo.data.repository.UserDataRepository;
import com.android.basics.features.todo.data.source.TodoDataSource;
import com.android.basics.features.todo.data.source.UserDataSource;
import com.android.basics.features.todo.data.source.local.TodoDatabase;
import com.android.basics.features.todo.data.source.local.TodoLocalDataSource;
import com.android.basics.features.todo.data.source.local.UserLocalDataSource;
import com.android.basics.features.todo.data.source.local.dao.TodoDao;
import com.android.basics.features.todo.data.source.local.dao.UserDao;
import com.android.basics.features.todo.data.source.local.mapper.TodoListMapper;
import com.android.basics.features.todo.data.source.local.mapper.TodoMapper;
import com.android.basics.features.todo.data.source.local.mapper.UserMapper;
import com.android.basics.features.todo.data.source.remote.FakeTodoRemoteDataSource;
import com.android.basics.features.todo.data.source.remote.FakeUserRemoteLocalSource;
import com.android.basics.features.todo.domain.repository.TodoRepository;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    public IntentFactory provideIntentFactory() {
        return new NativeIntentFactory();
    }

    public BundleFactory provideBundleFactory() {
        return new NativeBundleFactory();
    }

    public Navigator provideNavigator(AppCompatActivity activity, IntentFactory intentFactory, BundleFactory bundleFactory) {
        return new Navigator(activity, intentFactory, bundleFactory);
    }

    public UserRepository provideUserRepository(UserDataSource localDataSource, UserDataSource remoteDataSource) {
        return UserDataRepository.getInstance(localDataSource, remoteDataSource);
    }

    public TodoRepository provideTodoRepository(TodoDataSource localDataSource, TodoDataSource remoteDataSource) {
        return TodoDataRepository.getInstance(localDataSource, remoteDataSource);
    }

    public UserDataSource provideUserLocalDataSource() {
        return new UserLocalDataSource(provideUserDao(provideTodoDatabase(application)), provideAppExecutors(), provideUserMapper());
    }

    //Provided a fake remote source here
    public UserDataSource provideUserRemoteDataSource() {
        return new FakeUserRemoteLocalSource();
    }

    public TodoDataSource provideTodoLocalDataSource() {

        return new TodoLocalDataSource(provideTodoDao(provideTodoDatabase(application)), provideAppExecutors(), provideTodoListMapper(), provideTodoMapper());
    }

    //Provided a fake remote source here
    public TodoDataSource provideTodoRemoteDataSource() {
        return new FakeTodoRemoteDataSource();
    }

    public AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    public TodoDatabase provideTodoDatabase(Application application) {
        return TodoDatabase.getDatabase(application.getApplicationContext());
    }

    public UserDao provideUserDao(TodoDatabase database) {
        return database.userDao();
    }

    public TodoDao provideTodoDao(TodoDatabase database) {
        return database.todoDao();
    }

    public UserMapper provideUserMapper() {
        return new UserMapper();
    }

    public TodoListMapper provideTodoListMapper() {
        return new TodoListMapper();
    }

    public TodoMapper provideTodoMapper() {
        return new TodoMapper();
    }

    public Application getApplication() {
        return application;
    }
}

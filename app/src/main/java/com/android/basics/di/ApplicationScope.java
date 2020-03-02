package com.android.basics.di;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.core.di.InstanceContainer;
import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import  com.android.basics.core.navigation.Navigator;
import com.android.basics.features.todo.domain.repository.TodoRepository;
import com.android.basics.features.todo.domain.repository.UserRepository;

public class ApplicationScope {

    private InstanceContainer container = new InstanceContainer();
    private static ApplicationScope instance = null;


    private ApplicationModule module;

    public void setModule(ApplicationModule module) {
        this.module = module;
    }

    private ApplicationScope() {
    }

    public static ApplicationScope getInstance() {
        if (instance == null) {
            instance = new ApplicationScope();
        }
        return instance;
    }

    public IntentFactory intentFactory() {
        if (!container.has(IntentFactory.class)) {
            container.register(IntentFactory.class, module.provideIntentFactory());
        }
        return container.get(IntentFactory.class);
    }

    public BundleFactory bundleFactory() {
        if (!container.has(BundleFactory.class)) {
            container.register(BundleFactory.class, module.provideBundleFactory());
        }
        return container.get(BundleFactory.class);
    }

    public Navigator navigator(AppCompatActivity activity) {
        return module.provideNavigator(activity, intentFactory(), bundleFactory());
    }

    public UserRepository userRepository() {
        if (!container.has(UserRepository.class)) {
            container.register(UserRepository.class, module.provideUserRepository(module.provideUserLocalDataSource(), module.provideUserRemoteDataSource()));
        }
        return container.get(UserRepository.class);
    }

    public TodoRepository todoRepository() {
        if (!container.has(TodoRepository.class)) {
            container.register(TodoRepository.class, module.provideTodoRepository(module.provideTodoLocalDataSource(), module.provideTodoRemoteDataSource()));
        }
        return container.get(TodoRepository.class);
    }


    public InstanceContainer getContainer() {
        return container;
    }

    @VisibleForTesting
    public void setContainer(InstanceContainer container) {
        this.container = container;
    }


}

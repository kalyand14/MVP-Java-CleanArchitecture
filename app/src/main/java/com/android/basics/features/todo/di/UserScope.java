package com.android.basics.features.todo.di;

import androidx.annotation.VisibleForTesting;

import com.android.basics.core.di.BaseScope;
import com.android.basics.core.di.InstanceContainer;
import com.android.basics.di.ApplicationScope;

public class UserScope implements BaseScope {
    private InstanceContainer container = new InstanceContainer();

    private UserScope() {
    }

    public static UserScope getInstance() {
        if (!ApplicationScope.getInstance().getContainer().has(UserScope.class)) {
            ApplicationScope.getInstance().getContainer().register(UserScope.class, new UserScope());
        }
        return ApplicationScope.getInstance().getContainer().get(UserScope.class);
    }

    @Override
    public void end() {
        container.end();
    }

    public InstanceContainer getContainer() {
        return container;
    }

    @VisibleForTesting
    public void setContainer(InstanceContainer container) {
        this.container = container;
    }

}

package com.android.basics.di;

import com.android.basics.core.di.BaseScope;
import com.android.basics.core.di.InstanceContainer;

public class TodoScope implements BaseScope {
    private final InstanceContainer container = new InstanceContainer();

    private TodoScope() {
    }

    public static TodoScope getInstance() {
        if (!UserScope.getInstance().getContainer().has(TodoScope.class)) {
            UserScope.getInstance().getContainer().register(TodoScope.class, new TodoScope());
        }
        return UserScope.getInstance().getContainer().get(TodoScope.class);
    }

    public InstanceContainer getContainer() {
        return container;
    }

    @Override
    public void end() {
        container.end();
    }
}

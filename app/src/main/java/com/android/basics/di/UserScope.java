package com.android.basics.di;

import com.android.basics.core.di.BaseScope;
import com.android.basics.core.di.InstanceContainer;

public class UserScope implements BaseScope {
    private final InstanceContainer container = new InstanceContainer();

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


}

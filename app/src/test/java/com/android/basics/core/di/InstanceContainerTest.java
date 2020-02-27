package com.android.basics.core.di;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InstanceContainerTest {

    private InstanceContainer instanceContainer;

    @Before
    public void setUp() throws Exception {
        instanceContainer = new InstanceContainer();
    }

    @Test
    public void test_insert_read_delete() {
        instanceContainer.register(Dummy.class, new Dummy());
        Assert.assertTrue(instanceContainer.has(Dummy.class));
        Assert.assertNotNull(instanceContainer.get(Dummy.class));
        instanceContainer.destroy(Dummy.class);
        Assert.assertNull(instanceContainer.get(Dummy.class));

    }

    @Test
    public void test_scope_end() {
        instanceContainer.register(Dummy.class, new Dummy());
        Assert.assertTrue(instanceContainer.has(Dummy.class));
        Assert.assertNotNull(instanceContainer.get(Dummy.class));
        instanceContainer.end();
        Assert.assertNull(instanceContainer.get(Dummy.class));

    }
}
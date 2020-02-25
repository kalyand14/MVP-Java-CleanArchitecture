package com.android.basics.domain.model;

import com.android.basics.TestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestUtil.USER_ID, user.getUserId());
        Assert.assertEquals(TestUtil.USER_NAME, user.getUserName());
        Assert.assertEquals(TestUtil.PASSWORD, user.getPassword());
    }

    @Test
    public void test_setter() {
        user.setUserId(3);
        Assert.assertEquals(3, user.getUserId());
        user.setUserName("test name");
        Assert.assertEquals("test name", user.getUserName());
        user.setPassword("test description");
        Assert.assertEquals("test description", user.getPassword());
    }

}
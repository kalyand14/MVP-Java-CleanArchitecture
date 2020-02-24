package com.android.basics.domain.model;

import com.android.basics.TestConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User(TestConstants.USER_ID, TestConstants.USER_NAME, TestConstants.PASSWORD);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestConstants.USER_ID, user.getUserId());
        Assert.assertEquals(TestConstants.USER_NAME, user.getUserName());
        Assert.assertEquals(TestConstants.PASSWORD, user.getPassword());
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
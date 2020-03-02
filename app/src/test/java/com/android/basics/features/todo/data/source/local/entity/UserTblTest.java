package com.android.basics.features.todo.data.source.local.entity;

import com.android.basics.TestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTblTest {

    private UserTbl userTbl;

    @Before
    public void setUp() {
        userTbl = new UserTbl(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);
    }

    @Test
    public void test_getter() {
        Assert.assertEquals(TestUtil.USER_ID, userTbl.getUserId());
        Assert.assertEquals(TestUtil.USER_NAME, userTbl.getUserName());
        Assert.assertEquals(TestUtil.PASSWORD, userTbl.getPassword());
    }

}
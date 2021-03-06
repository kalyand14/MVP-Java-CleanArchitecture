package com.android.basics.featutes.todo.source.local.dao;

import com.android.basics.TestUtil;
import com.android.basics.features.todo.data.source.local.entity.UserTbl;
import com.android.basics.featutes.todo.source.TodoDatabaseTest;

import org.junit.Assert;
import org.junit.Test;


public class UserDaoTest extends TodoDatabaseTest {

    @Test
    public void insertRead() {

        // insert
        getUserDao().insert(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD);

        //read
        UserTbl userTbl = getUserDao().getUser(TestUtil.USER_NAME, TestUtil.PASSWORD);
        Assert.assertNotNull(userTbl);

        Assert.assertEquals(TestUtil.USER_ID, userTbl.getUserId());
        Assert.assertEquals(TestUtil.USER_NAME, userTbl.getUserName());
        Assert.assertEquals(TestUtil.PASSWORD, userTbl.getPassword());


    }

}
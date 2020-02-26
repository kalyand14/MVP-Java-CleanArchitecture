package com.android.basics.data.source.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.android.basics.TestUtil;
import com.android.basics.data.source.TodoDatabaseTest;
import com.android.basics.data.source.entity.UserTbl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserDaoTest extends TodoDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void insertRead() {

        // insert
        getUserDao().insert(TestUtil.USER_NAME, TestUtil.PASSWORD);

        //read
        UserTbl userTbl = getUserDao().getUser(TestUtil.USER_NAME, TestUtil.PASSWORD);
        assertNotNull(userTbl);

        Assert.assertEquals(TestUtil.USER_ID, userTbl.getUserId());
        Assert.assertEquals(TestUtil.USER_NAME, userTbl.getUserName());
        Assert.assertEquals(TestUtil.PASSWORD, userTbl.getPassword());


    }

}
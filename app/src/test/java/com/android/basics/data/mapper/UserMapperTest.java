package com.android.basics.data.mapper;

import com.android.basics.TestUtil;
import com.android.basics.domain.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserMapperTest {

    private UserMapper userMapper;

    @Before
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void test_convert_success() {
        User user = userMapper.convert(TestUtil.buildMockUserTbl(TestUtil.USER_ID, TestUtil.NAME, TestUtil.PASSWORD));
        Assert.assertEquals(TestUtil.USER_ID, user.getUserId());
        Assert.assertEquals(TestUtil.NAME, user.getUserName());
        Assert.assertEquals(TestUtil.PASSWORD, user.getPassword());
    }

    @Test
    public void test_convert_error() {
        User user = userMapper.convert(null);
        Assert.assertNull(user);

    }
}
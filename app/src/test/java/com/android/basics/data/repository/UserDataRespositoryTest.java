package com.android.basics.data.repository;

import com.android.basics.TestConstants;
import com.android.basics.core.Callback;
import com.android.basics.data.component.DaoCallback;
import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.UserMapper;
import com.android.basics.data.source.dao.UserDao;
import com.android.basics.data.source.entity.UserTbl;
import com.android.basics.domain.model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDataRespositoryTest {

    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private DaoExecutor daoExecutor;
    @InjectMocks
    private UserDataRespository userDataRespository;
    @Captor
    private ArgumentCaptor<DaoCallback<User>> callbackArgumentCaptor;
    @Mock
    private Callback<User> userCallback;
    @Mock
    private UserTbl userTbl;
    @Mock
    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void tes_register_doAsync_success() {
        when(userDao.getUser(TestConstants.USER_NAME, TestConstants.PASSWORD)).thenReturn(userTbl);
        userDataRespository.register(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(userDao).insert(TestConstants.USER_NAME, TestConstants.PASSWORD);
        verify(userMapper).convert(userTbl);
    }

    @Test
    public void tes_register_onComplete_success() {
        userDataRespository.register(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(user);
        verify(userCallback).onResponse(user);
    }

    @Test
    public void tes_register_onComplete_error() {
        userDataRespository.register(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(userCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

    @Test
    public void tes_authenticate_doAsync_success() {
        when(userDao.getUser(TestConstants.USER_NAME, TestConstants.PASSWORD)).thenReturn(userTbl);
        userDataRespository.authenticate(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(userMapper).convert(userTbl);
    }

    @Test
    public void tes_authenticate_onComplete_success() {
        userDataRespository.authenticate(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(user);
        verify(userCallback).onResponse(user);
    }

    @Test
    public void tes_authenticate_onComplete_error() {
        userDataRespository.authenticate(TestConstants.USER_NAME, TestConstants.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(userCallback).onError(TestConstants.ERROR_CODE, TestConstants.ERROR_MESSAGE);
    }

}
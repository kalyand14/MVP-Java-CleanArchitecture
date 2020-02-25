package com.android.basics.data.repository;

import com.android.basics.TestUtil;
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

public class UserDataRepositoryTest {

    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private DaoExecutor daoExecutor;
    @InjectMocks
    private UserDataRepository userDataRepository;
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
        when(userDao.getUser(TestUtil.USER_NAME, TestUtil.PASSWORD)).thenReturn(userTbl);
        userDataRepository.register(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(userDao).insert(TestUtil.USER_NAME, TestUtil.PASSWORD);
        verify(userMapper).convert(userTbl);
    }

    @Test
    public void tes_register_onComplete_success() {
        userDataRepository.register(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(user);
        verify(userCallback).onResponse(user);
    }

    @Test
    public void tes_register_onComplete_error() {
        userDataRepository.register(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(userCallback).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }

    @Test
    public void tes_authenticate_doAsync_success() {
        when(userDao.getUser(TestUtil.USER_NAME, TestUtil.PASSWORD)).thenReturn(userTbl);
        userDataRepository.authenticate(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().doAsync();
        verify(userMapper).convert(userTbl);
    }

    @Test
    public void tes_authenticate_onComplete_success() {
        userDataRepository.authenticate(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(user);
        verify(userCallback).onResponse(user);
    }

    @Test
    public void tes_authenticate_onComplete_error() {
        userDataRepository.authenticate(TestUtil.USER_NAME, TestUtil.PASSWORD, userCallback);
        verify(daoExecutor).start(callbackArgumentCaptor.capture());
        callbackArgumentCaptor.getValue().onComplete(null);
        verify(userCallback).onError(TestUtil.ERROR_CODE, TestUtil.ERROR_MESSAGE);
    }

}
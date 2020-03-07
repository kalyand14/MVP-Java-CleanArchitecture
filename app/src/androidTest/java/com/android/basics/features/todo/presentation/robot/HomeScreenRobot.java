package com.android.basics.features.todo.presentation.robot;

import com.android.basics.core.utils.ScreenRobot;

public class HomeScreenRobot extends ScreenRobot<HomeScreenRobot> {

    public void checkIsScreenTitleShown(String userName) {
        checkToolbarTitle("Welcome " + userName);
    }
}

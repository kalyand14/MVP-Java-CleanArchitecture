package com.android.basics.features.todo.presentation.robot;

import com.android.basics.R;
import com.android.basics.core.utils.ScreenRobot;

public class LogoutRobot extends ScreenRobot<LogoutRobot> {

    public LogoutRobot logoutAndConfirm() {
        return clickOnView(R.id.menu_action_logout).clickButtonTextOnDialog("YES");
    }
}

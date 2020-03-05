package com.android.basics.features.todo.presentation.registration;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.TestUtil;
import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.presentation.robot.RegisterRobot;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.android.basics.core.utils.ScreenRobot.withRobot;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterUserActivityTest {

    @Rule
    public ActivityTestRule<RegisterUserActivity> activityRule =
            new ActivityTestRule<RegisterUserActivity>(RegisterUserActivity.class);

    @BeforeClass
    public static void doCleanUp() {
        ApplicationScope.getInstance().userRepository().deleteAllUsers();
    }

    @Test
    public void shouldDisplayScreenHeader() {
        withRobot(RegisterRobot.class).checkIsScreenHeaderShown();
    }

    @Test
    public void shouldLandInHomeScreenWhenValid() {
        withRobot(RegisterRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .fillSignUpFormAndRegister(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2)
                .checkIsSuccessDialogShown();
    }

    @Test
    public void shouldShowErrorDialogWhenNotValid() {
        withRobot(RegisterRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .fillSignUpFormAndRegister("", "")
                .checkIsValidationErrorDialogShown();
    }

    @Test
    public void shouldShowRegistrationErrorDialogWhenTryToRegisterSameUser() {
        withRobot(RegisterRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .fillSignUpFormAndRegister(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2)
                .checkIsErrorDialogShown();
    }


}
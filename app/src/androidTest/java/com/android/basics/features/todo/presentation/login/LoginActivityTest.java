package com.android.basics.features.todo.presentation.login;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.presentation.robot.LoginRobot;
import com.android.basics.features.todo.presentation.robot.LogoutRobot;
import com.android.basics.features.todo.presentation.robot.RegisterRobot;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.android.basics.core.utils.ScreenRobot.withRobot;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @BeforeClass
    public static void doCleanUp() {
        ApplicationScope.getInstance().userRepository().deleteAllUsers();
    }

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void shouldDisplayScreenHeader() {
        withRobot(LoginRobot.class)
                .checkIsScreenHeaderShown()
                .checkUserNameAndPasswordLabelsDisplayed();
    }

    @Test
    public void shouldLandInHomeScreenWhenValid() {
        withRobot(LoginRobot.class).navigateToSignUp();
        withRobot(RegisterRobot.class).fillSignUpFormAndRegister();
        withRobot(LogoutRobot.class).logoutAndConfirm();
        withRobot(LoginRobot.class).login(activityRule);
    }

    @Test
    public void shouldShowErrorDialogWhenNotValid() {
        withRobot(LoginRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .clickLogin("", "")
                .checkIsErrorDialogShown();
    }
}
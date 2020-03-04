package com.android.basics.features.todo.presentation.registration;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android.basics.R;
import com.android.basics.TestUtil;
import com.android.basics.core.utils.ScreenRobot;
import com.android.basics.features.todo.presentation.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.android.basics.core.utils.ScreenRobot.withRobot;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterUserActivityTest {

    @Rule
    public IntentsTestRule<RegisterUserActivity> activityRule =
            new IntentsTestRule<>(RegisterUserActivity.class);


    @Test
    public void shouldProceeRegisterationWhenValid() {
        withRobot(RegisterRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .callLogin(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2)
                .checkIsSuccessDialogShown();
    }

    public static class RegisterRobot extends ScreenRobot<RegisterRobot> {

        public RegisterRobot checkIsLoggedIn() {
            intended(hasComponent(HomeActivity.class.getName()));
            return this;
        }

        public RegisterRobot checkUserNameAndPasswordLabelsDisplayed() {
            return checkViewHasText(R.id.txt_username, R.string.username_label)
                    .checkViewHasText(R.id.txt_password, R.string.password_label);
        }

        public RegisterRobot checkIsSuccessDialogShown() {
            return checkDialogWithTextIsDisplayed(R.string.registration_success);
        }

        public RegisterRobot checkIsInErrorState() {
            return checkDialogWithTextIsDisplayed(R.string.registration_error);
        }

        public RegisterRobot callLogin(String username, String password) {
            return enterTextIntoView(R.id.edt_username, username)
                    .enterTextIntoViewAndCloseKeyBoard(R.id.edt_password, password)
                    .clickOkOnView(R.id.btn_signup);
        }
    }

}
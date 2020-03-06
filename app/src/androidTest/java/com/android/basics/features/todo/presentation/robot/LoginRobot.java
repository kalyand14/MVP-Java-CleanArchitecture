package com.android.basics.features.todo.presentation.robot;

import androidx.test.rule.ActivityTestRule;

import com.android.basics.R;
import com.android.basics.TestUtil;
import com.android.basics.core.utils.ScreenRobot;
import com.android.basics.features.todo.presentation.home.HomeActivity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class LoginRobot extends ScreenRobot<LoginRobot> {

    public LoginRobot checkIsLoggedIn() {
        intended(hasComponent(HomeActivity.class.getName()), times(2));
        return this;
    }

    public LoginRobot navigateToSignUp() {
        return clickOnView(R.id.btn_signup);
    }

    public LoginRobot checkIsScreenHeaderShown() {
        return checkViewHasText(R.id.txt_login_header, R.string.login_header_text);
    }

    public LoginRobot checkUserNameAndPasswordLabelsDisplayed() {
        return checkViewHasText(R.id.txt_login_username, R.string.username_label)
                .checkViewHasText(R.id.txt_login_password, R.string.password_label);
    }

    public LoginRobot checkIsErrorDialogShown() {
        return checkDialogWithTextIsDisplayed(R.string.login_authentication_error);
    }

    public LoginRobot clickLogin(String username, String password) {
        return enterTextIntoView(R.id.edt_login_username, username)
                .enterTextIntoViewAndCloseKeyBoard(R.id.edt_login_password, password)
                .clickOnView(R.id.btn_login);
    }

    public void login(ActivityTestRule activityTestRule) {
        this.provideActivityContext(activityTestRule.getActivity())
                .clickLogin(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2)
                .checkIsLoggedIn();
    }
}

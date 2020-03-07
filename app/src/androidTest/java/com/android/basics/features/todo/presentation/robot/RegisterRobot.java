package com.android.basics.features.todo.presentation.robot;

import com.android.basics.R;
import com.android.basics.TestUtil;
import com.android.basics.core.utils.ScreenRobot;
import com.android.basics.features.todo.presentation.home.HomeActivity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;


public class RegisterRobot extends ScreenRobot<RegisterRobot> {

    public RegisterRobot checkIsLoggedIn() {
        intended(hasComponent(HomeActivity.class.getName()));
        return this;
    }

    public RegisterRobot checkIsScreenHeaderShown() {
        return checkViewHasText(R.id.txt_todo_add_header, R.string.registration_header);
    }

    public RegisterRobot checkUserNameAndPasswordLabelsDisplayed() {
        return checkViewHasText(R.id.txt_username, R.string.username_label)
                .checkViewHasText(R.id.txt_password, R.string.password_label);
    }

    public RegisterRobot checkIsSuccessDialogShown() {
        return checkDialogWithTextIsDisplayed(R.string.registration_success);
    }

    public RegisterRobot checkIsErrorDialogShown() {
        return checkDialogWithTextIsDisplayed(R.string.registration_error);
    }

    public RegisterRobot checkIsValidationErrorDialogShown() {
        return checkDialogWithTextIsDisplayed(R.string.registration_validation_error);
    }

    public RegisterRobot fillSignUpFormAndRegister(String username, String password) {
        return enterTextIntoView(R.id.edt_username, username)
                .enterTextIntoViewAndCloseKeyBoard(R.id.edt_password, password)
                .clickOnView(R.id.btn_signup);
    }

    public void fillSignUpFormAndRegister() {
        this.fillSignUpFormAndRegister(TestUtil.USER_NAME_2, TestUtil.PASSWORD_2)
                .checkDialogWithButtonTextIsDisplayed("OK")
                .clickButtonTextOnDialog("OK");
    }
}
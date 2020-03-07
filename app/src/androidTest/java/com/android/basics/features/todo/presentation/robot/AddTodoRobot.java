package com.android.basics.features.todo.presentation.robot;

import com.android.basics.R;
import com.android.basics.core.utils.ScreenRobot;

import java.util.Calendar;

public class AddTodoRobot extends ScreenRobot<AddTodoRobot> {

    public void checkIsScreenTitleShown() {
        checkToolbarTitle(getString(R.string.todo_add_title));
    }

    public void checkIsScreenHeaderShown() {
        checkViewHasText(R.id.txt_todo_add_header, R.string.todo_add_header);
    }

    public void checkLabelsAreDisplayed() {
        checkViewHasText(R.id.txt_todo_add_name, R.string.name_label);
        checkViewHasText(R.id.txt_todo_add_description, R.string.description_label);
        checkViewHasText(R.id.txt_todo_add_date, R.string.date_label);
    }

    public void checkButtonLabels() {
        checkViewHasText(R.id.btn_todo_todo_submit, R.string.submit_label);
        checkViewHasText(R.id.btn_todo_add_cancel, R.string.cancel_label);
    }

    public AddTodoRobot isSuccess() {
        return checkDialogWithTextIsDisplayed(R.string.todo_add_success_msg);
    }

    public AddTodoRobot isFailure() {
        return checkDialogWithTextIsDisplayed(R.string.toto_add_validation_error);
    }

    public HomeScreenRobot cancel() {
        clickOnView(R.id.menu_action_cancel);
        return new HomeScreenRobot();
    }

    public AddTodoRobot fillFormAndSubmit(String name, String description, Integer[] date) {
        Calendar calendar = Calendar.getInstance();
        return enterTextIntoView(R.id.edt_todo_add_name, name)
                .enterTextIntoViewAndCloseKeyBoard(R.id.edt_todo_add_description, description)
                .enterDateIntoView(R.id.btn_todo_add_date, date[0], date[1], date[2])
                .clickOnView(R.id.btn_todo_todo_submit);
    }
}

package com.android.basics.features.todo.presentation.robot;

import com.android.basics.R;
import com.android.basics.core.utils.ScreenRobot;

import java.util.Calendar;

public class EditTodoRobot extends ScreenRobot<EditTodoRobot> {

    public void checkIsScreenTitleShown() {
        checkToolbarTitle(getString(R.string.todo_edit_title));
    }

    public void checkIsScreenHeaderShown() {
        checkViewHasText(R.id.txt_todo_edit_header, R.string.todo_edit_header);
    }

    public void checkLabelsAreDisplayed() {
        checkViewHasText(R.id.txt_todo_edit_name, R.string.name_label);
        checkViewHasText(R.id.txt_todo_edit_description, R.string.description_label);
        checkViewHasText(R.id.txt_todo_edit_date, R.string.date_label);
    }

    public void checkButtonLabels() {
        checkViewHasText(R.id.btn_todo_update, R.string.update_label);
        checkViewHasText(R.id.btn_todo_delete, R.string.delete_label);
    }

    public void checkEditTextValues(String name, String description, String date) {
        checkViewHasText(R.id.edt_todo_edit_name, name);
        checkViewHasText(R.id.edt_todo_edit_description, description);
        checkViewHasText(R.id.edt_todo_add_date, date);
    }

    public EditTodoRobot isUpdateSuccess() {
        return checkDialogWithTextIsDisplayed(R.string.todo_edit_success_msg);
    }

    public EditTodoRobot isUpdateFailure() {
        return checkDialogWithTextIsDisplayed(R.string.todo_edit_validation_error);
    }

    public HomeScreenRobot cancel() {
        clickOnView(R.id.menu_action_cancel);
        return new HomeScreenRobot();
    }

    public EditTodoRobot clearText() {
        return clearTextFromView(R.id.edt_todo_edit_name)
                .clearTextFromView(R.id.edt_todo_add_date)
                .clearTextFromView(R.id.edt_todo_edit_description);
    }

    public EditTodoRobot fillFormAndSubmit(String name, String description, Integer[] date) {
        Calendar calendar = Calendar.getInstance();
        return enterTextIntoView(R.id.edt_todo_edit_name, name)
                .enterTextIntoViewAndCloseKeyBoard(R.id.edt_todo_edit_description, description)
                .enterDateIntoView(R.id.btn_edit_date, date[0], date[1], date[2])
                .clickOnView(R.id.btn_todo_update);
    }

    public EditTodoRobot deleteTodo() {
        return clickOnView(R.id.btn_todo_delete);
    }

    public EditTodoRobot isDeleteSuccess() {
        return checkDialogWithTextIsDisplayed(R.string.todo_delete_success_msg);
    }

}

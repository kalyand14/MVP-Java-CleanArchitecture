package com.android.basics.features.todo.presentation.todo.edit;

import com.android.basics.core.BasePresenter;

public interface EditTodoContract {

    interface View {
        void showProgressDialog(String message);

        void dismissProgressDialog();

        void showSuccessDialog(String message);

        void showErrorDialog(String message);

        void showDatePickerDialog();

        void setName(String name);

        void setDescription(String description);

        void setDate(String date);

        void showValidationErrorDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void loadTodo();

        void onSubmit(String name, String desc, String date);

        void navigateToViewTodoList();

        void OnCancel();

        void onDelete();

        void onSelectDate();
    }

    interface Navigator {
        void goToHomeScreen();
    }
}

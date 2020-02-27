package com.android.basics.features.todo.presentation.todo.add;

import com.android.basics.core.presenetation.BasePresenter;

public interface AddTodoContract {
    interface View {
        void showProgressDialog();

        void dismissProgressDialog();

        void showSuccessDialog();

        void showErrorDialog();

        void showDatePickerDialog();
    }

    interface Presenter extends BasePresenter<AddTodoContract.View> {
        void onSubmit(String name, String desc, String date);

        void navigateToViewTodoList();

        void OnCancel();

        void onSelectDate();
    }

    interface Navigator {
        void goToHomeScreen();
    }
}

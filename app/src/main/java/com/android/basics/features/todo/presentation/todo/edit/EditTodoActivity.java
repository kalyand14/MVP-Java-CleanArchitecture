package com.android.basics.features.todo.presentation.todo.edit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;

import java.util.Calendar;

public class EditTodoActivity extends AppCompatActivity implements EditTodoContract.View {

    EditTodoContract.Presenter presenter;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    EditText edtName;
    EditText edtDescription;
    EditText edtDate;
    Button btnSubmit;
    Button btnDelete;
    ImageButton btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        setTitle(getString(R.string.todo_edit_title));

        builder = new AlertDialog.Builder(this);

        edtName = findViewById(R.id.edt_todo_edit_name);
        edtDescription = findViewById(R.id.edt_todo_edit_description);
        edtDate = findViewById(R.id.edt_todo_add_date);

        btnSubmit = findViewById(R.id.btn_todo_update);
        btnDelete = findViewById(R.id.btn_todo_delete);
        btnDate = findViewById(R.id.btn_edit_date);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSubmit(edtName.getText().toString(), edtDescription.getText().toString(), edtDate.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDelete();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSelectDate();
            }
        });

        EditTodoInjector.getInstance().inject(this);

        presenter.attach(this);

        presenter.loadTodo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void showSuccessDialog(String message) {

        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                    presenter.navigateToViewTodoList();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void showErrorDialog(String message) {
        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Error");
        alert.show();
    }

    @Override
    public void showDatePickerDialog() {
        int mYear, mMonth, mDay, mHour, mMinute;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) ->
                        edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year),
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void setName(String name) {
        edtName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        edtDescription.setText(description);
    }

    @Override
    public void setDate(String date) {
        edtDate.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_action_cancel:
                presenter.OnCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showValidationErrorDialog() {
        //Setting message manually and performing action on button click
        builder.setMessage(getString(R.string.todo_edit_validation_error))
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Validation Error");
        alert.show();
    }

}

package com.android.basics.presentation.todo.add;

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

public class AddTodoActivity extends AppCompatActivity implements AddTodoContract.View {

    EditText edtName;
    EditText edtDescription;
    EditText edtDate;
    Button btnSubmit;
    Button btnCancel;
    ImageButton btnDate;

    AddTodoContract.Presenter presenter;

    ProgressDialog progressDialog;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        setTitle("Add Todo");

        builder = new AlertDialog.Builder(this);

        edtName = findViewById(R.id.edt_todo_name);
        edtDescription = findViewById(R.id.edt_todo_description);
        edtDate = findViewById(R.id.edt_todo_date);

        btnSubmit = findViewById(R.id.btn_add_todo);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDate = findViewById(R.id.btn_date);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSubmit(edtName.getText().toString(), edtDescription.getText().toString(), edtDate.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.OnCancel();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSelectDate();
            }
        });

        AddTodoInjector.getInstance().inject(this);

        presenter.attach(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        AddTodoInjector.getInstance().destroy();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Submitting ...");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void showSuccessDialog() {

        //Setting message manually and performing action on button click
        builder.setMessage("New record successfully inserted.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                    presenter.navigate();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void showErrorDialog() {

        //Setting message manually and performing action on button click
        builder.setMessage("There was a problem. could not able to insert the record.")
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cancel:
                presenter.OnCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

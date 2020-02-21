package com.android.basics.presentation.registration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserContract.View {

    RegisterUserContract.Presenter presenter;

    ProgressDialog progressDialog;

    Button btnLogin;
    Button btnRegister;

    EditText edtUserName;
    EditText edtPassword;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnLogin = findViewById(R.id.btn_add_todo);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_todo_name);
        edtPassword = findViewById(R.id.edt_todo_description);
        builder = new AlertDialog.Builder(this);

        btnRegister.setOnClickListener(view -> presenter.onRegisterClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnLogin.setOnClickListener(view -> presenter.onLoginClick());

        RegisterUserInjector.getInstance().inject(this);
        this.presenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.detach();
        RegisterUserInjector.getInstance().destroy();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void showRegistrationError() {

        edtUserName.setText("");
        edtPassword.setText("");

        //Setting message manually and performing action on button click
        builder.setMessage("There was a problem. could not able to register with details.")
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
    public void showRegistrationSuccess() {


        //Setting message manually and performing action on button click
        builder.setMessage("you've successfully registered.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                    presenter.onRegistrationSuccess();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Congrats");
        alert.show();

    }
}

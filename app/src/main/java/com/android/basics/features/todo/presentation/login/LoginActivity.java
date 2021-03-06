package com.android.basics.features.todo.presentation.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;
import com.android.basics.features.todo.di.UserScope;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    LoginContract.Presenter presenter;

    ProgressDialog progressDialog;

    Button btnLogin;
    Button btnRegister;

    EditText edtUserName;
    EditText edtPassword;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);
        builder = new AlertDialog.Builder(this);

        btnLogin.setOnClickListener(view -> presenter.OnLoginClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnRegister.setOnClickListener(view -> presenter.onRegisterClick());

        UserScope.getInstance().end();

        LoginInjector.getInstance().inject(this);
        this.presenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.detach();
        LoginInjector.getInstance().destroy();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void showAuthenticationError() {

        edtUserName.setText("");
        edtPassword.setText("");

        //Setting message manually and performing action on button click
        builder.setMessage(getString(R.string.login_authentication_error))
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Error");
        alert.show();


    }
}

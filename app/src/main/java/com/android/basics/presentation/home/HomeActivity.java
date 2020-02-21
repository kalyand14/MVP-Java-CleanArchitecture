package com.android.basics.presentation.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.basics.R;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.home.components.TodoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeScreenContract.View {

    ProgressDialog progressDialog;
    HomeScreenContract.Presenter presenter;
    RecyclerView recyclerView;
    TodoListAdapter todoListAdapter;
    LinearLayoutManager layoutManager;
    User user;
    TextView txtError;
    FloatingActionButton floatingActionButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        builder = new AlertDialog.Builder(this);

        floatingActionButton = findViewById(R.id.fab);
        txtError = findViewById(R.id.tv_message);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        HomeScreenInjector.getInstance().inject(this);

        recyclerView.setAdapter(todoListAdapter);
        presenter.attach(this);

        presenter.onLoadTodoList(user.getUserId());

        floatingActionButton.setOnClickListener(view -> presenter.onAddTodo());
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Loading todo list");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    public void showErrorLayout(boolean display) {
        txtError.setVisibility(display ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showList(boolean display) {
        recyclerView.setVisibility(display ? View.VISIBLE : View.GONE);
    }

    @Override
    public void loadTodoList(List<Todo> todoList) {
        todoListAdapter.addItems(todoList);
    }

    @Override
    public void setWelcomeMessage(String message) {
        setTitle(message);
    }

    @Override
    public void showLogoutConfirmationDialog() {
        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    dialog.dismiss();
                    presenter.logout();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Logout");
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HomeScreenInjector.getInstance().destroy();
        presenter.detach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                presenter.onLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.epitech.elominp.epitechstodolist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CreateTodoItemActivity extends AppCompatActivity {
    private int         id;
    private int         status;
    private int         creationDate;
    private int         endingDate;
    private EditText    editTitle;
    private EditText    editBody;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem chooseEnd = menu.add(Menu.NONE, Menu.NONE, Menu.NONE,
                "Choose Ending Date").setIcon(R.mipmap.ic_dialog_time);
        MenuItemCompat.setShowAsAction(chooseEnd, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        chooseEnd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Dialog endDialog = new Dialog(CreateTodoItemActivity.this);

                endDialog.setContentView(R.layout.date_time_picker_dialog);
                endDialog.setTitle("Choose ending date");

                if (Build.VERSION.SDK_INT >= 11) {
                    try {
                        DatePicker picker = (DatePicker) endDialog.findViewById(
                                R.id.date_time_picker_dialog_date);
                        Method setCalendarViewShown = picker.getClass().getMethod(
                                "setCalendarViewShown",
                                boolean.class
                        );
                        setCalendarViewShown.invoke(picker, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                endDialog.show();

                return true;
            }
        });

        MenuItem apply = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Apply").setIcon(
                R.mipmap.btn_check_buttonless_on);
        MenuItemCompat.setShowAsAction(apply, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        apply.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (id != -1) {
                    TodoListStorage.TodoItem item = new TodoListStorage.TodoItem(
                            id,
                            editTitle.getText().toString(),
                            editBody.getText().toString(),
                            status,
                            creationDate,
                            endingDate
                    );
                    TodoListStorage.getInstance().updateTodoItem(item);
                }
                else {
                    TodoListStorage.TodoItem item = new TodoListStorage.TodoItem(
                            editTitle.getText().toString(),
                            editBody.getText().toString(),
                            TodoListStorage.TodoStatus.NOT_DONE.ordinal(),
                            0,
                            0
                    );
                    TodoListStorage.getInstance().insertTodoItem(item);
                }
                finish();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo_item);

        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().setTitle("Editing");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ID, -1);
        String title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_TITLE);
        String body = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_BODY);
        status = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_STATUS, 0);
        creationDate = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_CREATION_DATE, 0);
        endingDate = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ENDING_DATE, 0);

        editTitle = (EditText) findViewById(R.id.editTitleInput);
        editBody = (EditText) findViewById(R.id.editBodyInput);

        editTitle.setText(title);
        editBody.setText(body);
    }
}

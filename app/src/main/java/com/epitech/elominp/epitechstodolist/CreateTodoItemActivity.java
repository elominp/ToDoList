package com.epitech.elominp.epitechstodolist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTodoItemActivity extends AppCompatActivity {
    int         id;
    String      title;
    String      body;
    int         status;
    int         creationDate;
    int         endingDate;
    EditText    editTitle;
    EditText    editBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo_item);

        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ID, -1);
        title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_TITLE);
        body = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_BODY);
        status = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_STATUS, 0);
        creationDate = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_CREATION_DATE, 0);
        endingDate = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ENDING_DATE, 0);

        editTitle = (EditText) findViewById(R.id.editTitleInput);
        editBody = (EditText) findViewById(R.id.editBodyInput);

        editTitle.setText(title);
        editBody.setText(body);

        Button button = (Button) findViewById(R.id.finishEditingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }
}

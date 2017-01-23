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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo_item);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_TITLE);
        String body = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_BODY);

        EditText editTitle = (EditText) findViewById(R.id.editTitleInput);
        EditText editBody = (EditText) findViewById(R.id.editBodyInput);

        editTitle.setText(title);
        editBody.setText(body);

        Button button = (Button) findViewById(R.id.finishEditingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

package com.epitech.elominp.epitechstodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TodoListStorage storage = TodoListStorage.getInstance();
    private final Activity me = this;
    private TodoListStorage.TodoItem[] _items;

    public final static String EXTRA_MESSAGE_TITLE = "com.epitech.elominp.epitechstodolist.MESSAGE_TITLE";
    public final static String EXTRA_MESSAGE_BODY = "com.epitech.elominp.epitechstodolist.MESSAGE_BODY";

    /**
     * Called when the Activity is created, so at the starting of the application.
     * It configures the display and show the ToDoList.
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupStorage();
        //setupMenuBar();
        setupTodoList();
        setupListeners();
    }

    protected void setupStorage() {
        storage.setContext(this.getApplicationContext());
    }

    /**
     * Sets up the listeners used in this activity :
     * - The listener for clicking an item
     * - The listener for holding an item
     * - The listener for clicking on the add button
     */
    protected void setupListeners() {
        /* ToDoListView configuration */
        ListView todoListView = (ListView) findViewById(R.id.todolistView);
        todoListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        /* A simple click on an item expand it */
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(me, CreateTodoItemActivity.class);
                intent.putExtra(EXTRA_MESSAGE_TITLE, _items[i].title);
                intent.putExtra(EXTRA_MESSAGE_BODY, _items[i].title);
                startActivity(intent);
            }
        });
        /* A long click on an item display a menu to edit it / remove it / change it's status */
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        /* Floating add button configuration */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /* Clicking on the floating add button will go the activity creating a new item */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(me, CreateTodoItemActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the menu bar.
     */
    protected void setupMenuBar() {
        /* Toolbar configuration */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    /**
     * Sets up the adapter of the ToDoList ListView and fill it
     */
    protected void setupTodoList() {
        ListView todoList = (ListView) findViewById(R.id.todolistView);
        _items = storage.getAllItems();
        TodoItemArrayAdapter adapter = new TodoItemArrayAdapter(
                getApplicationContext(),
                _items
        );
        todoList.setAdapter(adapter);

        refreshToDoList();
    }

    /**
     * Clear the ListView, fetch the list from the database and put it into the ListView.
     */
    protected void refreshToDoList() {
        ListView todoList = (ListView) findViewById(R.id.todolistView);
    }

    /**
     * Called when displayed, after creating the activity or after resuming app.
     * Currently not being used.
     */
    @Override
    protected void onStart() {
        super.onStart();
        setupMenuBar();
    }

    /**
     * Called when activity start interacting with the user.
     * Currently not being used.
     * Could be used in the future to refresh the list if it's synchronized with other sources.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when another activity comes into the foreground.
     * Currently not being used.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Called when the activity is no longer visible to the user.
     * Currently not being used.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Called when the activity is being destroyed.
     * Currently not being used.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when the activity is being restarted.
     * Currently not being used.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

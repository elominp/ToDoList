package com.epitech.elominp.epitechstodolist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {
    private TodoListStorage storage = TodoListStorage.getInstance();

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
        setupMenuBar();
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
        ExpandableListView todoListView = (ExpandableListView) findViewById(R.id.todolistView);
        /* A simple click on an item expand it */
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        /* A long click on an item display a menu to edit it / remove it / change it's status */
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return true;
            }
        });

        /* Floating add button configuration */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /* Clicking on the floating add button will go the activity creating a new item */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        ExpandableListView todoList = (ExpandableListView) findViewById(R.id.todolistView);

        refreshToDoList();
    }

    /**
     * Clear the ListView, fetch the list from the database and put it into the ListView.
     */
    protected void refreshToDoList() {
        ExpandableListView todoList = (ExpandableListView) findViewById(R.id.todolistView);
    }

    /**
     * Called when displayed, after creating the activity or after resuming app.
     * Currently not being used.
     */
    @Override
    protected void onStart() {
        super.onStart();
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

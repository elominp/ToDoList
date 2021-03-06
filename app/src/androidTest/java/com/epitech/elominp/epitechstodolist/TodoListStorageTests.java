package com.epitech.elominp.epitechstodolist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TodoListStorageTests {
    private TodoListStorage storage = TodoListStorage.getInstance();

    @Before
    public void configure() {
        storage.setContext(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void insertItemTitle() {
        storage.insertTodoItem("test");
    }

    @Test
    public void insertItemTitleBody() {
        storage.insertTodoItem("test", "test");
    }

    @Test
    public void insertItemTitleBodyStatus() {
        storage.insertTodoItem("test", "test", TodoListStorage.TodoStatus.NOT_DONE.ordinal());
    }

    @Test
    public void insertItemTitleBodyStatusEnd() {
        storage.insertTodoItem("test", "test", TodoListStorage.TodoStatus.NOT_DONE.ordinal(),
                System.currentTimeMillis() / 1000L);
    }

    @Test
    public void insertItemTodoItemObject() {
        storage.insertTodoItem(new TodoListStorage.TodoItem("test", "test", 0, 0, 0));
    }

    @Test
    public void getAllItems() {
        for (TodoListStorage.TodoItem item : storage.getAllItems())
            Log.d("getAllItems", item.title);
    }

    @Test
    public void updateTodoItemObject() {
        TodoListStorage.TodoItem[] items = storage.getAllItems();

        if (items.length > 0) {
            TodoListStorage.TodoItem item = items[0];
            storage.updateTodoItem(new TodoListStorage.TodoItem(item.id, "", "", 3, 0, 0));
        }
    }


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.epitech.elominp.epitechstodolist", appContext.getPackageName());
    }
}

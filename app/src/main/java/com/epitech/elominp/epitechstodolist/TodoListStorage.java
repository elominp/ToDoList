package com.epitech.elominp.epitechstodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Singleton class allowing to manage ToDoList storage
 */
public class TodoListStorage {
    /**
     * Nested class used to handle IO operations with Android SQLite database
     */
    protected class TodoListDatabaseOpenHelper extends SQLiteOpenHelper {

        public TodoListDatabaseOpenHelper(
                Context context,
                String name,
                SQLiteDatabase.CursorFactory factory,
                int version
        ) {
            super(context, name, factory, version);
        }

        /**
         * Create a new table for the application if the passed SQLite database
         * @param sqLiteDatabase database
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE EpitechTodoList (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL, " +
                    "body TEXT NOT NULL, " +
                    "status INTEGER NOT NULL DEFAULT 0, " +
                    "creationDate INTEGER NOT NULL, " +
                    "endingDate INTEGER NOT NULL);");
        }

        /**
         * Upgrade the table for the application in the passed SQLite database
         * @param sqLiteDatabase database
         * @param old previous version id of the application
         * @param version current version id of the application
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int version) {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master " +
                    "WHERE tbl_name = 'EpitechTodoList';", null);
            if (cursor == null)
                return;
            if (cursor.getCount() < 1)
                onCreate(sqLiteDatabase);
            cursor.close();
        }
    }

    /**
     * Pseudo-struct grouping all todoItem data
     */
    public static class TodoItem {
        public final int id;
        public final String title;
        public final String body;
        public final int status;
        public final long creationDate;
        public final long endingDate;

        /**
         * Build the struct with given parameters
         * @param t title of the item
         * @param b body of the item
         * @param s status of the item
         * @param c creation date of the item
         * @param e ending date of the item
         */
        public TodoItem(@NonNull String t, @NonNull String b, int s, long c, long e) {
            id = -1;
            title = t;
            body = b;
            status = s;
            creationDate = c;
            endingDate = e;
        }

        public TodoItem(int i, @NonNull String t, @NonNull String b, int s, long c, long e) {
            id = i;
            title = t;
            body = b;
            status = s;
            creationDate = c;
            endingDate = e;
        }
    }

    /**
     * Enumeration to alias statuses integer values in database
     * It contains these members :
     * - NOT_DONE : for items which status is "not done"
     * - DONE : for items which status is "done"
     */
    public enum TodoStatus {
        NOT_DONE,
        DONE
    }

    private static TodoListStorage _defaultInstance = new TodoListStorage();
    private TodoListDatabaseOpenHelper _openHelper;
    private Context _context = null;
    private SQLiteDatabase _db = null;

    /**
     * This function is used to get access to the instance of the singleton
     * @return TodoListStorage instance of the singleton
     */
    public static TodoListStorage getInstance() {
        return _defaultInstance;
    }

    /**
     * This function is used to set the context of the Android application.
     * It NEEDS to be called before any access to the database, otherwise the functions will
     * throw RuntimeException.
     * @param context of the Android application
     */
    public void setContext(@NonNull Context context) {
        if (_context == null) {
            _openHelper = new TodoListDatabaseOpenHelper(
                    context,
                    "EpitechTodoList.db",
                    null,
                    1);
            _db = _openHelper.getWritableDatabase();
        }
        _context = context;
    }

    /**
     * Insert a new item in the database.
     * @param title the title shown to the user for the todoItem
     */
    public void insertTodoItem(@NonNull String title) {
        insertTodoItemDb(title, "", TodoStatus.NOT_DONE.ordinal(), 0);
    }

    /**
     * Insert a new item in the database.
     * @param title the title shown to the user for the todoItem
     * @param body the body of the totoItem
     */
    public void insertTodoItem(@NonNull String title, @NonNull String body) {
        insertTodoItemDb(title, body, TodoStatus.NOT_DONE.ordinal(), 0);
    }

    /**
     * Insert a new item in the database.
     * @param title the title shown to the user for the todoItem
     * @param body the body of the totoItem
     * @param status the status of the item (NOT_DONE or DONE)
     */
    public void insertTodoItem(@NonNull String title, @NonNull String body, int status) {
        insertTodoItemDb(title, body, status, 0);
    }

    /**
     * Insert a new item in the database.
     * @param title the title shown to the user for the todoItem
     * @param body the body of the totoItem
     * @param status the status of the item (NOT_DONE or DONE)
     * @param endingDate the endingDate of the item (in posix timestamp)
     */
    public void insertTodoItem(@NonNull String title, @NonNull String body,
                               int status, long endingDate) {
        insertTodoItemDb(title, body, status, endingDate);
    }

    /**
     * Insert a new item in the database using a TodoItem.
     *
     * Note : The creationDate will be ignored as it's computed at recording time.
     * @param item object containing all parameters
     */
    public void insertTodoItem(@NonNull TodoItem item) {
        insertTodoItemDb(item.title, item.body, item.status, item.endingDate);
    }

    private void insertTodoItemDb(@NonNull String title, @NonNull String body, int status,
                                  long endingDate) {
        if (_context == null)
            throw new RuntimeException("Context not settled");
        if (status != TodoStatus.NOT_DONE.ordinal() && status != TodoStatus.DONE.ordinal())
            throw new IllegalArgumentException("Invalid status code");
        long creationDate = System.currentTimeMillis() / 1000;
        ContentValues row = new ContentValues();

        row.put("title", title);
        row.put("body", body);
        row.put("status", status);
        row.put("creationDate", creationDate);
        row.put("endingDate", endingDate);

        _db.insert("EpitechTodoList", null, row);
    }

    public void updateTodoItem(@NonNull TodoItem item) {
        if (item.id < 0)
            throw new IllegalArgumentException("Invalid todo item");
        ContentValues row = new ContentValues();

        row.put("title", item.title);
        row.put("body", item.body);
        if (item.endingDate != 0)
            row.put("endingDate", item.endingDate);

        String[] args = {Integer.toString(item.id)};

        _db.update("EpitechTodoList", row, "id = ?", args);
    }

    /**
     * Function used to get all todoItems stored inside the database
     * @return an array of TodoItem containing all items stored inside the database
     */
    @NonNull
    public TodoItem[] getAllItems() {
        if (_context == null)
            throw new RuntimeException("Context not settled");
        final String[] columns = {"id", "title", "body", "status", "creationDate", "endingDate"};
        Cursor rows = _db.query(
                "EpitechTodoList",
                columns,
                null,
                null,
                null,
                null,
                "creationDate DESC"
        );
        if (rows == null)
            throw new RuntimeException("Unable to query database");
        int nbRows = rows.getCount();
        TodoItem[] items = new TodoItem[nbRows];
        rows.moveToFirst();
        for (int i = 0; i < nbRows; i++) {
            items[i] = new TodoItem(
                    rows.getInt(0),
                    rows.getString(1),
                    rows.getString(2),
                    rows.getInt(3),
                    rows.getLong(4),
                    rows.getLong(5)
            );
            rows.moveToNext();
        }
        rows.close();
        return items;
    }

    private TodoListStorage() {
    }
}

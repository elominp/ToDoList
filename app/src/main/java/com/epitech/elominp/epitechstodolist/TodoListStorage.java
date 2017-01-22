package com.epitech.elominp.epitechstodolist;

public class TodoListStorage {
    private static TodoListStorage ourInstance = new TodoListStorage();

    public static TodoListStorage getInstance() {
        return ourInstance;
    }

    private TodoListStorage() {
    }
}

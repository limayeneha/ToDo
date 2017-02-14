package com.example.limayeneha.todo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by limayeneha on 2/8/17.
 */

@Database(name = ToDoDatabase.NAME, version = ToDoDatabase.VERSION)
public class ToDoDatabase {
    public static final String NAME = "ToDoDatabase";

    public static final int VERSION = 1;
}

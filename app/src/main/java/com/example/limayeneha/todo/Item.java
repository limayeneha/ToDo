package com.example.limayeneha.todo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by limayeneha on 2/8/17.
 */
@Table(database = ToDoDatabase.class)
public class Item extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String taskName;

    @Column
    public int status;

    @Column
    public String priority;

    @Column
    public String dueDate;

    public Item() {

    }

    public Item(String taskName, int status, String priority, String dueDate) {
        this.taskName = taskName;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Item(int id, int status) {
        this.id = id;
        this.status = status;
    }
}

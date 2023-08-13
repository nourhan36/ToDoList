package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.todo.Adapter.AAdapter;
import com.example.todo.DataBase.DataBaseHandler;
import com.example.todo.Model.MModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView recyclerViewTasks;
    private AAdapter tasksAdapter;
    private List<MModel> tasksList;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        db = new DataBaseHandler(this);
        db.openDatabase();

        tasksList = new ArrayList<>();
        recyclerViewTasks = findViewById(R.id.recyclerViewtasks);
        tasksAdapter = new AAdapter(MainActivity.this);
        recyclerViewTasks.setAdapter(tasksAdapter);

        MModel task = new MModel();
        task.setTask("Test Task");
        task.setStatus(0);
        task.setId(1);

        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);

        tasksAdapter.setTasks(tasksList);
    }

    public void handleDialogClose(DialogInterface dialog) {
        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged();
    }
}

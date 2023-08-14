package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todo.Adapter.AAdapter;
import com.example.todo.DataBase.DataBaseHandler;
import com.example.todo.Model.MModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    String name;
    private RecyclerView recyclerViewTasks;
    private AAdapter tasksAdapter;
    private List<MModel> tasksList;
    private DataBaseHandler db;
    private FloatingActionButton fab;
    private Button selectBtn;

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
        tasksAdapter = new AAdapter(db,MainActivity.this);
        recyclerViewTasks.setAdapter(tasksAdapter);

        fab=findViewById(R.id.floatingActionButton2);

        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.newInstance().show(getSupportFragmentManager(), AddTask.TAG);
            }
        });
    }

    public void handleDialogClose(DialogInterface dialog) {
        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        tasksAdapter.setTasks(tasksList);
        tasksAdapter.notifyDataSetChanged();
    }
}

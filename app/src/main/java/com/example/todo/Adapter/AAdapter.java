package com.example.todo.Adapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AddTask;
import com.example.todo.DataBase.DataBaseHandler;
import com.example.todo.MainActivity;
import com.example.todo.Model.MModel;
import com.example.todo.R;
import com.example.todo.ViewTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;

import java.util.List;


public class AAdapter extends RecyclerView.Adapter<AAdapter.ViewHolder>{

    private List<MModel> todoList;
    private MainActivity activity;
    private DataBaseHandler db;
    private ViewTask vt;

    public AAdapter(DataBaseHandler db ,MainActivity activity){
        this.db=db;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        MModel item=todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));

        holder.selectBtn.setOnClickListener(view -> {
            int pos=position;
            showItem(position);
            //editItem(position);
        });
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int status) {
        return status!=0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<MModel>todoList){
        this.todoList=todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        MModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public void showItem(int position){
        MModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());

        ViewTask fragment = new ViewTask(position, this); // Pass the adapter instance and position
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), ViewTask.TAG);
    }

    public void editItem(int position) {
        MModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddTask fragment = new AddTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Button selectBtn;
        CheckBox task;
        public ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.checkBox);
            selectBtn=view.findViewById(R.id.btnselect);
        }
    }
}

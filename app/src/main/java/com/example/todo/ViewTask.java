package com.example.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adapter.AAdapter;
import com.example.todo.DataBase.DataBaseHandler;
import com.example.todo.Model.MModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ViewTask extends BottomSheetDialogFragment {

    private int position;
    private TextView tvTask;
    private Button btnEdit , btnDelete , btnCancel;
    public static final String TAG = "ActionBottomDialog";
    private DataBaseHandler db;
    private List<MModel> todoList;
    private AAdapter adapter;
    private MainActivity activity;
    public ViewTask(int position, AAdapter adapter) {
        this.position = position;
        this.adapter = adapter;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTask=getView().findViewById(R.id.tvTask_viewTasks);
        btnEdit = getView().findViewById(R.id.editbtn);
        btnDelete = getView().findViewById(R.id.deletebtn);

        final Bundle bundle = getArguments();
        if(bundle != null){
            String task = bundle.getString("task");
            tvTask.setText(task);
        }

        btnEdit.setOnClickListener(view1 -> {
            if (adapter != null) {
                adapter.editItem(position);
            }
        });

        btnDelete.setOnClickListener(view1 -> {
            showDeleteConfirmationDialog();
        });

    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()); // Use requireContext()
        builder.setTitle(R.string.delete_task_dialog_title);
        builder.setMessage(R.string.delete_task_dialog_message);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (adapter != null) {
                    adapter.deleteItem(position);
                }
                dialog.dismiss();
                dismiss(); // Close the dialog
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //    tvTask = getView().findViewById(R.id.tvTask_viewTasks);
//    btnEdit = getView().findViewById(R.id.editbtn);
//    btnDelete = getView().findViewById(R.id.deletebtn);
//    btnCancel = getView().findViewById(R.id.cancelBtn);


}

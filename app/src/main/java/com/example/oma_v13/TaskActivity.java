package com.example.oma_v13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TaskActivity<taskAdapter> extends AppCompatActivity {
    FloatingActionButton addTaskFAB;
    TextView fromEventTxt, taskDetailTxt, taskDueTxt;
    FirebaseFirestore fStore;
    RecyclerView trecyclerView;
    FirestoreRecyclerAdapter<Task, TaskViewHolder> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        fromEventTxt = findViewById(R.id.fromEventTxt);
        taskDetailTxt = findViewById(R.id.TaskDetailTxt);
        taskDueTxt = findViewById(R.id.TaskDueTxt);
        addTaskFAB = findViewById(R.id.addTaskFAB);

        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNewTask.class));
                finish();
            }
        });

        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("Task").orderBy("taskName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Task> alltask = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query,Task.class)
                .build();

        taskAdapter = new FirestoreRecyclerAdapter<Task, TaskViewHolder>(alltask) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull Task task) {
                taskViewHolder.taskName.setText(task.getTaskName());
                taskViewHolder.taskFrom.setText(task.getTaskFrom());
                taskViewHolder.taskDetail.setText(task.getTaskDetail());
                taskViewHolder.taskDue.setText(task.getTaskDue());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_layout, parent, false);
                return new TaskViewHolder(view);
            }
        };

        trecyclerView = findViewById(R.id.taskRecycler);
        trecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trecyclerView.setAdapter(taskAdapter);


    }
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskName, taskDetail, taskFrom, taskDue;
        View view;

        public TaskViewHolder(@NonNull View taskView){
            super(taskView);

            taskName = taskView.findViewById(R.id.viewTaskName);
            taskFrom = taskView.findViewById(R.id.viewFromEvent);
            taskDetail = taskView.findViewById(R.id.viewTaskDetail);
            taskDue = taskView.findViewById(R.id.viewTaskDue);
            view = taskView;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        taskAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (taskAdapter != null){
            taskAdapter.stopListening();
        }
    }
}

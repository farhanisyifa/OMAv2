package com.example.oma_v13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends AppCompatActivity {
    EditText inputTaskName, inputTaskFrom, inputTaskDetail, inputTaskDue;
    Button addTaskBtn;
    ImageButton taskDueBtn;
    FirebaseFirestore fStore;
    private int mDate, mMonth, mYear;
    private Object DatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        inputTaskName = findViewById(R.id.taskName);
        inputTaskFrom = findViewById(R.id.taskFrom);
        inputTaskDetail = findViewById(R.id.taskDetail);
        inputTaskDue = findViewById(R.id.taskDue);

        taskDueBtn = findViewById(R.id.taskDueBtn);
        addTaskBtn = findViewById(R.id.AddTaskBtn);

        taskDueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar taskDueBtn = Calendar.getInstance();
                mDate = taskDueBtn.get(Calendar.DATE);
                mMonth = taskDueBtn.get(Calendar.MONTH);
                mYear = taskDueBtn.get(Calendar.YEAR);
                android.app.DatePickerDialog datePickerDialog= new DatePickerDialog(AddNewTask.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        inputTaskDue.setText(date+"/"+month+"/"+year);

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        fStore = FirebaseFirestore.getInstance();

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = inputTaskName.getText().toString();
                String FromEvent = inputTaskFrom.getText().toString();
                String Detail = inputTaskDetail.getText().toString();
                String Due = inputTaskDue.getText().toString();

                if (Name.isEmpty()) {
                    inputTaskName.setError("Please add event name");
                    return;
                }

                if (FromEvent.isEmpty()) {
                    inputTaskFrom.setError("Please add description of the event");
                    return;
                }

                if (Detail.isEmpty()) {
                    inputTaskDetail.setError("Please add the detail of the task");
                    return;
                }

                if (Due.isEmpty()) {
                    inputTaskDue.setError("Please add the due date");
                    return;
                }

                DocumentReference docref = fStore.collection("Task").document(Name);
                Map<String,Object> note = new HashMap<>();
                note.put("taskName", Name);
                note.put("taskFrom", FromEvent);
                note.put("taskDetail", Detail);
                note.put("taskDue", Due);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNewTask.this, "item added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewTask.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
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

public class AddNewEvent extends AppCompatActivity {
    EditText eventName, eventDescription, eventStart, eventEnd;
    Button addEvent;
    FirebaseFirestore fstore;
    ImageButton calStart, calEnd;
    private int mDate, mMonth, mYear;
    private Object DatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        eventName = findViewById(R.id.eventName);
        eventDescription = findViewById(R.id.eventDescription);
        eventStart = findViewById(R.id.eventStartTxt);
        eventEnd = findViewById(R.id.eventEndTxt);
        addEvent = findViewById(R.id.AddEventBtn);

        calStart = findViewById(R.id.eventStartBtn);
        calEnd = findViewById(R.id.eventEndBtn);

        calStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calStart = Calendar.getInstance();
                mDate = calStart.get(Calendar.DATE);
                mMonth = calStart.get(Calendar.MONTH);
                mYear = calStart.get(Calendar.YEAR);
                android.app.DatePickerDialog datePickerDialog= new DatePickerDialog(AddNewEvent.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        eventStart.setText(date+"/"+month+"/"+year);

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        calEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calStart = Calendar.getInstance();
                mDate = calStart.get(Calendar.DATE);
                mMonth = calStart.get(Calendar.MONTH);
                mYear = calStart.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(AddNewEvent.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        eventEnd.setText(date+"/"+month+"/"+year);

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        fstore = FirebaseFirestore.getInstance();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = eventName.getText().toString();
                String Description = eventDescription.getText().toString();
                String StartDate = eventStart.getText().toString();
                String EndDate = eventEnd.getText().toString();

                if (Name.isEmpty()) {
                    eventName.setError("Please add event name");
                    return;
                }

                if (Description.isEmpty()) {
                    eventDescription.setError("Please add description of the event");
                    return;
                }

                if (StartDate.isEmpty()) {
                    eventStart.setError("Please add the starting date");
                    return;
                }

                if (EndDate.isEmpty()) {
                    eventEnd.setError("Please add the end date");
                    return;
                }

                DocumentReference docref = fstore.collection("Event").document(Name);
                Map<String,Object> note = new HashMap<>();
                note.put("eventName", Name);
                note.put("eventDescription", Description);
                note.put("startDate", StartDate);
                note.put("endDate", EndDate);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNewEvent.this, "item added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewEvent.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
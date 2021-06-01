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

public class SubmitPresence extends AppCompatActivity {
    EditText presenceAgenda, presenceDate, presenceEvent;
    ImageButton taskDueBtn;
    Button submitPresenceBtn;
    FirebaseFirestore fStore;
    private int mDate, mMonth, mYear;
    private Object DatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_presence);

        presenceAgenda = findViewById(R.id.presenceAgenda);
        presenceEvent = findViewById(R.id.presenceEvent);
        presenceDate = findViewById(R.id.presenceDate);
        submitPresenceBtn = findViewById(R.id.submitPresenceBtn);
        taskDueBtn = findViewById(R.id.taskDueBtn);
        fStore = FirebaseFirestore.getInstance();

        taskDueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar taskDueBtn = Calendar.getInstance();
                mDate = taskDueBtn.get(Calendar.DATE);
                mMonth = taskDueBtn.get(Calendar.MONTH);
                mYear = taskDueBtn.get(Calendar.YEAR);
                android.app.DatePickerDialog datePickerDialog= new DatePickerDialog(SubmitPresence.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        presenceDate.setText(date+"/"+month+"/"+year);

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        submitPresenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Agenda = presenceAgenda.getText().toString();
                String Event = presenceEvent.getText().toString();
                String Date = presenceDate.getText().toString();

                if (Agenda.isEmpty()) {
                    presenceAgenda.setError("Please add event name");
                    return;
                }

                if (Event.isEmpty()) {
                    presenceEvent.setError("Please add description of the event");
                    return;
                }

                if (Date.isEmpty()) {
                    presenceDate.setError("Please add the starting date");
                    return;
                }

                DocumentReference docref = fStore.collection("PresenceActivity").document(Agenda);
                Map<String,Object> note = new HashMap<>();
                note.put("presenceName", Agenda);
                note.put("presenceEvent", Event);
                note.put("presenceDate", Date);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SubmitPresence.this, "item added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PresenceActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SubmitPresence.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
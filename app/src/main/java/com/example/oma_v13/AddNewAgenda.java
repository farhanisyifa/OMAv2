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

public class AddNewAgenda extends AppCompatActivity {
    EditText agendaName, agendaFrom, agendaDetail, agendaDate;
    ImageButton cal;
    Button submitAgenda;
    FirebaseFirestore fStore;
    private int mDate, mMonth, mYear;
    private Object DatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_agenda);

        agendaName = findViewById(R.id.agendaName);
        agendaFrom = findViewById(R.id.agendaFrom);
        agendaDetail = findViewById(R.id.agendaDetail);
        agendaDate = findViewById(R.id.agendaDate);

        cal = findViewById(R.id.calAgenda);
        submitAgenda = findViewById(R.id.addAgendaBtn);


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar taskDueBtn = Calendar.getInstance();
                mDate = taskDueBtn.get(Calendar.DATE);
                mMonth = taskDueBtn.get(Calendar.MONTH);
                mYear = taskDueBtn.get(Calendar.YEAR);
                android.app.DatePickerDialog datePickerDialog= new DatePickerDialog(AddNewAgenda.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        agendaDate.setText(date+"/"+month+"/"+year);

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        fStore = FirebaseFirestore.getInstance();

        submitAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = agendaName.getText().toString();
                String FromEvent = agendaFrom.getText().toString();
                String Detail = agendaDetail.getText().toString();
                String Date = agendaDate.getText().toString();

                if (Name.isEmpty()) {
                    agendaName.setError("Please add event name");
                    return;
                }

                if (FromEvent.isEmpty()) {
                    agendaFrom.setError("Please add description of the event");
                    return;
                }

                if (Detail.isEmpty()) {
                    agendaDetail.setError("Please add the detail of the task");
                    return;
                }

                if (Date.isEmpty()) {
                    agendaDate.setError("Please add the due date");
                    return;
                }

                DocumentReference docref = fStore.collection("Agenda").document(Name);
                Map<String,Object> note = new HashMap<>();
                note.put("agendaName", Name);
                note.put("agendaFrom", FromEvent);
                note.put("agendaDetail", Detail);
                note.put("agendaDate", Date);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNewAgenda.this, "item added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),PresenceActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewAgenda.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
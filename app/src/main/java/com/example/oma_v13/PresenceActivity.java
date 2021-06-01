package com.example.oma_v13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Presence extends AppCompatActivity {
    FloatingActionButton submitPresence, addAgenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        submitPresence = findViewById(R.id.userAddPresence);
        addAgenda = findViewById(R.id.userAddAgenda);

        submitPresence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewAgenda.class));
                finish();
            }
        });

        addAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubmitPresence.class));
                finish();
            }
        });
    }
}
package com.example.oma_v13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Path;
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

public class PresenceActivity extends AppCompatActivity {
    FloatingActionButton submitPresence, addAgenda;
    TextView agendaFrom, agendaDetail, agendaDate;
    FirebaseFirestore fStore;
    RecyclerView precyclerView;
    FirestoreRecyclerAdapter<Agenda, AgendaViewHolder> agendaAdapter;


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

        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("Agenda").orderBy("agendaName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Agenda> allagenda = new FirestoreRecyclerOptions.Builder<Agenda>()
                .setQuery(query, Agenda.class)
                .build();

        agendaAdapter = new FirestoreRecyclerAdapter<Agenda, AgendaViewHolder>(allagenda) {
            @Override
            protected void onBindViewHolder(@NonNull AgendaViewHolder agendaViewHolder, int i, @NonNull Agenda agenda) {
                agendaViewHolder.agendaName.setText(agenda.getAgendaName());
                agendaViewHolder.agendaFrom.setText(agenda.getAgendaFrom());
                agendaViewHolder.agendaDetail.setText(agenda.getAgendaDetail());
                agendaViewHolder.agendaDate.setText(agenda.getAgendaDate());
            }

            @NonNull
            @Override
            public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_list_layout, parent, false);
                return new AgendaViewHolder(view);
            }

        };

        precyclerView = findViewById(R.id.agendaRecycler);
        precyclerView.setLayoutManager(new LinearLayoutManager(this));
        precyclerView.setAdapter(agendaAdapter);
    }

    public class AgendaViewHolder extends RecyclerView.ViewHolder {
        TextView agendaName, agendaFrom, agendaDetail, agendaDate;
        View view;

        public AgendaViewHolder(@NonNull View agendaView) {
            super(agendaView);

            agendaName = agendaView.findViewById(R.id.viewAgendaName);
            agendaFrom = agendaView.findViewById(R.id.viewAgendaFrom);
            agendaDetail = agendaView.findViewById(R.id.viewAgendaDetail);
            agendaDate = agendaView.findViewById(R.id.viewAgendaDate);
            view = agendaView;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        agendaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (agendaAdapter != null) {
            agendaAdapter.stopListening();
        }
    }
}
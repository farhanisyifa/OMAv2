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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Event extends AppCompatActivity {
    FloatingActionButton addEventBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Events, EventsViewHolder> eventAdapter;
    RecyclerView mrecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        addEventBtn = findViewById(R.id.addEventFAB);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewEvent.class));
                finish();
            }
        });

        Query query = fStore.collection("Event").orderBy("eventName", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Events> allevents = new FirestoreRecyclerOptions.Builder<Events>()
                .setQuery(query,Events.class)
                .build();

        eventAdapter = new FirestoreRecyclerAdapter<Events, EventsViewHolder>(allevents) {
            @Override
            protected void onBindViewHolder(@NonNull EventsViewHolder eventsViewHolder, int i, @NonNull Events events) {
                eventsViewHolder.eventName.setText(events.getEventName());
                eventsViewHolder.eventStart.setText(events.getStartDate());
                eventsViewHolder.eventEnd.setText(events.getEndDate());
                eventsViewHolder.eventDesc.setText(events.getEventDescription());
            }

            @NonNull
            @Override
            public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_layout,parent,false);
                return new EventsViewHolder(view);
            }
        };

        mrecyclerView = findViewById(R.id.eventRecycler);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setAdapter(eventAdapter);

    }

    public class EventsViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, eventStart, eventEnd, eventDesc;
        View view;

        public EventsViewHolder(@NonNull View eventsView){
            super(eventsView);

            eventName = eventsView.findViewById(R.id.viewEventName);
            eventStart = eventsView.findViewById(R.id.viewEventStart);
            eventEnd = eventsView.findViewById(R.id.viewEventEnd);
            eventDesc = eventsView.findViewById(R.id.viewEventDescription);
            view = eventsView;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (eventAdapter != null){
            eventAdapter.stopListening();
        }
    }
}
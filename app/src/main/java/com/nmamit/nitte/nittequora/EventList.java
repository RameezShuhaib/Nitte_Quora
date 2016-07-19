package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventList extends AppCompatActivity {
    ImageView profile, books;
    Firebase fb;
    ListView listview;
    ArrayList<Events> eventsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Firebase.setAndroidContext(this);
        listview = (ListView) findViewById(R.id.listview);
        fb = new Firebase("https://nittequora.firebaseio.com/events");
        fb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                getUpdates(dataSnapshot);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        profile = (ImageView) findViewById(R.id.profile);
        books = (ImageView) findViewById(R.id.books);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.profile"));
                finish();
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.library"));
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Events  e    = (Events) listview.getItemAtPosition(position);
                Intent i = new Intent("android.intent.action.eventregistration");
                i.putExtra("eventname", e.getEventName());
                i.putExtra("description", e.getDescription());
                i.putExtra("startdate", e.getStartDate());
                i.putExtra("enddate", e.getEndDate());
                i.putExtra("eventkey", e.getEventKey());
                startActivity(i);
            }
        });

    }

    private void getUpdates(DataSnapshot data){
        Events e = data.getValue(Events.class);
        e.setEventKey(data.getKey());
        eventsList.add(e);
        if(eventsList.size()>0){
            ArrayAdapter<Events> adapter = new ArrayAdapter<Events>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, eventsList);
            listview.setAdapter(adapter);
        }
    }

}

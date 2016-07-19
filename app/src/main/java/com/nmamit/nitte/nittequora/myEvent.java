package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class myEvent extends AppCompatActivity {
    Firebase fb;
    ListView mylistview;
    ArrayList<Events> eventsList = new ArrayList<>();
    String eventKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/events");
        fb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("adminUser").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    getUpdates(dataSnapshot);
                }
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

        mylistview = (ListView) findViewById(R.id.myeventlist);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Events  e    = (Events) mylistview.getItemAtPosition(position);
                Intent i = new Intent("android.intent.action.RegisterdParticipants");
                i.putExtra("eventName", e.getEventName());
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
            mylistview.setAdapter(adapter);
        }
    }
}

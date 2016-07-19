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
import java.util.List;

public class RegisterdParticipants extends AppCompatActivity {
    Firebase fb;
    ListView myEventParticipants;
    ArrayList<Participant> participantsList = new ArrayList<>();
    Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerd_participants);
        data = getIntent();
        Firebase.setAndroidContext(this);
        myEventParticipants = (ListView) findViewById(R.id.myeventparticipants);
        fb = new Firebase("https://nittequora.firebaseio.com/events");
        fb.child(data.getStringExtra("eventkey")+"/participants").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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

        myEventParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participant  p  = (Participant) myEventParticipants.getItemAtPosition(position);
                Intent i = new Intent("android.intent.action.RegistrationPayment");
                i.putExtra("name", p.toString());
                i.putExtra("eventName", getIntent().getStringExtra("eventName"));
                i.putExtra("uid", p.getUid());
                i.putExtra("eventKey", data.getStringExtra("eventkey"));
                startActivity(i);
            }
        });

    }

    private void getUpdates(DataSnapshot data){
        Participant p = data.getValue(Participant.class);
        p.setUid(data.getKey());
        Toast.makeText(getApplicationContext(),p.getUid(),Toast.LENGTH_SHORT).show();
        participantsList.add(p);
        if(participantsList.size()>0){
            ArrayAdapter<Participant> adapter = new ArrayAdapter<Participant>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, participantsList);
            myEventParticipants.setAdapter(adapter);
        }
    }
}

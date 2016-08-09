package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    ImageView profile, books, events;
    Firebase fb;
    ListView listview;
    ArrayList<Message> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Firebase.setAndroidContext(this);
        listview = (ListView) findViewById(R.id.notifications);
        fb = new Firebase("https://nittequora.firebaseio.com/users");
        fb.child(Application.getUsn(getApplicationContext())+"/messages").addChildEventListener(new ChildEventListener() {
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

        profile = (ImageView) findViewById(R.id.profile);
        books = (ImageView) findViewById(R.id.books);
        events = (ImageView) findViewById(R.id.events);

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

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.eventlist"));
                finish();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message  m = (Message) listview.getItemAtPosition(position);
                Intent i = new Intent("android.intent.action.myMessages");
                i.putExtra("date", m.getDate());
                i.putExtra("sender", m.getSender());
                i.putExtra("subject", m.getSubject());
                i.putExtra("message", m.getMessage());
                startActivity(i);
            }
        });

    }
    private void getUpdates(DataSnapshot data){
        notificationList.add(data.getValue(Message.class));
        if(notificationList.size()>0){
            ArrayAdapter<Message> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, notificationList);
            listview.setAdapter(adapter);
        }
    }

}

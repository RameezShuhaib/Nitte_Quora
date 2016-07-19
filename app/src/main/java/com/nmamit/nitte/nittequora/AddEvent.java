package com.nmamit.nitte.nittequora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.Event;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class AddEvent extends AppCompatActivity {
    Firebase fb;
    FirebaseAuth firebaseAuth;
    TextView eventName, description, startDate, endDate;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/");


        eventName = (TextView) findViewById(R.id.eventName);
        description = (TextView) findViewById(R.id.description);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        upload = (Button) findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Events e = new Events();
                e.setEventName(eventName.getText().toString());
                e.setDescription(description.getText().toString());
                e.setStartDate(startDate.getText().toString());
                e.setEndDate(endDate.getText().toString());
                e.setAdminUser(firebaseAuth.getCurrentUser().getUid());
                fb.child("events").push().setValue(e);
                Toast.makeText(getApplicationContext(), "event succesfully added",
                        Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }
}

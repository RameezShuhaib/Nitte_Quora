package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class Library extends AppCompatActivity {
    ImageView profile, events;
    Firebase fb;
    TextView title, author;
    Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/");
        upload = (Button) findViewById(R.id.upload);
        title = (EditText) findViewById(R.id.Title);
        author = (EditText) findViewById(R.id.Author);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().equals("") && !author.getText().equals("")){
                    Books bk = new Books();
                    bk.setAuthor(author.getText().toString());
                    bk.setTitle(title.getText().toString());
                    fb.child("books").push().setValue(bk);
                    finish();
                }
            }
        });


        profile = (ImageView) findViewById(R.id.profile);
        events = (ImageView) findViewById(R.id.events);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.profile"));
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
    }
}

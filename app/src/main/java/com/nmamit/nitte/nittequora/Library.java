package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class Library extends AppCompatActivity {
    ImageView profile, events, messages;
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
                    bk.setUser(Application.getUsn(getApplicationContext()));
                    fb.child("books").push().setValue(bk);
                    Toast.makeText(getApplicationContext(), "Successfully Submited", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Please fill the book details", Toast.LENGTH_SHORT).show();
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

        messages = (ImageView) findViewById(R.id.messages);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.Notification"));
                finish();
            }
        });

    }
}

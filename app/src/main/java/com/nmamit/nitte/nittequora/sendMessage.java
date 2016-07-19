package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;

public class sendMessage extends AppCompatActivity {
    Firebase fb;
    Message msg;
    TextView to, subject, message;
    Button send;
    String uid;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/users");

        to = (TextView) findViewById(R.id.To);
        subject = (TextView) findViewById(R.id.subject);
        message = (TextView) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.sendmsg);
        i = getIntent();

        to.setText(i.getStringExtra("uid"));
//        if(i.getStringExtra("uid") != null)
//            uid = i.getStringExtra("uid");



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = new Message();
                msg.setSubject(subject.getText().toString());
                String date = DateFormat.getDateTimeInstance().format(new Date());
                msg.setDate(date);
                msg.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());
                msg.setMessage(message.getText().toString());
                fb.child(to.getText().toString()+"/messages").push().setValue(msg);

            }
        });


    }
}

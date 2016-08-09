package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class myMessages extends AppCompatActivity {

    Intent i;
    String Date, Sender, Subject, Message;
    TextView date, sender, subject, message;
    Button reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        i = getIntent();
        Date = i.getStringExtra("date");
        Sender = i.getStringExtra("sender");
        Subject = i.getStringExtra("subject");
        Message = i.getStringExtra("message");

        date = (TextView) findViewById(R.id.date);
        subject = (TextView) findViewById(R.id.subject);
        sender = (TextView) findViewById(R.id.sender);
        message = (TextView) findViewById(R.id.message);
        date.setText(Date);
        subject.setText(Subject);
        sender.setText(Sender);
        message.setText(Message);

        reply = (Button) findViewById(R.id.reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent("android.intent.action.sendmessage");
                m.putExtra("uid", Sender);
                startActivity(m);
                finish();
            }
        });

    }
}

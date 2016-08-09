package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;

public class RegistrationPayment extends AppCompatActivity {
    Intent i;
    Firebase fb, fbuser;
    Button save, paymentNotification, participantProfile;
    TextView name;
    EditText amount;
    CheckBox paidFlag;
    Participant participant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_payment);
        Firebase.setAndroidContext(this);
        i=getIntent();
        fb = new Firebase("https://nittequora.firebaseio.com/events");
        name = (TextView) findViewById(R.id.paiduser);
        amount = (EditText) findViewById(R.id.paidamount);
        paidFlag = (CheckBox) findViewById(R.id.paidflag);

        fb.child(i.getStringExtra("eventKey")+"/participants/"+i.getStringExtra("uid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                participant = dataSnapshot.getValue(Participant.class);
                    name.setText(participant.getUser());
                    amount.setText(Integer.toString(participant.getAmount()));
                    paidFlag.setChecked(participant.isPaidFlag());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        save = (Button) findViewById(R.id.paidsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participant p = new Participant();
                p.setPaidFlag(paidFlag.isChecked());
                p.setUid(i.getStringExtra("uid"));
                p.setAmount(Integer.parseInt(amount.getText().toString()));
                p.setUser(participant.getUser());
                fb.child(i.getStringExtra("eventKey")+"/participants/"+i.getStringExtra("uid")).setValue(p);
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });

        paymentNotification = (Button) findViewById(R.id.paidsendnotification);
        paymentNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.setSubject(i.getStringExtra("eventName")+" Registration");
                String date = DateFormat.getDateTimeInstance().format(new Date());
                msg.setDate(date);
                msg.setSender(Application.getUsn(getApplicationContext()));
                msg.setMessage("You Have Successfully Paid Rs."+amount.getText().toString()+" and got Registerd to "+i.getStringExtra("eventName")+" at "+date);
                fbuser = new Firebase("https://nittequora.firebaseio.com/users");
                fbuser.child(i.getStringExtra("uid")+"/messages/").push().setValue(msg);
            }
        });

        participantProfile = (Button) findViewById(R.id.paiduserprofile);
        participantProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent("android.intent.action.profile");
                p.putExtra("uid", i.getStringExtra("uid"));
                startActivity(p);
                finish();
            }
        });

    }

}

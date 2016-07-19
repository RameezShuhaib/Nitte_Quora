package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class EventRegistration extends AppCompatActivity {
    TextView eventName, description, date;
    Participant p;
    Button register;
    Firebase fb, fbuser;
    Intent data;
    boolean regFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_event_registration);
        fb = new Firebase("https://nittequora.firebaseio.com/events");
        fbuser = new Firebase("https://nittequora.firebaseio.com/users");
        data = getIntent();
        eventName = (TextView) findViewById(R.id.eeventName);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        register = (Button) findViewById(R.id.register);
        eventName.setText(data.getStringExtra("eventname"));
        description.setText(data.getStringExtra("description"));
        String d = data.getStringExtra("startdate")+" to "+data.getStringExtra("enddate");
        date.setText(d);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regFlag){
                    fbuser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            p = new Participant();
                            p.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            p.setUser(dataSnapshot.child("username").getValue().toString());
                            p.setPaidFlag(false);
                            String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            fb.child(data.getStringExtra("eventkey")+"/participants/"+UID).setValue(p);
                            Toast.makeText(getApplicationContext(), "succesfully Registerd to"+data.getStringExtra("eventname"),
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }else{
                    String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    fb.child(data.getStringExtra("eventkey")+"/participants/"+UID).removeValue();
                    Toast.makeText(getApplicationContext(), data.getStringExtra("eventname") + " Registration Cancelled",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        fb.child(data.getStringExtra("eventkey")+"/participants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    register.setText("Cancell Registration");
                    regFlag = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

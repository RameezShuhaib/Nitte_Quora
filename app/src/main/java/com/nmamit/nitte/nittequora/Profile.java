package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    String uid;
    ImageView books, events;
    LinearLayout myevent, leftpanel, sendmessage;
    TextView userName, usn, branch, bio, sec, sem, gender, profileTitle;
    Intent i;
    User u;
    Firebase fb;
    LinearLayout addEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button logout = (Button) findViewById(R.id.signout);

        leftpanel = (LinearLayout) findViewById(R.id.leftpanel);
        sendmessage = (LinearLayout) findViewById(R.id.SendMessage);
        userName = (TextView) findViewById(R.id.pname);
        usn = (TextView) findViewById(R.id.pusn);
        branch = (TextView) findViewById(R.id.pbranch);
        bio = (TextView) findViewById(R.id.pbio);
        sec = (TextView) findViewById(R.id.psec);
        sem = (TextView) findViewById(R.id.psem);
        gender = (TextView) findViewById(R.id.pgender);
        profileTitle = (TextView) findViewById(R.id.profiletitle);
        i = getIntent();
        if(i.getStringExtra("uid") != null)
            uid = i.getStringExtra("uid");
        else
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myevent = (LinearLayout) findViewById(R.id.myevents);
        myevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.myEvent"));
            }
        });

        if (logout != null) {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    finish();

                }
            });
        }

        ImageView editProfile = (ImageView) findViewById(R.id.editProfile);
        addEvent = (LinearLayout) findViewById(R.id.addEvent);
        if (editProfile != null) {
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent("android.intent.action.editprofile");
                    startActivity(i);
                    finish();
                }
            });
        }

        if (addEvent != null) {
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent("android.intent.action.addevent"));
                }
            });
        }

        books = (ImageView) findViewById(R.id.books);
        events = (ImageView) findViewById(R.id.events);
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
                Intent i = new Intent("android.intent.action.eventlist");
                startActivity(i);
                finish();
            }
        });
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/users");
        fb.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(User.class);
                userName.setText(u.getUsername());
                usn.setText(u.getUsn());
                branch.setText(u.getBranch());
                bio.setText(u.getBio());
                sec.setText(u.getSec());
                sem.setText(u.getSem());
                gender.setText(u.getGender());
                userProfile();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent("android.intent.action.sendmessage");
                m.putExtra("uid", i.getStringExtra("uid"));
                startActivity(m);
            }
        });
    }

    void userProfile(){
        if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)){
            profileTitle.setText(u.getUsername());
            leftpanel.setVisibility(View.GONE);
            myevent.setVisibility(View.GONE);
        }else{
            sendmessage.setVisibility(View.GONE);
            if(!u.isEventAdminFlag())
                addEvent.setVisibility(View.GONE);
        }

    }


}

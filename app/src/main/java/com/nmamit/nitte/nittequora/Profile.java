package com.nmamit.nitte.nittequora;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    ImageView books, events, messages, pic;
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
        pic = (ImageView) findViewById(R.id.pic);
        i = getIntent();



        if(i.getStringExtra("uid") != null) {
            uid = i.getStringExtra("uid");
        }
        else {
            uid = Application.getUsn(getApplicationContext());
        }




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
                    try {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        finish();
                    }
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
        messages = (ImageView) findViewById(R.id.messages);

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

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.Notification"));
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

//        Button b = (Button) findViewById(R.id.Make_me_admin);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fb.child(Application.getUsn(getApplicationContext())+"/eventAdminFlag").setValue(true);
//            }
//        });
    }

    void userProfile(){
        if(!Application.getUsn(this).equals(uid)){
            profileTitle.setText(u.getUsername());
            leftpanel.setVisibility(View.GONE);
            myevent.setVisibility(View.GONE);
        }else{
            try {
                new ImageLoadTask(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), pic).execute();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            sendmessage.setVisibility(View.GONE);
            if(!u.isEventAdminFlag())
                addEvent.setVisibility(View.GONE);
        }

    }


}

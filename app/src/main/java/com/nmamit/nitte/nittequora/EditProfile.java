package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.View;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    TextView userName, password, branch, bio, sec, sem;
    Firebase fb;
    User u;
    Map<String, Object> update = new HashMap<>();
    boolean gender=false;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Firebase.setAndroidContext(this);
        fb = new Firebase("https://nittequora.firebaseio.com/users");
        u = new User();
        userName = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        branch = (TextView) findViewById(R.id.branch);
        bio = (TextView) findViewById(R.id.bio);
        sec = (TextView) findViewById(R.id.sec);
        sem = (TextView) findViewById(R.id.sem);
        save = (Button) findViewById(R.id.save);


        save.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
//                if(!userName.getText().equals("") && !branch.getText().equals("") && !sec.getText().equals("") && !sem.getText().equals("")){
                    u.setUsername(userName.getText().toString());
                    u.setBio(bio.getText().toString());
                    u.setBranch(branch.getText().toString());
                    u.setSec(sec.getText().toString());
                    u.setSem(sem.getText().toString());
                    u.setUserId(Application.getUsn(getApplicationContext()));
                    u.setUsn(Application.getUsn(getApplicationContext()));
                    u.setPassword(password.getText().toString());
                    u.setGender(gender? "female":"male");
                    fb.child(Application.getUsn(getApplicationContext())).setValue(u);
                    startActivity(new Intent("android.intent.action.profile"));
                    finish();
//                }
            }
        });

        fb.child(Application.getUsn(getApplicationContext())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userName.setText(dataSnapshot.getValue(User.class).getUsername());
                    password.setText(dataSnapshot.getValue(User.class).getPassword());
                    branch.setText(dataSnapshot.getValue(User.class).getBranch());
                    bio.setText(dataSnapshot.getValue(User.class).getBio());
                    sec.setText(dataSnapshot.getValue(User.class).getSec());
                    sem.setText(dataSnapshot.getValue(User.class).getSem());
                    u.setEventAdminFlag(dataSnapshot.getValue(User.class).isEventAdminFlag());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void onSelected(android.view.View v){
       if(v.getId() == R.id.male){
           gender = false;
       }
    }
}

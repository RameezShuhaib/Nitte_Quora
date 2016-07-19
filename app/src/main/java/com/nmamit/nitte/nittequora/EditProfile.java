package com.nmamit.nitte.nittequora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.View;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    TextView userName, usn, branch, bio, sec, sem;
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
        usn = (TextView) findViewById(R.id.usn);
        branch = (TextView) findViewById(R.id.branch);
        bio = (TextView) findViewById(R.id.bio);
        sec = (TextView) findViewById(R.id.sec);
        sem = (TextView) findViewById(R.id.sem);
        save = (Button) findViewById(R.id.save);


        save.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
//                if(!userName.getText().equals("")&&!bio.getText().equals("")&&!branch.getText().equals("")&&!sec.getText().equals("")&&!sem.getText().equals("")&&!usn.getText().equals("")){
                if(true){
                    u.setUsername(userName.getText().toString());
                    u.setBio(bio.getText().toString());
                    u.setBranch(branch.getText().toString());
                    u.setSec(sec.getText().toString());
                    u.setSem(sem.getText().toString());
                    u.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    u.setUsn(usn.getText().toString());
                    u.setGender(gender? "female":"male");
                    fb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(u);
                    startActivity(new Intent("android.intent.action.profile"));
                    finish();
                }
            }
        });

    }

    public void onSelected(android.view.View v){
       if(v.getId() == R.id.male){
           gender = false;
       }
    }
}

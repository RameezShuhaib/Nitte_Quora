package com.nmamit.nitte.nittequora;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.Event;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by rameezshuhaib on 13-07-2016.
 */
public class Application {
    private SharedPreferences sharedPreferences;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static String getUsn(Context context) {
        return getPrefs(context).getString("usn", "");
    }

    public static void setUsn(Context context, String usn) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("usn", usn);
        editor.commit();
    }
}


class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}




class Books{
    String title, author, user;
    boolean existFlag;



    public Books() {
        this.user = null;
        this.title = null;
        this.author = null;
        this.existFlag = false;
    }

    public Books(String title, String author, String user, boolean existFlag) {
        this.title = title;
        this.author = author;
        this.user = user;
        this.existFlag = existFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isExistFlag() {
        return existFlag;
    }

    public void setExistFlag(boolean existFlag) {
        this.existFlag = existFlag;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

class  Participant{
    String user, uid;
    int amount;
    boolean paidFlag;

    public Participant() {
        this.paidFlag = false;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(boolean paidFlag) {
        this.paidFlag = paidFlag;
    }

    @Override
    public String toString() {
        return user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class Events{
    String eventName, description, startDate, endDate, adminUser, eventKey;
    Map<String, Participant> participants;

    public Events(String eventName, String description, String startDate, String endDate, String adminUser) {
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adminUser = adminUser;
    }

    public Events() {

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminName) {
        this.adminUser = adminName;
    }


    @Override
    public String toString() {
        return eventName;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }


    public Map<String, Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Participant> participants) {
        this.participants = participants;
    }
}


class User{
    String userId, username, usn, branch, gender, bio, sem, sec, password;
    Map<String, Message> messages;
    boolean eventAdminFlag;
    public User() {
        eventAdminFlag = false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }

    public boolean isEventAdminFlag() {
        return eventAdminFlag;
    }

    public void setEventAdminFlag(boolean eventAdminFlag) {
        this.eventAdminFlag = eventAdminFlag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Message{
    String sender, subject, message, date;
    boolean readFlag;

    public Message() {
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReadFlag() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }

    public String toString() {
        return subject;
    }

}
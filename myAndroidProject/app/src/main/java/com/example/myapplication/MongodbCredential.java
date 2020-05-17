package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MongodbCredential {
    public MongodbCredential() { }

    String Name;
    String LastName;
    String UserName;
    String Email;
    String Password;
    String Password2;
    String AlertUser;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
       Name = name;
        if(name == null ){
            setAlertUser("Name is null");
            throw new NullPointerException("Name can't be null");
        }
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
        if(lastName == null ){
            setAlertUser("LastName is null");
            throw new NullPointerException("Last name can't be null");
        }
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
        if(userName == null ){
            setAlertUser("UserName is null");
            throw new NullPointerException("User name can't be null");
        }
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
        if(email == null ){
            setAlertUser("Email is Null");
            throw new NullPointerException("Email can't be null");
        }
        /*if(!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
            throw new NullPointerException("Email is invalid, try again!");
        }*/
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
        if(password == null ){
            setAlertUser("Password Is Null");
            throw new NullPointerException("Name can't be null");
        }
    }

    public String getPassword2() {
        return Password2;
    }

    public void setPassword2(String password2) throws Exception {

        Password2 = password2;
        if(!password2.equals(getPassword())){
            setAlertUser("Password not Match");
            throw new Exception("Wrong password, try again!");
        }
    }

    public String getAlertUser() {
        return AlertUser;
    }

    public void setAlertUser(String alertUser) {
        AlertUser = alertUser;
    }

    public void ShowCredential () {
        List<Document> docs = new ArrayList<>();
        final StitchAppClient client =
                Stitch.initializeDefaultAppClient("toandroid-uotho");

        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("sensorData").getCollection("sensors");

        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                new Continuation<StitchUser, Task<RemoteInsertOneResult>>() {

                    @Override
                    public Task<RemoteInsertOneResult> then(@NonNull Task<StitchUser> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Login failed!");
                            throw Objects.requireNonNull(task.getException());
                        }

                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult().getId()
                        );
                        Document newItem = new Document()
                                .append("name", Name)
                                .append("lastname", LastName)
                                .append("username", UserName)
                                .append("email", Email)
                                .append("password", Password);


                        final Task <RemoteInsertOneResult> insertTask = coll.insertOne(newItem);
                        insertTask.addOnCompleteListener(new OnCompleteListener <RemoteInsertOneResult> () {
                            @Override
                            public void onComplete(@NonNull Task <RemoteInsertOneResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("app", String.format("successfully inserted item with id %s",
                                            task.getResult().getInsertedId()));
                                } else {
                                    Log.e("app", "failed to insert document with: ", task.getException());
                                }
                            }
                        });


                        return insertTask;

                    }

                });
    }}
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

import org.bson.Document;

import java.io.IOException;
import java.util.Objects;

public class GetUser {

    GetUser() {super();}


    String UserName;
    String Password;
    String ValidateUser;
    String BadLogin;
    String newValue;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
        if(userName == null){
            setValidateUser("User Name cannot be Empty");
            throw new NullPointerException("Name can not be null");
        }
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
        if(password == null){
            setValidateUser("Password cannot be Empty");
            throw new NullPointerException("Password can not be null");
        }
    }

    public String getBadLogin() {
        return BadLogin;
    }

    public void setBadLogin(String badLogin) {
        BadLogin = badLogin;
    }

    public String getValidateUser() {
        return ValidateUser;
    }

    public void setValidateUser(String validateUser) {
        ValidateUser = validateUser;
    }

    public void  GetMongoUserData() throws IOException {

        final StitchAppClient client = Stitch.initializeDefaultAppClient("toandroid-uotho");

        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("sensorData").getCollection("sensors");

        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                new Continuation<StitchUser, Task<Document>>() {

                    @Override
                    public Task<Document> then(@NonNull Task<StitchUser> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Login failed!");
                            throw Objects.requireNonNull(task.getException());
                        }
                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult().getId()
                       );
                        //getData

                        Document document1 = new Document()
                                .append("$eq", UserName);
                        Document document2 = new Document()
                                .append("$eq", Password);

                        Document query = new Document()
                                .append("username", document1 )
                                .append("password", document2 );

                        Task <Document> th = coll.findOne(query);

                        th.addOnCompleteListener(new OnCompleteListener<Document>() {
                            @Override
                            public void onComplete(@NonNull Task <Document> task) {
                                if (task.getResult() == null) {
                                    setBadLogin("Login failed, try again!");
                                    Log.d("app", String.format("No document matches the provided query"));
                                }
                                else if (task.isSuccessful()) {
                                    Log.d("app", String.format("Successfully found document: %s",
                                            task.getResult()));
                                } else {
                                    setBadLogin("Email or Password is wrong, try again!");
                                    Log.e("app", "Failed to findOne: ", task.getException());
                                }
                            }
                        });
                        return th;

                    }
                   // client.close();
                });
       // client.close();
    }
}

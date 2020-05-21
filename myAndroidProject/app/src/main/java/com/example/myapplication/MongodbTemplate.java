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

import java.io.IOException;
import java.util.Objects;

public class MongodbTemplate {

    MongodbTemplate(String showResult) {
         this.oneActivity = showResult;
        }
    MongodbTemplate() { }


    public String oneActivity;
    public String saveNewActivity;

    public String getOneActivity() {
        return oneActivity;
    }

    public void setOneActivity(String oneActivity) {
        this.oneActivity = oneActivity;
    }

    public String getSaveNewActivity() {
        return saveNewActivity;
    }

    public void setSaveNewActivity(String saveNewActivity) {
        this.saveNewActivity = saveNewActivity;
    }

    public void mongoLoginTemplate() throws IOException {
       // client = Stitch.initializeDefaultAppClient("toandroid-uotho");
        final StitchAppClient client = Stitch.getAppClient("toandroid-uotho");
        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
        final RemoteMongoCollection<Document> coll= mongoClient.getDatabase("sensorData").getCollection("sensors");

        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                new Continuation<StitchUser, Task<RemoteInsertOneResult>>() {

                    @Override
                    public Task<RemoteInsertOneResult> then(@NonNull Task<StitchUser> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Login failed!");
                            throw Objects.requireNonNull(task.getException());
                        }
                        GetUser gtu = new GetUser();

                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult().getId()
                        );
                        Document newItem = new Document()
                                .append("username", saveNewActivity)
                                .append("newactivity", oneActivity);

                        final Task <RemoteInsertOneResult> insertTask = coll.insertOne(newItem);
                        insertTask.addOnCompleteListener(new OnCompleteListener<RemoteInsertOneResult>() {
                            @Override
                            public void onComplete(@NonNull Task <RemoteInsertOneResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("app", String.format("successfully inserted newActivity with id %s",
                                            task.getResult().getInsertedId()));
                                } else {
                                    Log.e("app", "failed to insert document with: ", task.getException());
                                }
                            }
                        });
                        return insertTask;
                    }
                });
       // client.close();
    }
    }


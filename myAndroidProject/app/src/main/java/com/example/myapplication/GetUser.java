package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;

import java.util.Objects;

public class GetUser {
    public GetUser() {}


    String Name;
    Object foundUser;

    public Object getFoundUser() {
        return foundUser;
    }

    public void setFoundUser(Object foundUser) {
        this.foundUser = foundUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void  GetMongoUserData(){
        final StitchAppClient client =
                Stitch.initializeDefaultAppClient("toandroid-uotho");

        final RemoteMongoClient mongoClient =
                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

        final RemoteMongoCollection<Document> coll =
                mongoClient.getDatabase("sensorData").getCollection("sensors");

        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                new Continuation<StitchUser, Task<RemoteUpdateResult>>() {

                    @Override
                    public Task<RemoteUpdateResult> then(@NonNull Task<StitchUser> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Login failed!");
                            throw Objects.requireNonNull(task.getException());
                        }
                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult().getId()
                       );
                        //getData

                        Document document1 = new Document().append("$eq", Name);
                        Document query = new Document().append("Name", document1 );
                        coll.findOne(query);
                        updateDoc.append("Name",Name);

                        return coll.updateOne(null, updateDoc, new RemoteUpdateOptions().upsert(true));

                    }
                });

    }


}

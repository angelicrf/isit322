package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterUser extends AppCompatActivity {
    MongodbCredential mc = new MongodbCredential();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);

        Button toMongo = (Button) findViewById(R.id.buttonregist);
        TextView showEmailuser = (TextView) findViewById(R.id.userShow);
        EditText userName = (EditText) findViewById(R.id.Namebtn);
        EditText userLastName = (EditText) findViewById(R.id.LNbtn);
        EditText userEmail = (EditText) findViewById(R.id.EMUser);
        EditText userPasswordv = (EditText) findViewById(R.id.PsUs);
        EditText userVerify = (EditText) findViewById(R.id.RePasUs);
        toMongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNameUser = userName.getText().toString();
                String getLastNameUSer = userLastName.getText().toString();
                String getEmailUser = userEmail.getText().toString();
                String getPasswordUser = userPasswordv.getText().toString();

                mc.setName(getNameUser);
                mc.setLastName(getLastNameUSer);
                mc.setEmail(getEmailUser);
                mc.setPassword(getPasswordUser);
                mc.ShowCredential();

                showEmailuser.setText(getEmailUser);
            }
        });


    }
}

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
        EditText userNameCr = (EditText) findViewById(R.id.userNameText);
        EditText userSecondPassword = (EditText) findViewById(R.id.RePasUs);

        toMongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNameUser = userName.getText().toString();
                String getLastNameUSer = userLastName.getText().toString();
                String getUserName = userNameCr.getText().toString();
                String getEmailUser = userEmail.getText().toString();
                String getPasswordUser = userPasswordv.getText().toString();
                String getPassword_2 = userSecondPassword.getText().toString();

                mc.setName(getNameUser);
                mc.setLastName(getLastNameUSer);
                mc.setUserName(getUserName);
                mc.setEmail(getEmailUser);
                mc.setPassword(getPasswordUser);
                try {
                    mc.setPassword2(getPassword_2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String findAlert = mc.getAlertUser();
                if(findAlert != null){
                    showEmailuser.setText(String.format("%s There is an error! ", findAlert));
                }
                else {
                    mc.ShowCredential();
                    showEmailuser.setText(String.format("%s Welcome to DeviceDetector! ", getUserName));
                }
            }
        });


    }
}

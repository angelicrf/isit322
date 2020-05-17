package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    GetUser gu = new GetUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysecond);
        getWindow().getDecorView().setBackgroundColor(Color.GRAY);

        Button submitbtn = (Button) findViewById(R.id.loginbtn1);
        submitbtn.setOnClickListener(new View.OnClickListener(){


            EditText UserNameText = (EditText) findViewById(R.id.UserName);
            EditText PassText = (EditText) findViewById(R.id.PasswordText);

            TextView displayText = (TextView) findViewById(R.id.display);


            @Override
            public void onClick(View view){
                String getUserName = UserNameText.getText().toString();
                String getPass = PassText.getText().toString();
                gu.setUserName(getUserName);
                gu.setPassword(getPass);
                Object rgUser = gu.getUserName();
                String showEmpty = gu.getValidateUser();
                String showBadLog = gu.getBadLogin();
                if(showEmpty != null){
                    displayText.setText(String.format("Login Failed Error Found %s", showEmpty.toString()));
                }
                else{
                    gu.GetMongoUserData();
                    if(showBadLog == null){
                    displayText.setText(String.format("Welcome Back %s", rgUser.toString()));
                    }else{
                        displayText.setText(String.format("Login Failed %s", showBadLog));
                    }
                }


              }

    });
}
}
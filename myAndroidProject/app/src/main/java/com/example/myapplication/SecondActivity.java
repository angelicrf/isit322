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


            EditText NameText = (EditText) findViewById(R.id.UserName);
            EditText passwordText = (EditText) findViewById(R.id.PasswordText);
            TextView displayText = (TextView) findViewById(R.id.display);


            @Override
            public void onClick(View view){
                String getName = NameText.getText().toString();
                String getPassword = passwordText.getText().toString();
                gu.setName(getName);
                gu.GetMongoUserData();
                Object rgUser = gu.getName();
                displayText.setText(rgUser.toString());

              }

    });
}
}
package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.CYAN);

        Button loginbtn = (Button) findViewById(R.id.button2);
        Button registerbtn = (Button) findViewById(R.id.registerbtn);

        loginbtn.setOnClickListener(new View.OnClickListener(){

           @Override
            public void onClick(View view){

               Intent secondPage = new Intent(getApplicationContext(), SecondActivity.class);
               startActivity(secondPage);

           }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(registerPage);
            }
        });






    }




}

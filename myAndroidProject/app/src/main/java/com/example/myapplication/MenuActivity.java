package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button WDeviceDetect = (Button) findViewById(R.id.wddtbtn);
        Button DeepScan = (Button) findViewById(R.id.dsbtn);
        Button InfraCamera = (Button) findViewById(R.id.infrcbtn);
        Button Instructions = (Button) findViewById(R.id.instrbtn);
        Button ContactUs = (Button) findViewById(R.id.contactbtn);
        TextView messageToUser = (TextView) findViewById(R.id.menuTextView);

        WDeviceDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = MenuActivity.this;

                WirelessDeviceDetect wdt = new WirelessDeviceDetect();
                try {
                    GetUser gtu = new GetUser();
                    wdt.saveNewActivity = gtu.getUserName();
                    wdt.RunSensorToDetect(mContext);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                int displayRes_1 = wdt.Rssi;
                int displayRes_2 = wdt.level;
                String displayRes_3 = wdt.showResult;

                messageToUser.setText(String.format("RSSI: %dLevel: %dBSSID: %s", displayRes_1, displayRes_2, displayRes_3));
            }
        });
    }

}

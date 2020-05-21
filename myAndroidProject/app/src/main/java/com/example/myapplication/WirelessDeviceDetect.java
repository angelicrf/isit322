package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WirelessDeviceDetect extends MongodbTemplate{

    WirelessDeviceDetect(String showResult){
        super(showResult);
    }
    WirelessDeviceDetect() {
        super();
    }

    private Context mContext;
    private WifiManager wifiManager;

    int Rssi;
    int level;
    JSONObject saveMongo = new JSONObject();
    String showResult;



    public void RunSensorToDetect(Context context) throws JSONException, IOException {

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connManager != null;
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo  wifiinfo;
        assert wifiManager != null;
        wifiinfo = wifiManager.getConnectionInfo();
        assert networkInfo != null;
        assert wifiManager != null;
        if(wifiManager.isWifiEnabled()) {
            wifiManager.startScan();
            if (networkInfo.isConnected()) {
                assert wifiManager != null;
                if (wifiinfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    showResult = wifiinfo.getBSSID();
                    Rssi = wifiManager.getConnectionInfo().getRssi();
                    level = WifiManager.calculateSignalLevel(Rssi, 5);
                    saveMongo.put("level",level);
                    saveMongo.put("rssi", Rssi);
                    saveMongo.put("bssid", showResult);

                    oneActivity = showResult;
                    setOneActivity(showResult);

                    mongoLoginTemplate();
                }
            }
        }


    }

}

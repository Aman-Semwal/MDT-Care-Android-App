package com.example.application7;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BatteryNetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_network);

        TextView tvBattery = findViewById(R.id.tvBatteryInfo);
        TextView tvNetwork = findViewById(R.id.tvNetworkInfo);

        Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        String batteryInfo = getBatteryInfo(batteryStatus);
        String networkInfo = getNetworkInfo();

        tvBattery.setText(batteryInfo);
        tvNetwork.setText(networkInfo);
    }

    private String getBatteryInfo(Intent batteryStatus) {
        if (batteryStatus == null) return "Battery information not available.";

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        float percent = level * 100 / (float) scale;

        String charging = (status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL) ? "Charging" : "Not Charging";

        return "Battery Information\n\n"
                + "Battery Percentage: " + percent + "%\n"
                + "Charging Status: " + charging;
    }

    private String getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return "Network information not available.";

        Network network = cm.getActiveNetwork();
        if (network == null) return "Network Information\n\nNo active network connection.";

        NetworkCapabilities nc = cm.getNetworkCapabilities(network);
        if (nc == null) return "Network Information\n\nUnable to determine network details.";

        String type = "Unknown";
        if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) type = "Wi-Fi";
        else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) type = "Mobile Data";

        return "Network Information\n\nConnection Type: " + type;
    }
}

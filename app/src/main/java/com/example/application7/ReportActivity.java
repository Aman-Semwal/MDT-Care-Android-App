package com.example.application7;

import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class ReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        TextView tv = findViewById(R.id.tvReport);

        String report = "===== MOBILE DIAGNOSTIC REPORT =====\n\n"
                + getDeviceInfo() + "\n\n"
                + getBatteryInfo() + "\n\n"
                + getNetworkInfo() + "\n\n"
                + getStorageInfo() + "\n\n"
                + getSensorInfo() + "\n\n"
                + "Overall Status: Device diagnostics completed successfully.";

        tv.setText(report);
    }

    private String getDeviceInfo() {
        return "Device Info:\n"
                + "Model: " + Build.MODEL + "\n"
                + "Brand: " + Build.BRAND + "\n"
                + "Android Version: " + Build.VERSION.RELEASE + "\n"
                + "SDK Level: " + Build.VERSION.SDK_INT;
    }

    private String getBatteryInfo() {
        Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus == null) return "Battery Info: Not available";

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        float percent = level * 100 / (float) scale;

        String charging = (status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL) ? "Charging" : "Not Charging";

        return "Battery Info:\nBattery Level: " + percent + "%\nStatus: " + charging;
    }

    private String getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return "Network Info: Not available";

        Network network = cm.getActiveNetwork();
        if (network == null) return "Network Info:\nNo active network connection";

        NetworkCapabilities nc = cm.getNetworkCapabilities(network);
        if (nc == null) return "Network Info:\nUnable to determine connection";

        String type = "Unknown";
        if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) type = "Wi-Fi";
        else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) type = "Mobile Data";

        return "Network Info:\nConnection Type: " + type;
    }

    private String getStorageInfo() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSizeLong();
        long total = stat.getBlockCountLong() * blockSize;
        long available = stat.getAvailableBlocksLong() * blockSize;

        return "Storage Info:\nTotal Internal Storage: " + formatSize(total)
                + "\nAvailable Internal Storage: " + formatSize(available);
    }

    private String getSensorInfo() {
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        return "Sensor Availability:\n"
                + "Accelerometer: " + check(sm, Sensor.TYPE_ACCELEROMETER) + "\n"
                + "Gyroscope: " + check(sm, Sensor.TYPE_GYROSCOPE) + "\n"
                + "Proximity: " + check(sm, Sensor.TYPE_PROXIMITY) + "\n"
                + "Light: " + check(sm, Sensor.TYPE_LIGHT) + "\n"
                + "Magnetometer: " + check(sm, Sensor.TYPE_MAGNETIC_FIELD);
    }

    private String check(SensorManager sm, int type) {
        return sm.getDefaultSensor(type) != null ? "Supported" : "Not Supported";
    }

    private String formatSize(long size) {
        double kb = size / 1024.0, mb = kb / 1024.0, gb = mb / 1024.0;
        if (gb >= 1) return String.format("%.2f GB", gb);
        if (mb >= 1) return String.format("%.2f MB", mb);
        if (kb >= 1) return String.format("%.2f KB", kb);
        return size + " Bytes";
    }
}
package com.example.application7;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeviceInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        TextView tv = findViewById(R.id.tvDeviceInfo);

        String info = "Device Information\n\n"
                + "Brand: " + Build.BRAND + "\n"
                + "Manufacturer: " + Build.MANUFACTURER + "\n"
                + "Model: " + Build.MODEL + "\n"
                + "Device: " + Build.DEVICE + "\n"
                + "Product: " + Build.PRODUCT + "\n"
                + "Board: " + Build.BOARD + "\n"
                + "Hardware: " + Build.HARDWARE + "\n"
                + "Android Version: " + Build.VERSION.RELEASE + "\n"
                + "SDK Level: " + Build.VERSION.SDK_INT;

        tv.setText(info);
    }
}
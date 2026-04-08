package com.example.application7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDeviceInfo).setOnClickListener(v ->
                startActivity(new Intent(this, DeviceInfoActivity.class)));

        findViewById(R.id.btnBatteryNetwork).setOnClickListener(v ->
                startActivity(new Intent(this, BatteryNetworkActivity.class)));

        findViewById(R.id.btnSensorTest).setOnClickListener(v ->
                startActivity(new Intent(this, SensorTestActivity.class)));

        findViewById(R.id.btnStorage).setOnClickListener(v ->
                startActivity(new Intent(this, StorageActivity.class)));

        findViewById(R.id.btnReport).setOnClickListener(v ->
                startActivity(new Intent(this, ReportActivity.class)));
    }
}
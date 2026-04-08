package com.example.application7;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SensorTestActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor currentSensor;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

        tv = findViewById(R.id.tvSensorOutput);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        findViewById(R.id.btnAccelerometer).setOnClickListener(v -> selectSensor(Sensor.TYPE_ACCELEROMETER, "Accelerometer"));
        findViewById(R.id.btnGyroscope).setOnClickListener(v -> selectSensor(Sensor.TYPE_GYROSCOPE, "Gyroscope"));
        findViewById(R.id.btnProximity).setOnClickListener(v -> selectSensor(Sensor.TYPE_PROXIMITY, "Proximity"));
        findViewById(R.id.btnLight).setOnClickListener(v -> selectSensor(Sensor.TYPE_LIGHT, "Light"));
        findViewById(R.id.btnMagnetometer).setOnClickListener(v -> selectSensor(Sensor.TYPE_MAGNETIC_FIELD, "Magnetometer"));
    }

    private void selectSensor(int type, String name) {
        if (currentSensor != null) sensorManager.unregisterListener(this);

        currentSensor = sensorManager.getDefaultSensor(type);

        if (currentSensor == null) {
            tv.setText(name + " Sensor: Not Supported on this device.");
        } else {
            tv.setText(name + " Sensor: Available\n\nWaiting for values...");
            sensorManager.registerListener(this, currentSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String text = "Sensor Name: " + event.sensor.getName() + "\n\n";
        for (int i = 0; i < event.values.length; i++) {
            text += "Value " + (i + 1) + ": " + event.values[i] + "\n";
        }
        tv.setText(text);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
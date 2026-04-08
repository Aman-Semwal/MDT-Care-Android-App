package com.example.application7;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class StorageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        TextView tv = findViewById(R.id.tvStorageInfo);

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSizeLong();
        long total = stat.getBlockCountLong() * blockSize;
        long available = stat.getAvailableBlocksLong() * blockSize;
        long used = total - available;

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        if (am != null) am.getMemoryInfo(mi);

        String info = "Storage and Memory Information\n\n"
                + "Total Internal Storage: " + formatSize(total) + "\n"
                + "Used Internal Storage: " + formatSize(used) + "\n"
                + "Available Internal Storage: " + formatSize(available) + "\n\n"
                + "Total RAM: " + formatSize(mi.totalMem) + "\n"
                + "Available RAM: " + formatSize(mi.availMem);

        tv.setText(info);
    }

    private String formatSize(long size) {
        double kb = size / 1024.0, mb = kb / 1024.0, gb = mb / 1024.0;
        if (gb >= 1) return String.format("%.2f GB", gb);
        if (mb >= 1) return String.format("%.2f MB", mb);
        if (kb >= 1) return String.format("%.2f KB", kb);
        return size + " Bytes";
    }
}
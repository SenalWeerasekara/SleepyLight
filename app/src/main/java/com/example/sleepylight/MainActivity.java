package com.example.sleepylight;

import static android.Manifest.permission.FOREGROUND_SERVICE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    NumberPicker np1;
    String[] timers;
    static Button btn;
    static Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("count");

        BroadcastReceiver broadcaster = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                service.et1.setText(String.valueOf(intent.getIntExtra("timeRemaining", 0)));
            }
        };

        registerReceiver(broadcaster, intentFilter);

        Intent intent = new Intent(MainActivity.this, service.class);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        service.et1 = findViewById(R.id.et1);
        np1 = findViewById(R.id.numPick);
        timers = getResources().getStringArray(R.array.timers);
        btn = findViewById(R.id.btn_start);
        btn2 = findViewById(R.id.btn3);

        np1.setMinValue(1);
        np1.setMaxValue(14);
        np1.setDisplayedValues(timers);
        service.et1.setText("10");
        service.counter = 10;

        btn2.setVisibility(View.GONE);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                if (timers[newVal - 1].equals("10s")){
                    service.et1.setText("10");
                    service.counter = 10;
                }
                else if (timers[newVal - 1].equals("30s")){
                    service.et1.setText("30");
                    service.counter = 30;
                }
                else if (timers[newVal - 1].equals("45s")){
                    service.et1.setText("45");
                    service.counter = 45;
                }
                else if (timers[newVal - 1].equals("1m")){
                    service.et1.setText("60");
                    service.counter = 60;
                }
                else if (timers[newVal - 1].equals("2m")){
                    service.et1.setText("120");
                    service.counter = 120;
                }
                else if (timers[newVal - 1].equals("3m")){
                    service.et1.setText("180");
                    service.counter = 180;
                }
                else if (timers[newVal - 1].equals("4m")){
                    service.et1.setText("240");
                    service.counter = 240;
                }
                else if (timers[newVal - 1].equals("5m")){
                    service.et1.setText("300");
                    service.counter = 300;
                }
                else if (timers[newVal - 1].equals("10m")){
                    service.et1.setText("600");
                    service.counter = 600;
                }
                else if (timers[newVal - 1].equals("15m")){
                    service.et1.setText("900");
                    service.counter = 900;
                }
                else if (timers[newVal - 1].equals("20m")){
                    service.et1.setText("1200");
                    service.counter = 1200;
                }
                else if (timers[newVal - 1].equals("30m")){
                    service.et1.setText("1800");
                    service.counter = 1800;
                }
                else if (timers[newVal - 1].equals("45m")){
                    service.et1.setText("2700");
                    service.counter = 2700;
                }
                else if (timers[newVal - 1].equals("60m")){
                    service.et1.setText("3600");
                    service.counter = 3600;
                }
                else{
                    service.et1.setText("1");
                    service.counter = 1;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(intent);

                btn2.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.tt.cancel();
                stopService(intent);
                flashlight(false);

                Toast.makeText(getApplicationContext(),"stiooping",Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
            }
        });
    }

    public void flashlight(boolean input){
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                CameraManager camMan = (CameraManager) getSystemService(CAMERA_SERVICE);
                String camID = null;
                try {
                    camID = camMan.getCameraIdList()[0];
                    camMan.setTorchMode(camID, input);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "This Version of Android is too old", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "No Flash", Toast.LENGTH_LONG).show();
        }
    }

}





























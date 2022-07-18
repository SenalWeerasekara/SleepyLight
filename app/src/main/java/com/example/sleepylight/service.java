package com.example.sleepylight;

import android.app.Service;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class service extends Service {

    static EditText et1;
    public static int counter;
    static TimerTask tt;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        flashlight(true);
        Toast.makeText(getApplicationContext(),"torch",Toast.LENGTH_SHORT).show();

        try {
            counter = Integer.parseInt(et1.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        Timer timer = new Timer();
        long startTime = 0;
        long offsetTime = 1000;

        tt = new TimerTask() {
            @Override
            public void run() {
                Intent intent2 = new Intent();
                intent2.setAction("count");
                counter--;
                if (counter <= 0){
                    timer.cancel();
                    flashlight(false);
                    MainActivity.btn.setVisibility(View.VISIBLE);
                    MainActivity.btn2.setVisibility(View.GONE);
                }

                intent2.putExtra("timeRemaining", counter );
                sendBroadcast(intent2);
            }
        };

        try {
            timer.scheduleAtFixedRate(tt, startTime, offsetTime);
        } catch (Exception e) {

        }
        return super.onStartCommand(intent, flags, startId);
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

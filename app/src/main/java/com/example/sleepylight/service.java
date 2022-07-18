package com.example.sleepylight;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import androidx.core.app.NotificationCompat;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class service extends Service {

    private static final String CHANNEL_ID = "NotificationChannelID";

    static EditText et1;
    public static int counter;
    static TimerTask tt;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        flashlight(true);
        Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_SHORT).show();
        Notification(counter);

        Timer timer = new Timer();
        long startTime = 0;
        long offsetTime = 1000;

        tt = new TimerTask() {
            @Override
            public void run() {
                Intent intent2 = new Intent();
                intent2.setAction("count");
                counter--;
//                Notification(counter);

                if (counter <= 0){
                    timer.cancel();
                    flashlight(false);
//                    MainActivity.btn.setVisibility(View.VISIBLE);
//                    MainActivity.btn2.setVisibility(View.GONE);
//                    stopSelf();
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

    public void Notification(Integer timer){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                try{
                    Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
                    Intent notificationIntent = new Intent(this, MainActivity.class);
                    final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                    final Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setContentTitle("My Stop Watch")
                            .setContentText("Time Remain" + timer)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentIntent(pendingIntent)
                            .build();
                    startForeground(1, notification);


                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Counter Service", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(notificationChannel);
//                return START_STICKY;
                }
                catch (Exception e){

                }
            }
    }
}

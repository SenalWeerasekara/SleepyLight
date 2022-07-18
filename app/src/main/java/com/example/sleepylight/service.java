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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.concurrent.ScheduledExecutorService;

public class service extends Service {
    

    public static CountDownTimer cdt1;
    static EditText et1;
    public static int counter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flashlight(true);
        Toast.makeText(getApplicationContext(),"torch",Toast.LENGTH_SHORT).show();

        try {
            counter = Integer.parseInt(et1.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        cdt1 = new CountDownTimer(600000, 1000){
            public void onTick(long millisUntilFinished){
                et1.setText(String.valueOf(counter));
                counter--;

                if (counter < 0){
                    flashlight(false);
                    cdt1.cancel();
                }
            }
            public  void onFinish(){
//                        textView.setText("FINISH!!");
            }
        }.start();



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

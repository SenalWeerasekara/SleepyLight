package com.example.sleepylight;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText et1;
    NumberPicker np1;
    String[] timers;
    Button btn;
    Button btn2;
    public int counter;
    public CountDownTimer cdt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = findViewById(R.id.et1);
        np1 = findViewById(R.id.numPick);
        timers = getResources().getStringArray(R.array.timers);
        btn = findViewById(R.id.btn_start);
        btn2 = findViewById(R.id.btn3);



        np1.setMinValue(1);
        np1.setMaxValue(14);
        np1.setDisplayedValues(timers);
        et1.setText("10");
        counter = 10;

        btn2.setVisibility(View.GONE);
//        stopButton.setVisibility(View.VISIBLE);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                if (timers[newVal - 1].equals("10s")){
                    et1.setText("10");
                    counter = 10;
                }
                else if (timers[newVal - 1].equals("30s")){
                    et1.setText("30");
                    counter = 30;
                }
                else if (timers[newVal - 1].equals("45s")){
                    et1.setText("45");
                    counter = 45;
                }
                else if (timers[newVal - 1].equals("1m")){
                    et1.setText("60");
                    counter = 60;
                }
                else if (timers[newVal - 1].equals("2m")){
                    et1.setText("120");
                    counter = 120;
                }
                else if (timers[newVal - 1].equals("3m")){
                    et1.setText("180");
                    counter = 180;
                }
                else if (timers[newVal - 1].equals("4m")){
                    et1.setText("240");
                    counter = 240;
                }
                else if (timers[newVal - 1].equals("5m")){
                    et1.setText("300");
                    counter = 300;
                }
                else if (timers[newVal - 1].equals("10m")){
                    et1.setText("600");
                    counter = 600;
                }
                else if (timers[newVal - 1].equals("15m")){
                    et1.setText("900");
                    counter = 900;
                }
                else if (timers[newVal - 1].equals("20m")){
                    et1.setText("1200");
                    counter = 1200;
                }
                else if (timers[newVal - 1].equals("30m")){
                    et1.setText("1800");
                    counter = 1800;
                }
                else if (timers[newVal - 1].equals("45m")){
                    et1.setText("2700");
                    counter = 2700;
                }
                else if (timers[newVal - 1].equals("60m")){
                    et1.setText("3600");
                    counter = 3600;
                }
                else{
                    et1.setText("1");
                    counter = 1;
                }
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
                flashlight(true);

                try {
                    counter = Integer.parseInt(et1.getText().toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                cdt1 = new CountDownTimer(30000, 1000){
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
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdt1.cancel();
                btn.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
                try {
                counter = Integer.parseInt(et1.getText().toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                flashlight(false);
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
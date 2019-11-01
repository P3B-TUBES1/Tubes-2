package com.example.tubes2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IMainActivity, View.OnTouchListener , View.OnClickListener, SensorEventListener {
    private ImageView im;
    private Canvas mCanvas;
    private Presenter presenter;
    private Paint paint;
    Bitmap bmPlayer;
    Bitmap bmEnemy1;
    Bitmap bmEnemy2;
    Button btn_gyro;
    boolean gyro_toggle = false;
    SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    float[] accelerometerReading = new float[3];
    float[] magnetometerReading = new float[3];
    float roll;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = this.findViewById(R.id.canvas);
        this.btn_gyro = findViewById(R.id.button_gyro);
        this.btn_gyro.setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onWindowFocusChanged(boolean focus) {
        super.onWindowFocusChanged(focus);
        initiateAll();
    }

    private void initiateAll() {
        Bitmap bitMap = Bitmap.createBitmap(im.getWidth(), im.getHeight(), Bitmap.Config.ARGB_8888);
        bmPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        bmEnemy1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemygreen);
        bmEnemy2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemyred);
        this.im.setImageBitmap(bitMap);
        this.mCanvas = new Canvas(bitMap);
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        int background = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        mCanvas.drawColor(background);
        this.im.invalidate();
        this.presenter = new Presenter(this, im.getWidth(), im.getHeight());//initialize presenter
        this.im.setOnTouchListener(this);
    }

    @Override
    public void drawPlayer(int x, int y) {
        int background = Color.BLACK;
        mCanvas.drawColor(background); // berfungsi untuk reset layar
        //this.mCanvas.drawRect(new Rect(x-30,y-30,x+30,y+30),paint);
        mCanvas.drawBitmap(bmPlayer, null, new Rect(x, y , x + 100, y + 100), null);
    }

    @Override
    public void drawBullet(int x, int y) {
        this.mCanvas.drawRect(new Rect(x - 0, y - 0, x + 2, y + 50), paint);
    }

    @Override
    public void drawEnemy(int x, int y, int type) {
        //this.mCanvas.drawRect(new Rect(x-50,y-50,x+50,y+50),paint);
        switch (type) {
            case 0:
                mCanvas.drawBitmap(bmEnemy1, null, new Rect(x , y, x + 200, y + 128), null);
                break;
            case 1:
                mCanvas.drawBitmap(bmEnemy2, null, new Rect(x , y, x + 200, y + 128), null);
                break;
        }
    }

    @Override
    public void resetCanvas() {
        this.im.invalidate();
    }

    @Override
    public void drawPowerUp(int x, int y) {
        this.mCanvas.drawCircle(x, y, 5, paint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                float x = e.getX();
                float y = e.getY();
                this.presenter.movePlayer(x, y);
                break;
            case MotionEvent.ACTION_UP:
                this.presenter.stopMovePlayer();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btn_gyro.getId()){
            if(gyro_toggle){
                this.btn_gyro.setText("OFF");
                this.gyro_toggle = false;
            }
            else{
                this.btn_gyro.setText("ON");
                this.gyro_toggle = true;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(this.accelerometer != null){
            this.mSensorManager.registerListener(this,this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(this.magnetometer != null){
            this.mSensorManager.registerListener(this,this.magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        switch(sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                this.accelerometerReading = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetometerReading = sensorEvent.values.clone();
                break;
        }
        final float[] rotationMatrix = new float[9];
        mSensorManager.getRotationMatrix(rotationMatrix,null,accelerometerReading,magnetometerReading);

        final float[] orientationAngles = new float[3];
        mSensorManager.getOrientation(rotationMatrix,orientationAngles);

        final float VALUE_DRIFT = 0.05f;

        this.roll = orientationAngles[2];

        if(Math.abs(roll) < VALUE_DRIFT){
            roll =0;
        }

        if(gyro_toggle){
            if(roll<0){
                this.presenter.movePlayer(im.getWidth()/2-1,0);
            }
            else if(roll>0){
                this.presenter.movePlayer(im.getWidth()/2+1,0);
            }
            else{
                this.presenter.stopMovePlayer();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

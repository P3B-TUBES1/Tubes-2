package com.example.tubes2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IMainActivity,View.OnTouchListener{
    private ImageView im;
    private Canvas mCanvas;
    private Presenter presenter;
    private Paint paint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = this.findViewById(R.id.canvas);
    }
    @Override
    public void onWindowFocusChanged(boolean focus){
        super.onWindowFocusChanged(focus);
        initiateAll();
    }
    private void initiateAll() {

        Bitmap bitMap = Bitmap.createBitmap(im.getWidth(),im.getHeight(), Bitmap.Config.ARGB_8888);
        this.im.setImageBitmap(bitMap);
        this.mCanvas = new Canvas(bitMap);
        this.paint = new Paint();
        int background = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        mCanvas.drawColor(background);
        this.im.invalidate();
        this.presenter = new Presenter(this,im.getWidth(),im.getHeight());//initialize presenter
        this.im.setOnTouchListener(this);

    }

    @Override
    public void drawPlayer(int[] n,int[][] n2) {
        int background = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        mCanvas.drawColor(background); // berfungsi untuk reset layar
        this.mCanvas.drawRect(new Rect(n[0]-25,n[1]-25,n[0]+25,n[1]+25),paint);
        drawBullet(n2);
        this.im.invalidate();

    }

    @Override
    public void drawBullet(int[][] n2) {
        for(int i=0;i<n2[0].length;i++) {
            this.mCanvas.drawRect(new Rect(n2[0][i] - 10, n2[1][i] - 10, n2[0][i] + 10
                    , n2[1][i] + 50), paint);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch(e.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                float x = e.getX();
                float y = e.getY();
                this.presenter.movePlayer(x,y);
                break;
            case MotionEvent.ACTION_UP:
                this.presenter.stopMovePlayer();
                break;
        }
        return true;
    }
}

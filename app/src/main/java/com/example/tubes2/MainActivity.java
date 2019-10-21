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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IMainActivity, View.OnTouchListener{
    private ImageView im;
    private Canvas mCanvas;
    private boolean isInitiated = false;
    private Presenter presenter;
    private Paint paint;
    private CustomGestureDetector customGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = this.findViewById(R.id.canvas);
    }
    @Override
    public void onWindowFocusChanged(boolean focus){
        super.onWindowFocusChanged(focus);
        initiateCanvas();
    }
    private void initiateCanvas() {

        Bitmap bitMap = Bitmap.createBitmap(im.getWidth(),im.getHeight(), Bitmap.Config.ARGB_8888);
        this.im.setImageBitmap(bitMap);
        this.mCanvas = new Canvas(bitMap);
        this.paint = new Paint();
        int background = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        mCanvas.drawColor(background);
        this.im.invalidate();
        this.presenter = new Presenter(this,im.getWidth(),im.getHeight());//initialize presenter
        this.customGestureDetector = new CustomGestureDetector(this.presenter);
        this.im.setOnTouchListener(this);
    }

    @Override
    public void drawPlayer(int x,int y) {
        this.mCanvas.drawRect(new Rect(x-25,y-25,x+25,y+25),paint);
        this.im.invalidate();
    }

    /*
     untuk implementasi gesture ontouchup
     */
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch(e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                float x, y;
                x = e.getX();
                y = e.getY();
                this.presenter.movePlayer(x, y);
                Log.d("action down","test");
            }
            case MotionEvent.ACTION_UP:{
                this.presenter.stopMovePlayer();
            }
        }
        return true;
    }
}

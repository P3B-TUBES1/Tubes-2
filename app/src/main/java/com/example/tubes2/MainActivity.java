package com.example.tubes2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MainActivity extends AppCompatActivity implements IMainActivity, View.OnTouchListener {
    private ImageView im;
    private Canvas mCanvas;
    private Presenter presenter;
    private Paint paint;
    Bitmap bmPlayer;
    Bitmap bmEnemy1;
    Bitmap bmEnemy2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = this.findViewById(R.id.canvas);
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
}

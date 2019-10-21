package com.example.tubes2;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private Presenter presenter;
    public CustomGestureDetector(Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public boolean onDown(MotionEvent e){
        float x,y;
        x = e.getX();
        y = e.getY();
        this.presenter.movePlayer(x,y);
        return true;
    }

}

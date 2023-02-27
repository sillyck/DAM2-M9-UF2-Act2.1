package com.example.gira;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener
{
    private Context context;
    private int fingersDown = 0;

    public MyGestureListener(Context context)
    {
        this.context = context;
    }

    /**
     * @param e The down motion event
     */
    @Override
    public void onShowPress(@NonNull MotionEvent e)
    {
//        super.onShowPress(e);
        if(fingersDown==3)
        {
            // Trigger the three-fingers-down gesture
            Toast.makeText(context/*getApplicationContext()*/, "Three fingers down", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return
     */
    @Override
    public boolean onDown(MotionEvent event)
    {
        fingersDown = event.getPointerCount();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (fingersDown == 3 && distanceY < 0){
            // Trigger three-fingers-down scrolling gesture
//            scrollView.scrollBy(0, -50);
            Toast.makeText(context/*getApplicationContext()*/, "SCROLL DOWN!", Toast.LENGTH_SHORT).show();

        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }
}
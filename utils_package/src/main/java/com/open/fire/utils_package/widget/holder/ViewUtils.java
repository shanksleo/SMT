package com.open.fire.utils_package.widget.holder;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-12-18
 */
public class ViewUtils {
    public static void  gestureHand(){
        GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };
    }
}

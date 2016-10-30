package videira.ifc.edu.br.georent.listeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

import videira.ifc.edu.br.georent.interfaces.ViewFlipperOnSwipeListener;

/**
 * Created by root on 30/10/16.
 */

public class ViewFlipperTouchListener implements ViewFlipper.OnTouchListener{

    private Context mContext;
    private GestureDetector mGestureDetector;
    private ViewFlipperOnSwipeListener mViewFlipperOnSwipeListener;

    public ViewFlipperTouchListener(Context context, final ViewFlipper recyclerView, ViewFlipperOnSwipeListener viewFlipperOnSwipeListener) {

        this.mContext = context;
        this.mViewFlipperOnSwipeListener = viewFlipperOnSwipeListener;
        this.mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("Fling","onFling()");

                if(Math.abs(e1.getY()-e2.getY()) > 250)
                    return false;
                if(e1.getX() - e2.getX() > 120 && Math.abs(velocityX) > 200){
                    Log.d("Fling", "Move Next");
                    mViewFlipperOnSwipeListener.onSwipeRight();
                }
                else if(e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200){
                    Log.d("Fling", "Move Previous");
                    mViewFlipperOnSwipeListener.onSwipeLeft();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("LOG", "onTouch()");
        mGestureDetector.onTouchEvent(event);
        return false;
    }
}

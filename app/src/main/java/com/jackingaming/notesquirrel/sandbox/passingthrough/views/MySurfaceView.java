package com.jackingaming.notesquirrel.sandbox.passingthrough.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.InputManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.views.threads.MySurfaceViewUpdaterThread;

public class MySurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {
    public interface MySurfaceViewSurfaceChangeListener {
        void onMySurfaceViewSurfaceChanged(MySurfaceView mySurfaceView, SurfaceHolder surfaceHolder, int format, int widthScreen, int heightScreen);
    }
    private MySurfaceViewSurfaceChangeListener mySurfaceViewSurfaceChangeListener;
    public void setMySurfaceViewSurfaceChangeListener(MySurfaceViewSurfaceChangeListener mySurfaceViewSurfaceChangeListener) {
        this.mySurfaceViewSurfaceChangeListener = mySurfaceViewSurfaceChangeListener;
    }

    public interface MySurfaceViewTouchListener {
        boolean onMySurfaceViewTouched(MySurfaceView mySurfaceView, MotionEvent event);
    }
    private MySurfaceViewTouchListener mySurfaceViewTouchListener;
    public void setMySurfaceViewTouchListener(MySurfaceViewTouchListener mySurfaceViewTouchListener) {
        this.mySurfaceViewTouchListener = mySurfaceViewTouchListener;
    }

    private MySurfaceViewUpdaterThread runner;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + " constructor");

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".surfaceCreated(SurfaceHolder holder)");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".surfaceChanged(SurfaceHolder holder, int format, int width, int height): (" + holder + ", " + format + ", " + width + ", " + height +").");
        mySurfaceViewSurfaceChangeListener.onMySurfaceViewSurfaceChanged(this, holder, format, width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onTouchEvent(MotionEvent event)");
        return mySurfaceViewTouchListener.onMySurfaceViewTouched(this, event);
    }

    public void runGame(Game game, InputManager inputManager) {
        runner = new MySurfaceViewUpdaterThread(game, inputManager);
        ///////////////
        runner.start();
        ///////////////
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".surfaceDestroyed(SurfaceHolder holder)");
        shutdownMySurfaceViewUpdaterThread();
    }

    private void shutdownMySurfaceViewUpdaterThread() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".shutdownMySurfaceViewUpdaterThread()");

        if (runner != null) {
            runner.shutdown();

            while (runner != null) {
                try {
                    runner.join();
                    runner = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
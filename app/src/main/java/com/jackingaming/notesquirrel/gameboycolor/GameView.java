package com.jackingaming.notesquirrel.gameboycolor;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.pong.PongCartridge;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.PoohFarmerCartridge;

public class GameView extends SurfaceView
        implements SurfaceHolder.Callback {

    private GameCartridge gameCartridge;
    private GameRunner runner;

    private int widthScreen;
    private int heightScreen;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
    }

    /**
     * System's callback method when the user triggers a touch event.
     *
     * @param event The touch event's meta-data (e.g. x and y position
     *              of the user triggered touch event)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameCartridge.getInput(event);

        // If you've handled the touch event, return true.
        // If false, will NOT check for drag event (can't drag without touch).
        return true;
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceChanged(SurfaceHolder, int, int, int)");
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     *
     * When the surface is create, I want to start my GameRunner.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceCreated(SurfaceHolder)");

        widthScreen = getWidth();
        heightScreen = getHeight();

        gameCartridge = new PoohFarmerCartridge(getContext(), holder, getResources(), widthScreen, heightScreen);
        //gameCartridge = new PongCartridge(getContext(), holder, getResources(), widthScreen, heightScreen);
        runner = new GameRunner(gameCartridge);
        // Tell the Thread class to go to the "public void run()" method.
        runner.start();
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceDestroyed(SurfaceHolder)");

        if (runner != null) {
            runner.shutdown();

            /*
            NOW: want to wait for the thread to stop drawing.

            Want to NOT allow the phone to go to another application until
            this runner has stopped drawing... because if it goes to another
            application, the surface will no longer exist yet the thread will
            still be try to renderGame on it which will cause it to CRASH.
             */
            while (runner != null) {
                // This method waits for the thread to terminate.
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
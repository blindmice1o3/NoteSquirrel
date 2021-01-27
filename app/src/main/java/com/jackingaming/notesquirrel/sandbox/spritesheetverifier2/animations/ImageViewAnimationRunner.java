package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.animations;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;

import java.util.List;

public class ImageViewAnimationRunner implements Runnable {

    private boolean looping;
    private List<Bitmap> userSelectedBitmaps;
    private ImageView imageView;

    public ImageViewAnimationRunner(boolean looping, List<Bitmap> userSelectedBitmaps, ImageView imageView) {
        this.looping = looping;
        this.userSelectedBitmaps = userSelectedBitmaps;
        this.imageView = imageView;
    }

    public void shutdown() {
        looping = false;
    }

    @Override
    public void run() {
        int index = 0;
        int speed = 3_000;
        long lastTime = System.currentTimeMillis();
        long timer = 0L;

        while (looping) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;

            timer += elapsed;

            Log.d(MainActivity.DEBUG_TAG, "timer: " + timer);
            if (timer > speed) {
                index++;
                timer = 0;

                if (index >= userSelectedBitmaps.size()) {
                    index = 0;
                }

                Log.d(MainActivity.DEBUG_TAG, "WHILE LOOP index: " + index);
                imageView.setImageBitmap(userSelectedBitmaps.get(index));
            }

            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

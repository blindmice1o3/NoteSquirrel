package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

public class Car extends Creature {

    public enum Type { PINK, WHITE, YELLOW, BIG_RIG; }

    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Type type;

    private Bitmap image;

    public Car(Handler handler, float xCurrent, float yCurrent,
               Direction direction, Type type) {
        super(handler, xCurrent, yCurrent);

        //Do NOT allow Direction.UP or Direction.DOWN (default is Direction.RIGHT).
        if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
            this.direction = direction;
        } else {
            this.direction = Direction.RIGHT;
        }

        this.type = type;

        gameCamera = handler.getGameCartridge().getGameCamera();
        widthPixelToViewportRatio = ((float) handler.getGameCartridge().getWidthViewport()) /
                gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) handler.getGameCartridge().getHeightViewport()) /
                gameCamera.getHeightClipInPixel();

        init();
    }

    @Override
    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "Car.init()");

        initImage();
        adjustSizeAndBounds();
    }

    private void adjustSizeAndBounds() {
        Log.d(MainActivity.DEBUG_TAG, "Car.adjustSizeAndBounds()");

        //TODO: WORK-AROUND (FROGGER TILE_WIDTH and TILE_HEIGHT)
        if (handler.getGameCartridge().getIdGameCartridge() == GameCartridge.Id.FROGGER) {
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;

            //ADJUST width, height, and bounds.
            switch (type) {
                case PINK:
                case WHITE:
                case YELLOW:
                    width = 1 * tileWidthFrogger;
                    break;
                case BIG_RIG:
                    width = 2 * tileWidthFrogger;
                    break;
            }
            height = 1 * tileHeightFrogger;
            bounds = new Rect(0, 0, width, height);
        }
    }

    private void initImage() {
        if (direction == Direction.RIGHT) {
            switch (type) {
                case PINK:
                    image = Assets.flipHorizontally(Assets.carPinkLeft);
                    break;
                case WHITE:
                    image = Assets.carWhiteRight;
                    break;
                case YELLOW:
                    image = Assets.flipHorizontally(Assets.carYellowLeft);
                    break;
                case BIG_RIG:
                    image = Assets.flipHorizontally(Assets.bigRigLeft);
                    break;
            }
        } else if (direction == Direction.LEFT) {
            switch (type) {
                case PINK:
                    image = Assets.carPinkLeft;
                    break;
                case WHITE:
                    image = Assets.flipHorizontally(Assets.carWhiteRight);
                    break;
                case YELLOW:
                    image = Assets.carYellowLeft;
                    break;
                case BIG_RIG:
                    image = Assets.bigRigLeft;
                    break;
            }
        }
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Rect bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect screenRect = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}
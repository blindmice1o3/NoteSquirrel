package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.moveable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class Log extends Creature {

    public enum Size { LARGE, MEDIUM, SMALL; }

    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Size size;

    transient private Bitmap image;

    public Log(GameCartridge gameCartridge, float xCurrent, float yCurrent,
               Direction direction, Size size) {
        super(gameCartridge, xCurrent, yCurrent);

        //Do NOT allow Direction.UP or Direction.DOWN (default is Direction.RIGHT).
        if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
            this.direction = direction;
        } else {
            this.direction = Direction.RIGHT;
        }

        this.size = size;

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        android.util.Log.d(MainActivity.DEBUG_TAG, "Log.init(GameCartridge)");
        this.gameCartridge = gameCartridge;

        gameCamera = gameCartridge.getGameCamera();
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCamera.getHeightClipInPixel();

        initImage();
        initBounds();
    }

    @Override
    public void initBounds() {
        android.util.Log.d(MainActivity.DEBUG_TAG, "Log.initBounds()");

        //TODO: WORK-AROUND (FROGGER TILE_WIDTH and TILE_HEIGHT)
        if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.FROGGER) {
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;

            //ADJUST width, height, and bounds.
            switch (size) {
                case LARGE:
                    width = Assets.logLarge.getWidth();
                    moveSpeed = 2f;
                    break;
                case MEDIUM:
                    width = Assets.logMedium.getWidth();
                    moveSpeed = 3f;
                    break;
                case SMALL:
                    width = Assets.logSmall.getWidth();
                    moveSpeed = 4f;
                    break;
            }
            height = 1 * tileHeightFrogger;
            bounds = new Rect(0, 0, width, height);
        }
    }

    private void initImage() {
        if (direction == Direction.RIGHT) {
            switch (size) {
                case LARGE:
                    image = Assets.logLarge;
                    break;
                case MEDIUM:
                    image = Assets.logMedium;
                    break;
                case SMALL:
                    image = Assets.logSmall;
                    break;
            }
        } else if (direction == Direction.LEFT) {
            switch (size) {
                case LARGE:
                    image = Assets.flipHorizontally(Assets.logLarge);
                    break;
                case MEDIUM:
                    image = Assets.flipHorizontally(Assets.logMedium);
                    break;
                case SMALL:
                    image = Assets.flipHorizontally(Assets.logSmall);
                    break;
            }
        }
    }

    @Override
    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : gameCartridge.getSceneManager().getCurrentScene().getEntityManager().getEntities()) {
            //if the entity calling checkEntityCollision(float, float) finds ITSELF in the collection, skip by continue.
            if (e.equals(this)) {
                continue;
            }

            //check EACH entity to see if their collision bounds INTERSECTS with yours.
            if (e.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))) {
                //Frog can walk on Log instances.
                ////////////////////////
                if (e instanceof Player) {
                    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    e.setxCurrent( (e.getxCurrent() + xOffset) );
                    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                    //@@@@@@@@@@%
                    return false;
                    //@@@@@@@@@@@
                } else {
                    return true;
                }
                ////////////////////////
            }
        }

        return false;
    }

    @Override
    public void update(long elapsed) {
        xMove = 0;
        yMove = 0;

        switch (direction) {
            case LEFT:
                if ( (xCurrent-moveSpeed) > 0 ) {
                    xMove = -moveSpeed;
                } else {
                    active = false;
                }

                break;
            case RIGHT:
                int widthSceneMax = gameCartridge.getSceneManager().getCurrentScene().getTileMap().getWidthSceneMax();
                if ( (xCurrent+width+moveSpeed) < widthSceneMax ) {
                    xMove = moveSpeed;
                } else {
                    active = false;
                }

                break;
            default:
                android.util.Log.d(MainActivity.DEBUG_TAG, "Log.update(long) switch (direction)'s default.");
                break;
        }

        move(direction);
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
package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class CropEntity extends Entity {

    public enum Id { TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }
    public enum Stage { ONE, TWO, THREE, HARVESTABLE; }

    private CropEntity.Id id;

    private Stage stage;
    private boolean isWatered;
    transient private Bitmap image;

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public CropEntity(GameCartridge gameCartridge, CropEntity.Id id, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        this.id = id;

        stage = Stage.ONE;
        isWatered = false;

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
        initBounds();
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}
package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;

public class SceneManager {

    private Context context;
    private int widthViewport;
    private int heightViewport;
    private GameCartridge.Id cartridgeID;

    private Scene currentScene;

    public SceneManager(Context context, int widthViewport, int heightViewport, GameCartridge.Id cartridgeID) {
        this.context = context;
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
        this.cartridgeID = cartridgeID;

        init();
    }

    private void init() {
        switch (cartridgeID) {
            case POOH_FARMER:
                currentScene = new Scene(context, widthViewport, heightViewport, Scene.Id.FARM);
                break;
            case POCKET_CRITTERS:
                currentScene = new Scene(context, widthViewport, heightViewport, Scene.Id.PART_01);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager.init() switch construct's default block.");
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

}
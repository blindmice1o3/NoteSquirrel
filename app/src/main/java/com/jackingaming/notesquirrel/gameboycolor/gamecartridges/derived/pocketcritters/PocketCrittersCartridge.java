package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class PocketCrittersCartridge
        implements GameCartridge {

    private Context context;
    private Id idGameCartridge;

    private SurfaceHolder surfaceHolder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Handler handler;
    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;
    private StateManager stateManager;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        Assets.init(context);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        handler = new Handler(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        gameCamera = new GameCamera(handler);
        player = new Player(handler);
        sceneManager = new SceneManager(handler);
        /////////////////////////////////////////
        stateManager = new StateManager(handler);
        /////////////////////////////////////////

        //TODO: if isReturningFromActivity... then load
        if ( ((JackInActivity)context).isReturningFromActivity() ) {
            loadSavedState();

            ((JackInActivity)context).setReturningFromActivity(false);
        }
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.savePresentState()");

        SerializationDoer.saveWriteToFile(this, false);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.loadSavedState()");

        // !!!THIS CHECKING FOR NULL IS NECESSARY!!!
        if (handler != null) {
            SerializationDoer.loadReadFromFile(this, false);
        }
    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            stateManager.getCurrentState().getInputViewport();
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            stateManager.getCurrentState().getInputDirectionalPad();
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
           stateManager.getCurrentState().getInputButtonPad();
        }
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        //////////////////////////

        stateManager.getCurrentState().update(elapsed);
    }

    @Override
    public void render() {
        stateManager.getCurrentState().render();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public StateManager getStateManager() {
        return stateManager;
    }

}
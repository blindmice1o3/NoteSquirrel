package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.content.Context;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface IGameCartridge {

    enum Id { POCKET_CRITTERS, POOH_FARMER, PONG, FROGGER; }

    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen);

    public void savePresentState();
    public void loadSavedState();

    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();
    public void getInputSelectButton();
    public void getInputStartButton();

    public void update(long elapsed);
    public void render();

    public Context getContext();
    public Id getIdGameCartridge();
    public SurfaceHolder getSurfaceHolder();
    public InputManager getInputManager();
    public int getWidthViewport();
    public int getHeightViewport();

    public Player getPlayer();
    public void setPlayer(Player player);
    public GameCamera getGameCamera();
    public void setGameCamera(GameCamera gameCamera);
    public HeadUpDisplay getHeadUpDisplay();
    public void setHeadUpDisplay(HeadUpDisplay headUpDisplay);
    public SceneManager getSceneManager();
    public StateManager getStateManager();
    public TimeManager getTimeManager();
    public void setTimeManager(TimeManager timeManager);

}

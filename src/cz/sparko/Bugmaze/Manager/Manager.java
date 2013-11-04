package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Model.GameData;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.scene.Scene;

public abstract class Manager {
    protected Game game;
    protected Scene scene;
    protected static ResourceHandler resourceHandler;
    protected static GameData gameData;

    protected Manager(Game game) {
        this.game = game;
        resourceHandler = game.getResourceHandler();
    }

    public static void setGameData(GameData gameData) {
        Manager.gameData = gameData;
    }
    public static GameData getGameData() { return Manager.gameData; }
    public static ResourceHandler getResourceHandler() { return resourceHandler; }

    public static void setResourceHandler(ResourceHandler resourceHandler) { Manager.resourceHandler = resourceHandler; }

    public Scene getScene() { setScene(); return scene; }
    protected abstract void setScene();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onSwitchOff();
    public abstract void onSwitchOn();
    public abstract void onKeyDown(int keyCode, KeyEvent event);
}

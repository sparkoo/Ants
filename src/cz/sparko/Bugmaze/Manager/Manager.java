package cz.sparko.Bugmaze.Manager;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.scene.Scene;

public abstract class Manager {

    protected Game game;
    protected Scene scene;
    protected ResourceHandler resourceHandler;

    protected Manager(Game game) {
        this.game = game;
        resourceHandler = ResourceHandler.getInstance();
    }

    public Scene getScene() { setScene(); return scene; }
    protected abstract void setScene();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onSwitchOff();
    public abstract void onSwitchOn();
}

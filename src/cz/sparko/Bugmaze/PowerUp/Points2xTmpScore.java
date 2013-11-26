package cz.sparko.Bugmaze.PowerUp;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Points2xTmpScore extends PowerUp {
    private static final int TIMER = 2;

    public Points2xTmpScore(Game game) {
        super((ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.POWER_UP_BUTTON));
    }

    @Override
    public void action() {
        GameManager.getInstance().multiplyTmpScore(2);
        GameManager.getInstance().printScore();
    }

    @Override
    protected int getTimeToRefresh() {
        return TIMER;
    }
}

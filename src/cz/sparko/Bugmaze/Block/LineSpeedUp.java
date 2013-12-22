package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LineSpeedUp extends LineOneWay implements HasAction {
    public LineSpeedUp(Coordinate coordinate, Game game) {
        super(coordinate , (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_LINE_SPEED_UP), game);
    }

    @Override
    public void doActionBefore() {
        if (GameManager.getInstance().getCharacter().getSpeed() >= 0.2f)
            GameManager.getInstance().getCharacter().increaseSpeed(0.1f);
    }

    @Override
    public void doActionAfter() {

    }
}

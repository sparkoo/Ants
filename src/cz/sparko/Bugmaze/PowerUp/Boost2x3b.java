package cz.sparko.Bugmaze.PowerUp;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Boost2x3b extends Boost {

    public Boost2x3b(Game game) {
        super((ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.POWER_UP_BUTTON));
        speedMultiplier = 0.5f;
        blockCount = 3;
    }
}

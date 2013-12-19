package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class CornerMine extends Corner implements HasMine {
    private boolean hasMine = true;
    public CornerMine(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_CORNER_MINE), game, walkThroughs);
    }

    @Override
    public void removeMine() {
        hasMine = false;
    }

    @Override
    public boolean canGetInFrom(Coordinate fromCoordinate) {
        if (hasMine)
            return false;
        return super.canGetInFrom(fromCoordinate);
    }
}

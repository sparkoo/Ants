package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LineOneWay extends Line {
    public LineOneWay(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_LINE_ONEWAY), game, walkThroughs);
    }

    protected LineOneWay(Coordinate coordinate, ITiledTextureRegion texture, Game game, int walkThroughs) {
        super(coordinate, texture, game, walkThroughs);
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays.add(0, Direction.LEFT);
    }

    @Override
    public void setOutWays() {
        outWays.add(0, Direction.RIGHT);
    }
}

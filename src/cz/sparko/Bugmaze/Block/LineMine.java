package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LineMine extends Line implements HasMine {
    public LineMine(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_LINE_MINE), game, walkThroughs);
    }

    @Override
    public boolean canGetInFrom(Coordinate fromCoordinate) {
        return false;
    }

    @Override
    public Block getUnminedBlock(Game game) {
        Block unminedBlock = new Line(coordinate, game, 1);
        for (int i = 0; i < rotateCount; i++)
            unminedBlock.rotate();
        return unminedBlock;
    }
}

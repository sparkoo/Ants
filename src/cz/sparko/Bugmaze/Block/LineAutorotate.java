package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Helper.Direction;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class LineAutoRotate extends Line {
    public LineAutoRotate(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, (ITiledTextureRegion)game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD).getResource(GamefieldTextureResource.BLOCK_LINE_AUTOROTATE), game, walkThroughs);

        registerUpdateHandler(new IUpdateHandler() {
            float rotateTimeCounter = 0;
            @Override
            public void onUpdate(float pSecondsElapsed) {
                rotateTimeCounter += pSecondsElapsed;
                if (!isDeleted() && !isActive() && rotateTimeCounter >= 3) {
                    rotate();
                    rotateTimeCounter = 0;
                }
            }

            @Override
            public void reset() {

            }
        });
    }


}

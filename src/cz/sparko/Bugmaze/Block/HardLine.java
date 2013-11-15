package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HardLine extends Line {
    public HardLine(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, GameManager gameManager, int walkThroughs) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, gameManager, walkThroughs);
    }
    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        return false;
    }
}
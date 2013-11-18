package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Helper.Coordinate;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HardLine extends Line {
    public HardLine(Coordinate coordinate, Game game, int walkThroughs) {
        super(coordinate, game, walkThroughs);
    }
    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        return false;
    }
}

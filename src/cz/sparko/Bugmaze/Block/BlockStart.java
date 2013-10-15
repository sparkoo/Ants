package cz.sparko.Bugmaze.Block;

import cz.sparko.Bugmaze.Ant;
import cz.sparko.Bugmaze.Coordinate;
import cz.sparko.Bugmaze.Direction;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockStart extends Block {
    public BlockStart(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, 1);
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Ant ant) {
        final float centerX = this.getX() + (SIZE / 2) - (Ant.SIZE_X / 2);
        final float centerY = this.getY() + (SIZE / 2) - (Ant.SIZE_Y / 2);

        final float outPositionX = centerX + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(new MoveModifier(ant.getSpeed() / 2, centerX, centerX, centerY, centerY), new MoveModifier(ant.getSpeed() / 2, centerX, outPositionX, centerY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays = new ArrayList<Direction>(0);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Direction>(1);
        outWays.add(0, Direction.RIGHT);
    }
}
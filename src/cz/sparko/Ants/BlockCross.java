package cz.sparko.Ants;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockCross extends Block {
    public BlockCross(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int walkThroughs) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, walkThroughs);
    }

    @Override
    /*
    TODO: same as BlockLine ==> refactor ?
     */
    public SequenceEntityModifier getMoveHandler(Ant ant) {
        final float centerX = this.getX() + (Block.SIZE / 2) - (Ant.SIZE_X / 2);
        final float centerY = this.getY() + (Block.SIZE / 2) - (Ant.SIZE_Y / 2);

        final float sourcePositionX = centerX + ((Block.SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getX());
        final float sourcePositionY = centerY + ((Block.SIZE / 2) * sourceWays.get(wayNo).getCoordinate().getY());
        final float outPositionX = centerX + ((Block.SIZE / 2) * outWays.get(wayNo).getCoordinate().getX());
        final float outPositionY = centerY + ((Block.SIZE / 2) * outWays.get(wayNo).getCoordinate().getY());

        return new SequenceEntityModifier(new MoveModifier(ant.getSpeed(), sourcePositionX, outPositionX, sourcePositionY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays = new ArrayList<Direction>(4);
        sourceWays.add(0, Direction.UP);
        sourceWays.add(1, Direction.DOWN);
        sourceWays.add(2, Direction.LEFT);
        sourceWays.add(3, Direction.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Direction>(4);
        outWays.add(0, Direction.DOWN);
        outWays.add(1, Direction.UP);
        outWays.add(2, Direction.RIGHT);
        outWays.add(3, Direction.LEFT);
    }
}

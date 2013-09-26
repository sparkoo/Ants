package cz.sparko.Ants;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockLine extends Block{
    public BlockLine(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(coordinate ,pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    /*
    TODO: same as BlockCross ==> refactor ?
     */
    public SequenceEntityModifier getMoveHandler(Ant ant) {
        final float centerX = this.getX() + (Block.SIZE / 2) - (Ant.SIZE_X / 2);
        final float centerY = this.getY() + (Block.SIZE / 2) - (Ant.SIZE_Y / 2);

        final float sourcePositionX = centerX + ((Block.SIZE / 2) * directions[sourceWays.get(wayNo)].getX());
        final float sourcePositionY = centerY + ((Block.SIZE / 2) * directions[sourceWays.get(wayNo)].getY());
        final float outPositionX = centerX + ((Block.SIZE / 2) * directions[outWays.get(wayNo)].getX());
        final float outPositionY = centerY + ((Block.SIZE / 2) * directions[outWays.get(wayNo)].getY());

        return new SequenceEntityModifier(new MoveModifier(ant.getSpeed(), sourcePositionX, outPositionX, sourcePositionY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays = new ArrayList<Integer>(2);
        sourceWays.add(0, Block.DOWN);
        sourceWays.add(1, Block.UP);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Integer>(2);
        outWays.add(0, Block.UP);
        outWays.add(1, Block.DOWN);
    }
}

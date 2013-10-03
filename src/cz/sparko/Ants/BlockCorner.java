package cz.sparko.Ants;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockCorner extends Block {
    public BlockCorner(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int walkThroughs) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, walkThroughs);
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Ant ant) {
        final float centerX = this.getX() + (Block.SIZE / 2) - (Ant.SIZE_X / 2);
        final float centerY = this.getY() + (Block.SIZE / 2) - (Ant.SIZE_Y / 2);

        final float sourcePositionX = centerX + ((Block.SIZE / 2) * directions[sourceWays.get(wayNo)].getX());
        final float sourcePositionY = centerY + ((Block.SIZE / 2) * directions[sourceWays.get(wayNo)].getY());
        final float outPositionX = centerX + ((Block.SIZE / 2) * directions[outWays.get(wayNo)].getX());
        final float outPositionY = centerY + ((Block.SIZE / 2) * directions[outWays.get(wayNo)].getY());

        return new SequenceEntityModifier(
                new MoveModifier(ant.getSpeed() / 2, sourcePositionX, centerX, sourcePositionY, centerY),
                new MoveModifier(ant.getSpeed() / 2, centerX, outPositionX, centerY, outPositionY));
    }

    @Override
    public void setPossibleSourceWays() {
        sourceWays = new ArrayList<Integer>(2);
        sourceWays.add(0, Block.DOWN);
        sourceWays.add(1, Block.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Integer>(2);
        outWays.add(0, Block.RIGHT);
        outWays.add(1, Block.DOWN);
    }
}

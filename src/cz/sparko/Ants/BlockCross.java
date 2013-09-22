package cz.sparko.Ants;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockCross extends Block {
    public BlockCross(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public void moveAnt(Ant ant) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pastToNextBlock(Ant ant, Block[][] blocks) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPossibleSourceWays() {
        possibleSourceWays = new ArrayList<Integer>(4);
        possibleSourceWays.add(0, Block.UP);
        possibleSourceWays.add(1, Block.DOWN);
        possibleSourceWays.add(2, Block.LEFT);
        possibleSourceWays.add(3, Block.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Integer>(4);
        outWays.add(0, Block.DOWN);
        outWays.add(1, Block.UP);
        outWays.add(2, Block.RIGHT);
        outWays.add(3, Block.LEFT);
    }
}
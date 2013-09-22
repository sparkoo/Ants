package cz.sparko.Ants;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class BlockCorner extends Block {
    public BlockCorner(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public void moveAnt(Ant ant) {

    }

    @Override
    public void pastToNextBlock(Ant ant, Block[][] blocks) {

    }

    @Override
    public void setPossibleSourceWays() {
        possibleSourceWays = new ArrayList<Integer>(2);
        possibleSourceWays.add(0, Block.DOWN);
        possibleSourceWays.add(1, Block.RIGHT);
    }

    @Override
    public void setOutWays() {
        outWays = new ArrayList<Integer>(2);
        outWays.add(0, Block.RIGHT);
        outWays.add(1, Block.DOWN);
    }
}

package cz.sparko.Ants;

import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BlockStart extends Block {
    public BlockStart(Coordinate coordinate, float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(coordinate, pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public SequenceEntityModifier getMoveHandler(Ant ant) {
        return null;
    }

    @Override
    public void setPossibleSourceWays() {
    }

    @Override
    public void setOutWays() {
    }
}

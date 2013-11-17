package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Block.Corner;
import cz.sparko.Bugmaze.Block.Cross;
import cz.sparko.Bugmaze.Block.Line;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

public abstract class Level {
    private Game game;
    protected Level(Game game) {
        this.game = game;
    }

    public void reachedNextBlock() {

    }

    public Block createRandomBlock(Coordinate coordinate, int posX, int posY, VertexBufferObjectManager vertexBufferObjectManager, TextureResource textureResource) {
        Random rnd = new Random();
        Block nBlock;
        float pickBlock = rnd.nextFloat();
        if (pickBlock < 0.7)
            nBlock = new Corner(coordinate, posX, posY, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.BLOCK_CORNER), vertexBufferObjectManager, 1);
        else if (pickBlock < 0.9)
            nBlock = new Line(coordinate, posX, posY, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.BLOCK_LINE), vertexBufferObjectManager, 1);
        else
            nBlock = new Cross(coordinate, posX, posY, (ITiledTextureRegion)textureResource.getResource(GamefieldTextureResource.BLOCK_CROSS), vertexBufferObjectManager, 2);

        for (int i = 0; i < rnd.nextInt(4); i++)
            nBlock.rotate();

        return nBlock;
    }
}

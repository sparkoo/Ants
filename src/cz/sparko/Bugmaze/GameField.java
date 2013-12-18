package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Resource.GamefieldTextureResource;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Resource.TextureResource;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import java.util.Random;

public class GameField {
    public static final int FIELD_SIZE_X = 9;
    public static final int FIELD_SIZE_Y = 6;
    public static final int Z_INDEX_BACKGROUND = 99;

    private static int START_X = (Game.CAMERA_WIDTH - (GameField.FIELD_SIZE_X * Block.SIZE)) / 2;
    private static int START_Y = (Game.CAMERA_HEIGHT - (GameField.FIELD_SIZE_Y * Block.SIZE)) / 2;

    private Scene scene;
    private Game game;

    private TextureResource textureResource;
    private Sprite background;

    private Block[][] blocks;
    private Coordinate startBlockCoordinate;
    private Block activeBlock;

    private boolean refreshField = false;

    public GameField(Game game, Scene scene) {
        textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD);
        blocks = new Block[GameField.FIELD_SIZE_X][GameField.FIELD_SIZE_Y];
        this.game = game;
        this.scene = scene;
    }

    public Scene getScene() { return scene; }
    public void refreshFieldNeeded() { refreshField = true; }
    public void refreshFieldNotNeeded() { refreshField = false; }
    public boolean isNeedRefreshField() { return refreshField; }
    public Block getActiveBlock() { return activeBlock; }
    public void setActiveBlock(Block nActiveBlock) {
        if (activeBlock != null)
            activeBlock.deactivate();
        activeBlock = nActiveBlock;
        activeBlock.activate();
    }
    public Block getBlock(int x, int y) { return blocks[x][y]; }

    public void putBlock(int x, int y, Block block) {
        putBlock(x, y, block, true);
    }

    public void putBlock(int x, int y, Block block, boolean touchable) {
        scene.detachChild(getBlock(x, y));
        scene.unregisterTouchArea(getBlock(x, y));
        blocks[x][y] = block;
        block.setPosition(START_X + (x * Block.SIZE), START_Y + (y * Block.SIZE));
        scene.attachChild(block);
        if (touchable)
            scene.registerTouchArea(block);
    }

    public void createField(Level level) {
        background = new Sprite(0, 0, textureResource.getResource(GamefieldTextureResource.BACKGROUND), game.getVertexBufferObjectManager());
        background.setZIndex(Z_INDEX_BACKGROUND);
        scene.attachChild(background);

        refreshField(level);

        Random rnd = new Random();
        startBlockCoordinate = new Coordinate(rnd.nextInt(GameField.FIELD_SIZE_X), rnd.nextInt(GameField.FIELD_SIZE_Y));
        putBlock(startBlockCoordinate.getX(), startBlockCoordinate.getY(), Block.createStartBlock(startBlockCoordinate, game), false);

        activeBlock = blocks[startBlockCoordinate.getX()][startBlockCoordinate.getY()];
    }

    public void refreshField(Level level) {
        refreshFieldNotNeeded();
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++)
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++)
                if (getBlock(x, y) == null || (getBlock(x, y).isDeleted() && getBlock(x, y) != getActiveBlock()))
                    putBlock(x, y, level.createRandomBlock(new Coordinate(x, y)));

        for (Block levelBlock : level.getLevelBlocks())
            putBlock(levelBlock.getCoordinate().getX(), levelBlock.getCoordinate().getY(), levelBlock);

        scene.sortChildren();
    }
}

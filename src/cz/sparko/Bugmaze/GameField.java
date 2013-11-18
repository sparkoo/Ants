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
    public static final int Z_INDEX = 99;

    private static int START_X = (Game.CAMERA_WIDTH - (GameField.FIELD_SIZE_X * Block.SIZE)) / 2;
    private static int START_Y = (Game.CAMERA_HEIGHT - (GameField.FIELD_SIZE_Y * Block.SIZE)) / 2;

    private Scene scene;
    private Game game;

    private TextureResource textureResource;
    private Sprite background;

    private Block[][] blocks;
    private Coordinate startBlock;
    private Block activeBlock;

    private boolean refreshField = false;

    public GameField(Game game, Scene scene) {
        textureResource = game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD);
        this.game = game;
        this.scene = scene;
    }

    public Scene getScene() { return scene; }
    public void refreshFieldNeeded() { refreshField = true; }
    public void refreshFieldNotNeeded() { refreshField = false; }
    public boolean isNeedRefreshField() { return refreshField; }
    public Block getActiveBlock() { return activeBlock; }
    public void setActiveBlock(Block nActiveBlock) { activeBlock = nActiveBlock; }
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
        background.setZIndex(Z_INDEX);
        scene.attachChild(background);

        blocks = new Block[GameField.FIELD_SIZE_X][GameField.FIELD_SIZE_Y];
        Random rnd = new Random();
        startBlock = new Coordinate(rnd.nextInt(GameField.FIELD_SIZE_X), rnd.nextInt(GameField.FIELD_SIZE_Y));
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++) {
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++) {
                Block nBlock;
                Coordinate nCoordinate = new Coordinate(x, y);
                if (!nCoordinate.equals(startBlock))
                    nBlock = level.createRandomBlock(nCoordinate);
                else
                    nBlock = Block.createStartBlock(nCoordinate, game);

                putBlock(x, y, nBlock, !nCoordinate.equals(startBlock));
            }
        }

        activeBlock = blocks[startBlock.getX()][startBlock.getY()];
    }

    public void refreshField(Level level) {
        refreshFieldNotNeeded();
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++)
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++)
                if (getBlock(x, y).isDeleted() && getBlock(x, y) != getActiveBlock())
                    putBlock(x, y, level.createRandomBlock(new Coordinate(x, y)));
        scene.sortChildren();
    }
}

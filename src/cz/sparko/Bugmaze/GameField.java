package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Block.Block;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.Random;

public class GameField {
    public static final int FIELD_SIZE_X = 9;
    public static final int FIELD_SIZE_Y = 6;

    private Scene mScene;
    private BaseGameActivity context;

    private Block[][] blocks;
    private Coordinate startBlock;
    private Block activeBlock;

    private boolean refreshField = false;

    public GameField(Scene mScene, BaseGameActivity context) {
        this.mScene = mScene;
        this.context = context;
    }

    public void needRefreshField() {
        refreshField = true;
    }

    public boolean isNeedRefreshField() {
        return refreshField;
    }

    public Block getActiveBlock() {
        return activeBlock;
    }

    public void setActiveBlock(Block nActiveBlock) {
        activeBlock = nActiveBlock;
    }

    public Block getBlock(int x, int y) {
        return blocks[x][y];
    }

    public void createField() {
        int startX = 60;
        int startY = (Game.CAMERA_HEIGHT - (GameField.FIELD_SIZE_Y * Block.SIZE)) / 2;
        blocks = new Block[GameField.FIELD_SIZE_X][GameField.FIELD_SIZE_Y];
        Random rnd = new Random();
        startBlock = new Coordinate(rnd.nextInt(GameField.FIELD_SIZE_X), rnd.nextInt(GameField.FIELD_SIZE_Y));
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++) {
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++) {
                Block nBlock;
                Coordinate nCoordinate = new Coordinate(x, y);
                if (!nCoordinate.equals(startBlock)) {
                    nBlock = Block.createRandomBlockFactory(nCoordinate, startX + (x * Block.SIZE), startY + (y * Block.SIZE), context.getVertexBufferObjectManager());
                    mScene.registerTouchArea(nBlock);
                } else {
                    nBlock = Block.createStartBlockFactory(nCoordinate, startX + (x * Block.SIZE), startY + (y * Block.SIZE), context.getVertexBufferObjectManager());
                }
                blocks[x][y] = nBlock;
                mScene.attachChild(nBlock);
            }
        }

        activeBlock = blocks[startBlock.getX()][startBlock.getY()];
    }

    public void refreshField() {
        int startX = 60;
        int startY = (Game.CAMERA_HEIGHT - (GameField.FIELD_SIZE_Y * Block.SIZE)) / 2;
        refreshField = false;
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++) {
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++) {
                if (blocks[x][y].isDeleted() && blocks[x][y] != activeBlock) {
                    mScene.detachChild(blocks[x][y]);
                    mScene.unregisterTouchArea(blocks[x][y]);
                    Block nBlock = Block.createRandomBlockFactory(new Coordinate(x, y) ,startX + (x * Block.SIZE), startY + (y * Block.SIZE), context.getVertexBufferObjectManager());
                    blocks[x][y] = nBlock;
                    mScene.attachChild(nBlock);
                    mScene.registerTouchArea(nBlock);
                }
            }
        }
        mScene.sortChildren();
    }
}

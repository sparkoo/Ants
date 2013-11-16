package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.GameField;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.entity.scene.Scene;

public abstract class Level {
    private Game game;
    protected Level(Game game) {
        this.game = game;
    }

    public void refreshField(GameField gameField) {
        int startX = (Game.CAMERA_WIDTH - (GameField.FIELD_SIZE_X * Block.SIZE)) / 2;
        int startY = (Game.CAMERA_HEIGHT - (GameField.FIELD_SIZE_Y * Block.SIZE)) / 2;
        Scene scene = gameField.getScene();
        gameField.refreshFieldNotNeeded();
        for (int x = 0; x < GameField.FIELD_SIZE_X; x++) {
            for (int y = 0; y < GameField.FIELD_SIZE_Y; y++) {
                if (gameField.getBlock(x, y).isDeleted() && gameField.getBlock(x, y) != gameField.getActiveBlock()) {
                    scene.detachChild(gameField.getBlock(x, y));
                    scene.unregisterTouchArea(gameField.getBlock(x, y));
                    Block nBlock = Block.createRandomBasicBlockFactory(new Coordinate(x, y), startX + (x * Block.SIZE), startY + (y * Block.SIZE), game.getVertexBufferObjectManager(), game.getResourceHandler().getTextureResource(ResourceHandler.GAMEFIELD));
                    gameField.setBlock(x, y, nBlock);
                    scene.attachChild(nBlock);
                    scene.registerTouchArea(nBlock);
                }
            }
        }
        scene.sortChildren();
    }

    public void reachedNextBlock() {

    }
}

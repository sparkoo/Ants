package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Manager.GameManager;
import org.andengine.engine.handler.IUpdateHandler;

public class GameUpdateHandler implements IUpdateHandler {
    public static final int START_DELAY_SECONDS = 3;

    private GameManager gameManager;
    private GameField gameField;
    private Character character;
    private boolean running = false;
    float timeCounter = 0;
    public GameUpdateHandler(GameField gameField, Character character) {
        this.gameManager = GameManager.getInstance();
        this.gameField = gameField;
        this.character = character;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if (gameField.isNeedRefreshField()) {
            gameField.refreshField();
            gameManager.playRebuildSound();
            gameManager.countScore();
        }
        if (running && timeCounter > character.getSpeed()) {
            timeCounter = 0;
            int currentX = gameField.getActiveBlock().getCoordinate().getX() + gameField.getActiveBlock().getOutCoordinate().getX();
            int currentY = gameField.getActiveBlock().getCoordinate().getY() + gameField.getActiveBlock().getOutCoordinate().getY();
            if (currentX < 0) currentX = GameField.FIELD_SIZE_X - 1;
            if (currentX >= GameField.FIELD_SIZE_X) currentX = 0;
            if (currentY < 0) currentY = GameField.FIELD_SIZE_Y - 1;
            if (currentY >= GameField.FIELD_SIZE_Y) currentY = 0;
            Coordinate nCoordinate = new Coordinate(currentX, currentY);

            if (gameField.getBlock(nCoordinate.getX(), nCoordinate.getY()).canGetInFrom(gameField.getActiveBlock().getCoordinate())) {
                gameField.setActiveBlock(gameField.getBlock(nCoordinate.getX(), nCoordinate.getY()));
                character.registerEntityModifier(gameField.getActiveBlock().getMoveHandler(character));
                gameField.getActiveBlock().delete();
                gameManager.increaseScore();
            } else {
                gameOver();
            }
        } else {
            timeCounter += pSecondsElapsed;
            if (!running && timeCounter > START_DELAY_SECONDS) { //first step after start delay
                running = true;
                character.registerEntityModifier(gameField.getActiveBlock().getMoveHandler(character));
                gameField.getActiveBlock().delete();
                timeCounter = 0;
            }
        }
    }

    private void gameOver() {
        gameManager.saveScore();
        gameManager.gameOver();
    }

    @Override
    public void reset() {
    }
}

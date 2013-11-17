package cz.sparko.Bugmaze;

import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Helper.Coordinate;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.PowerUp.PowerUpNextBlockListener;
import org.andengine.engine.handler.IUpdateHandler;

public class GameUpdateHandler implements IUpdateHandler, PowerUpNextBlockListener {
    public static final int START_DELAY_SECONDS = 3;

    private GameManager gameManager;
    private GameField gameField;
    private cz.sparko.Bugmaze.Character.Character character;
    private boolean running = false;
    float timeCounter = 0;
    private PowerUpNextBlockListener powerUpNextBlockListener;
    private Level level;
    public GameUpdateHandler(GameField gameField, Character character, Level level) {
        this.gameManager = GameManager.getInstance();
        this.gameField = gameField;
        this.character = character;
        this.powerUpNextBlockListener = this;
        this.level = level;
    }

    public void setPowerUpNextBlockListener(PowerUpNextBlockListener powerUpNextBlockListener) {
        this.powerUpNextBlockListener = powerUpNextBlockListener;
    }
    public void unsetPowerUpNextBlockListener() {
        this.powerUpNextBlockListener = this;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if (!GameManager.getInstance().getRunning())
            return;

        if (gameField.isNeedRefreshField() && running) {
            gameField.refreshField(level);
            gameManager.playRebuildSound();
            gameManager.countScore();
        }
        if (running && timeCounter > character.getSpeed()) {
            powerUpNextBlockListener.reachedNextBlock();
            level.reachedNextBlock();

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
        running = false;
        gameManager.stopRunning();
        gameManager.saveScore();
        gameManager.showResultScreen();
    }

    @Override
    public void reset() {
    }

    @Override
    public void reachedNextBlock() {
    }
}

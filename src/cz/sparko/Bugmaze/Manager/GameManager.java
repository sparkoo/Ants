package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Character.LadyBug;
import cz.sparko.Bugmaze.GameField;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Settings;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Level.world1.Level1;
import cz.sparko.Bugmaze.Menu.MenuEnum;
import cz.sparko.Bugmaze.Menu.Pause;
import cz.sparko.Bugmaze.Menu.Results;
import cz.sparko.Bugmaze.Model.Score;
import cz.sparko.Bugmaze.PowerUp.PowerUp;
import cz.sparko.Bugmaze.R;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.LoopModifier;

import java.util.ArrayList;

public class GameManager extends Manager {
    //TODO: we dont want this. Handle instance in Game
    private static GameManager instance;

    private GameUpdateHandler gameUpdateHandler;

    private Pause pauseScene;
    private boolean paused = false;
    private boolean running = false;

    private static Character character = null;
    private static GameField gameField;
    private Level level;

    private boolean playSoundEffects;

    private Text mScoreText;
    private Text mTmpScoreText;
    private Text mCountDownText;
    private Text timerText;
    private long score = 0;
    private long tmpScore = 0;
    private long scoreBase = 0;
    private float runTime = 0;

    private ArrayList<PowerUp> characterPowerUps = new ArrayList<PowerUp>();
    private ArrayList<PowerUp> playerPowerUps = new ArrayList<PowerUp>();

    public static GameManager createInstance(Game game) {
        instance = new GameManager(game);
        return instance;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public GameManager(Game game) {
        super(game);
    }

    public Character getCharacter() { return character; }
    public boolean getRunning() { return running; }
    public void stopRunning() { running = false; }
    public void startRunning() { running = true; }
    public long getScore() { return score; }
    public void setPlaySoundEffects(boolean playSoundEffects) { this.playSoundEffects = playSoundEffects; }

    public void startGame(Level level) {
        score = 0;
        tmpScore = 0;
        runTime = 0;
        this.level = level;
        startRunning();
        game.switchManager(this);
    }

    public void increaseTime(float timeElapsed) {
        runTime += timeElapsed;
        int minutes = (int)(runTime / 60);
        int seconds = (int)(runTime - (minutes * 60));
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public Level getLevel() { return level; }

    public void gameOver() {
        game.switchManager(MenuManager.getInstance());
        GameManager.getGameData().updateCoins(50);
        GameManager.getGameData().save(game);
        MenuManager.getInstance().goToMenu(MenuEnum.MAIN);
    }

    public void showResultScreen() {
        scene.setChildScene(new Results(game.getCamera(), game, score, runTime));
    }

    public void pauseGame() {
        paused = true;
        scene.setChildScene(pauseScene, false, true, true);
    }

    public void unpauseGame() {
        paused = false;
        scene.clearChildScene();
    }

    @Override
    protected void setScene() {
        scene = new Scene();

        pauseScene = new Pause(game.getCamera(), scene, game);

        gameField = new GameField(game, scene);
        gameField.createField(level);

        scene.setBackground(new Background(0.17f, 0.61f, 0f));

        character = new LadyBug(0, 0, game, level.getSpeed());
        character.setStartPosition(gameField.getActiveBlock());

        mScoreText = new Text(10, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("Score: %020d", score), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        mTmpScoreText = new Text((game.CAMERA_WIDTH) / 2 - 100, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("%020d", tmpScore), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        printScore();
        mScoreText.setZIndex(101);
        mTmpScoreText.setZIndex(101);

        timerText = new Text((game.CAMERA_WIDTH) / 2 + 100, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("%02d:%02d", 0, 0), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        timerText.setZIndex(101);

        mCountDownText = new Text(game.CAMERA_WIDTH / 2, game.CAMERA_HEIGHT / 2, game.getResourceHandler().getFontIndieFlower36(), String.format("  "), new TextOptions(HorizontalAlign.CENTER), game.getVertexBufferObjectManager());
        mCountDownText.setZIndex(101);
        mCountDownText.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1f, 1f, 10f), GameUpdateHandler.START_DELAY_SECONDS + 1, new LoopEntityModifier.ILoopEntityModifierListener() {
            @Override
            public void onLoopStarted(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if (pLoop < pLoopCount - 1)
                    mCountDownText.setText(String.format("%d", GameUpdateHandler.START_DELAY_SECONDS - pLoop));
                else
                    mCountDownText.setText(String.format("GO"));
            }

            @Override
            public void onLoopFinished(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if (pLoop == pLoopCount - 1)
                    mCountDownText.setText("");
            }
        }));

        scene.setTouchAreaBindingOnActionDownEnabled(true);
        gameUpdateHandler = new GameUpdateHandler(gameField, character, new Level1(game));
        scene.registerUpdateHandler(gameUpdateHandler);

        setPowerUps(scene);

        scene.attachChild(mCountDownText);
        scene.attachChild(mScoreText);
        scene.attachChild(timerText);
        scene.attachChild(mTmpScoreText);
        scene.attachChild(character);

        scene.sortChildren();
    }

    private void setPowerUps(Scene scene) {
        int x = 20, y = 70;
        for (PowerUp powerUp : character.getPowerUps()) {
            powerUp.init(x, y, gameField, gameUpdateHandler, character, game.getVertexBufferObjectManager());
            scene.attachChild(powerUp.getSprite());
            scene.registerTouchArea(powerUp.getSprite());
            y += 95;
        }

        x = 716; y = 70;
        for (PowerUp powerUp : character.getPowerUps()) {
            powerUp.init(x, y, gameField, gameUpdateHandler, character, game.getVertexBufferObjectManager());
            scene.attachChild(powerUp.getSprite());
            scene.registerTouchArea(powerUp.getSprite());
            y += 95;
        }
    }

    @Override
    public void onPause() {
        pauseGame();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onSwitchOff() {
    }

    @Override
    public void onSwitchOn() {
        playSoundEffects = game.getSettingsBoolean(Settings.EFFECTS);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && paused)
            unpauseGame();
    }

    public void increaseScore() {
        scoreBase++;
        tmpScore += scoreBase;
        mTmpScoreText.setText(String.format("%d", tmpScore));
    }

    public void increaseTmpScore(int increaseBy) {
        tmpScore += increaseBy;
    }

    public void multiplyTmpScore(int multiplier) {
        tmpScore *= multiplier;
    }

    public void increaseScore(int increaseBy) {
        tmpScore += increaseBy;
    }

    public void multiplyScore(int multiplier) {
        tmpScore *= multiplier;
    }

    public void increaseScoreBase(int increaseBy) {
        scoreBase += increaseBy;
    }
    public void multiplyScoreBase(int multiplier) {
        scoreBase *= multiplier;
    }

    public void countScore() {
        increaseScore((int)tmpScore);
        tmpScore = 0;
        scoreBase = 0;
        printScore();
        mScoreText.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.1f, 1f, 1.5f), new ScaleModifier(0.3f, 1.5f, 1f)));
        mScoreText.setScale(1.2f);
    }

    public void playRebuildSound() {
        if (playSoundEffects)
            game.getResourceHandler().getRebuildSound().play();
    }

    public void printScore() {
        mScoreText.setText(String.format("%s%d", game.getString(R.string.score_text), score));
        mTmpScoreText.setText(String.format("%d", tmpScore));
    }

    public void saveScore() {
        Score.insertScore(new Score(score, ((Long) System.currentTimeMillis()).toString()), game.getDatabase());
        game.saveScore(score);
    }

    public void needRefreshField() {
        gameField.refreshFieldNeeded();
    }
}

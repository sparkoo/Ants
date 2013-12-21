package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Character.LadyBug;
import cz.sparko.Bugmaze.GameField;
import cz.sparko.Bugmaze.GameUpdateHandler;
import cz.sparko.Bugmaze.Helper.Settings;
import cz.sparko.Bugmaze.Level.Endless;
import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Level.LevelMinScore;
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

    private Text scoreText;
    private Text tmpScoreText;
    private Text countDownText;
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

    public static GameField getGameField() { return gameField; }

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
        //GameManager.getGameData().updateCoins(50);
        //GameManager.getGameData().save(game);
        MenuManager.getInstance().goToMenu(MenuEnum.MAIN);
    }

    public void showResultScreen(boolean completed) {
        scene.setChildScene(new Results(game.getCamera(), game, score, runTime, level, completed));
        if (completed) {
            game.setSharedPreferencesBoolean(level.getNextLevel().getClass().getName(), true);
            if (score > game.getSharePreferencesLong(level.getClass().getName() + "_score")) {
                game.setSharePreferencesLong(level.getClass().getName() + "_score", score);
            }
        }
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
        gameField.createField(new Endless(game));

        scene.setBackground(new Background(0.17f, 0.61f, 0f));

        character = new LadyBug(0, 0, game, level.getSpeed());
        character.setStartPosition(gameField.getActiveBlock());

        scoreText = new Text(10, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("Score: %020d", score), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        tmpScoreText = new Text((game.CAMERA_WIDTH) / 2 - 50, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("%020d", tmpScore), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        printScore();
        scoreText.setZIndex(101);
        tmpScoreText.setZIndex(101);

        timerText = new Text((game.CAMERA_WIDTH) / 2 + 200, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("%02d:%02d", 0, 0), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        timerText.setZIndex(101);

        countDownText = new Text(game.CAMERA_WIDTH / 2, game.CAMERA_HEIGHT / 2, game.getResourceHandler().getFontIndieFlower36(), String.format("  "), new TextOptions(HorizontalAlign.CENTER), game.getVertexBufferObjectManager());
        countDownText.setZIndex(101);
        countDownText.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1f, 1f, 10f), GameUpdateHandler.START_DELAY_SECONDS + 1, new LoopEntityModifier.ILoopEntityModifierListener() {
            @Override
            public void onLoopStarted(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {


                if (pLoop < pLoopCount - 1)
                    countDownText.setText(String.format("%d", GameUpdateHandler.START_DELAY_SECONDS - pLoop));
                else
                    countDownText.setText(String.format("GO"));
            }

            @Override
            public void onLoopFinished(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if (pLoop == pLoopCount - 1)
                    countDownText.setText("");
            }
        }));

        if (level instanceof LevelMinScore) {
            Text targetScoreText = new Text(-25, game.CAMERA_HEIGHT - 60, game.getResourceHandler().getFontIndieFlower36(), String.format("Target: %d", ((LevelMinScore) level).getTargetScore()), new TextOptions(HorizontalAlign.CENTER), game.getVertexBufferObjectManager());
            targetScoreText.setScale(0.7f);
            targetScoreText.setZIndex(101);
            scene.attachChild(targetScoreText);
        }

        Text levelText = new Text(0, 0, game.getResourceHandler().getFontIndieFlower36(), level.getClass().getSimpleName(), game.getVertexBufferObjectManager());
        levelText.setScale(0.7f);
        levelText.setZIndex(101);
        levelText.setPosition(game.CAMERA_WIDTH - levelText.getWidth(), game.CAMERA_HEIGHT - 60);
        scene.attachChild(levelText);


        scene.setTouchAreaBindingOnActionDownEnabled(true);
        gameUpdateHandler = new GameUpdateHandler(gameField, character, level);
        scene.registerUpdateHandler(gameUpdateHandler);

        setPowerUps(scene);

        scene.attachChild(countDownText);
        scene.attachChild(scoreText);
        scene.attachChild(timerText);
        scene.attachChild(tmpScoreText);
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
        tmpScoreText.setText(String.format("%d", tmpScore));
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
        score += tmpScore;
        tmpScore = 0;
        scoreBase = 0;
        printScore();
        scoreText.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.1f, 1f, 1.5f), new ScaleModifier(0.3f, 1.5f, 1f)));
        scoreText.setScale(1.2f);
    }

    public void playRebuildSound() {
        if (playSoundEffects)
            game.getResourceHandler().getRebuildSound().play();
    }

    public void printScore() {
        scoreText.setText(String.format("%s%d", game.getString(R.string.score_text), score));
        tmpScoreText.setText(String.format("%d", tmpScore));
    }

    public void saveScore() {
        Score.insertScore(new Score(score, ((Long) System.currentTimeMillis()).toString()), game.getDatabase());
        game.saveScore(score);
    }

    public void needRefreshField() {
        gameField.refreshFieldNeeded();
    }
}

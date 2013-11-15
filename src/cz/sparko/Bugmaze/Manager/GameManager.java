package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.*;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Character.Character;
import cz.sparko.Bugmaze.Character.LadyBug;
import cz.sparko.Bugmaze.Helper.Settings;
import cz.sparko.Bugmaze.Menu.MenuEnum;
import cz.sparko.Bugmaze.Model.ScoreDTO;
import cz.sparko.Bugmaze.PowerUp.PowerUp;
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

    private GamePause pauseScene;
    private boolean paused = false;
    private boolean running = false;

    private static Character character = null;
    private static GameField gameField;

    private boolean playSoundEffects;

    private Text mScoreText;
    private Text mTmpScoreText;
    private Text mCountDownText;
    private long score = 0;
    private long tmpScore = 0;

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

    public void startGame() {
        score = 0;
        tmpScore = 0;
        startRunning();
        game.switchManager(this);
    }

    public void gameOver() {
        game.switchManager(MenuManager.getInstance());
        GameManager.getGameData().updateCoins(50);
        GameManager.getGameData().save(game);
        MenuManager.getInstance().goToMenu(MenuEnum.MAIN);
    }

    public void showResultScreen() {
        scene.setChildScene(new GameResults(game.getCamera(), scene, game, score));
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

        pauseScene = new GamePause(game.getCamera(), scene, game);

        gameField = new GameField(game, scene);
        gameField.createField();

        scene.setBackground(new Background(0.17f, 0.61f, 0f));

        character = new LadyBug(0, 0, game);
        character.setStartPosition(gameField.getActiveBlock());

        mScoreText = new Text(10, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("Score: %020d", score), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        mTmpScoreText = new Text((game.CAMERA_WIDTH) / 2, -5, game.getResourceHandler().getFontIndieFlower36(), String.format("%020d", tmpScore), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        printScore();
        mScoreText.setZIndex(101);
        mTmpScoreText.setZIndex(101);

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
        gameUpdateHandler = new GameUpdateHandler(gameField, character);
        scene.registerUpdateHandler(gameUpdateHandler);

        setPowerUps(scene);

        scene.attachChild(mCountDownText);
        scene.attachChild(mScoreText);
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
        tmpScore++;
        mTmpScoreText.setText(String.format("%d", tmpScore * tmpScore));
    }

    public void increaseScore(long increaseBy) {
        tmpScore += increaseBy;
    }

    public void increaseScore(int multiplier) {
        tmpScore *= multiplier;
    }

    public void countScore() {
        score += tmpScore * tmpScore;
        tmpScore = 0;
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
        mTmpScoreText.setText(String.format("%d", tmpScore * tmpScore));
    }

    public void saveScore() {
        //SQLite
        game.getScoreModel().insertScore(new ScoreDTO(score, ((Long) System.currentTimeMillis()).toString()));
        game.saveScore(score);
    }

    public void needRefreshField() {
        gameField.needRefreshField();
    }
}

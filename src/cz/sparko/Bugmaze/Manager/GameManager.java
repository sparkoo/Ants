package cz.sparko.Bugmaze.Manager;

import android.view.KeyEvent;
import cz.sparko.Bugmaze.*;
import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Character;
import cz.sparko.Bugmaze.Helper.Settings;
import cz.sparko.Bugmaze.Menu.MenuEnum;
import cz.sparko.Bugmaze.Model.ScoreDTO;
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

public class GameManager extends Manager {
    private static GameManager instance;

    private GamePause pauseScene;
    private boolean paused = false;

    private static cz.sparko.Bugmaze.Character character = null;
    private static GameField gameField;

    private boolean playSoundEffects;

    private Text mScoreText;
    private Text mCountDownText;
    private int score = 0;
    private int tmpScore = 0;

    public static GameManager createInstance(Game game) {
        if (instance == null)
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

    public void startGame() {
        game.switchManager(this);
    }

    public void gameOver() {
        game.switchManager(MenuManager.getInstance());
        MenuManager.getInstance().goToMenu(MenuEnum.MAIN);
    }

    public void showResultScreen() {
        scene.setChildScene(new GameResults(game.getCamera(), scene, game.getVertexBufferObjectManager(), score));
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

        pauseScene = new GamePause(game.getCamera(), scene, game.getVertexBufferObjectManager());

        gameField = new GameField(game, scene);
        gameField.createField();

        scene.setBackground(new Background(0.17f, 0.61f, 0f));

        character = new Character(0, 0, game.getVertexBufferObjectManager());
        character.setStartPosition(gameField.getActiveBlock());

        mScoreText = new Text((game.CAMERA_WIDTH - (GameField.FIELD_SIZE_X * Block.SIZE)) / 2, -5, resourceHandler.getFontIndieFlower36(), String.format("Score: %020d", score), new TextOptions(HorizontalAlign. RIGHT), game.getVertexBufferObjectManager());
        printScore();
        mScoreText.setZIndex(101);

        mCountDownText = new Text(game.CAMERA_WIDTH / 2, game.CAMERA_HEIGHT / 2, resourceHandler.getFontIndieFlower36(), String.format("  "), new TextOptions(HorizontalAlign.CENTER), game.getVertexBufferObjectManager());
        mCountDownText.setZIndex(101);
        mCountDownText.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1f, 1f, 10f), GameUpdateHandler.START_DELAY_SECONDS + 1, new LoopEntityModifier.ILoopEntityModifierListener() {
            @Override
            public void onLoopStarted(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if (pLoop < pLoopCount - 1) {
                    mCountDownText.setText(String.format("%d", GameUpdateHandler.START_DELAY_SECONDS - pLoop));
                } else {
                    mCountDownText.setText(String.format("GO"));
                }
            }

            @Override
            public void onLoopFinished(LoopModifier<IEntity> pLoopModifier, int pLoop, int pLoopCount) {
                if (pLoop == pLoopCount - 1)
                    mCountDownText.setText("");
            }
        }));

        scene.setTouchAreaBindingOnActionDownEnabled(true);
        scene.registerUpdateHandler(new GameUpdateHandler(gameField, character));

        scene.attachChild(mCountDownText);
        scene.attachChild(mScoreText);
        scene.attachChild(character);

        scene.sortChildren();
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
        playSoundEffects = game.getSettings(Settings.EFFECTS);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && paused)
            unpauseGame();
    }

    //TODO: text handle
    public void increaseScore() {
        tmpScore++;
        //mScoreText.setText(String.format("%09d + %09d", tmpScore * tmpScore, score));
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
            resourceHandler.getRebuildSound().play();
    }

    private void printScore() {
        mScoreText.setText(String.format("%s%d", game.getString(R.string.score_text), score));
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

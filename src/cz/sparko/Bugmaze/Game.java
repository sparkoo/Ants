package cz.sparko.Bugmaze;

import android.graphics.Typeface;
import android.os.Bundle;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.Block.Block;
import cz.sparko.Bugmaze.Model.ScoreDTO;
import cz.sparko.Bugmaze.Model.ScoreModel;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.util.HorizontalAlign;

import java.sql.SQLException;

//TODO: refactor - was renamed from Field. Make new class Field
//TODO: make some universal gameactivity
public class Game extends GBaseGameActivityAND {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private ScoreModel scoreModel;


    private int score = 0;
    private int tmpScore = 0;
    private Font mScoreFont;
    private Text mScoreText;

    private BuildableBitmapTextureAtlas mBitmapTextureAtlas;

    private Scene mScene;

    private static Character character = null;
    private static GameField gameField;

    public Game() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreModel = new ScoreModel(this);
        gameField = new GameField(this);
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onResumeGame() {
        if (this.mEngine != null)
            super.onResumeGame();
    }

    @Override
    protected void onPause() {
        scoreModel.close();
        super.onPause();
    }

    //TODO: text handle
    public void increaseScore() {
        tmpScore++;
        mScoreText.setText(String.format("%09d + %09d", tmpScore * tmpScore, score));
    }

    public void countScore() {
        score += tmpScore * tmpScore;
        tmpScore = 0;
        mScoreText.setText(String.format("%09d + %09d", tmpScore * tmpScore, score));
    }

    public static Character getCharacter() { return character; }
    public static GameField getGameField() { return gameField; }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }

    @Override
    public void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        gameField.loadResources(this.getTextureManager());

        this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mBitmapTextureAtlas.clearTextureAtlasSources();
        Character.loadResources(this.mBitmapTextureAtlas, this);
        Block.loadResources(this.mBitmapTextureAtlas, this);

        try {
            this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.mBitmapTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }

        this.mScoreFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        this.mScoreFont.load();
    }


    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final float centerX = (CAMERA_WIDTH - Character.getTexture().getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - Character.getTexture().getHeight()) / 2;

        mScene = new Scene();

        gameField.setScene(mScene);
        gameField.createField();

        mScene.setBackground(new Background(0.17f, 0.61f, 0f));

        character = new Character(centerX, centerY, this.getVertexBufferObjectManager());
        character.setPosition(gameField.getActiveBlock().getX() + (Block.SIZE / 2) - (Character.SIZE_X / 2), gameField.getActiveBlock().getY() + (Block.SIZE / 2) - (Character.SIZE_Y / 2));
        character.setRotation(gameField.getActiveBlock().getOutDirection().getDegree());

        mScoreText = new Text(10, 10, this.mScoreFont, String.format("%09d + %09d", tmpScore * tmpScore, score), new TextOptions(HorizontalAlign. RIGHT), this.getVertexBufferObjectManager());
        mScoreText.setZIndex(101);

        mScene.setTouchAreaBindingOnActionDownEnabled(true);
        mScene.registerUpdateHandler(new GameUpdateHandler(this, gameField, character));

        mScene.attachChild(mScoreText);
        mScene.attachChild(character);

        mScene.sortChildren();

        return mScene;
    }

    public void saveScore() {
        //SQLite
        scoreModel.insertScore(new ScoreDTO(score, ((Long)System.currentTimeMillis()).toString()));
        if (mHelper.isSignedIn()) {
            getGamesClient().submitScore(getString(R.string.leaderboard_id), score);
        }
    }

    @Override
    public void onSignInFailed() {
        System.out.println("Sign-in failed.");
    }

    @Override
    public void onSignInSucceeded() {
        System.out.println("Sign-in succeeded.");
    }
}

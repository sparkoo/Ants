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
import org.andengine.entity.sprite.Sprite;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
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
    private BitmapTextureAtlas mBackgroundTextureAtlas;
    private TiledTextureRegion mAntTextRegion;
    private ITextureRegion mBackgroundTexture;

    private Scene mScene;

    private static Ant ant = null;
    private static GameField gameField;

    public Game() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreModel = new ScoreModel(this);
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

    public static Ant getAnt() { return ant; }
    public static GameField getGameField() { return gameField; }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }

    @Override
    public void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512);
        this.mBackgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTextureAtlas, this, "playgroundBack.png", 0, 0);
        this.mBackgroundTextureAtlas.load();

        //TODO: solve better texture handling
        this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mBitmapTextureAtlas.clearTextureAtlasSources();
        this.mAntTextRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "ant.png", 1, 1);
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

        final float centerX = (CAMERA_WIDTH - this.mAntTextRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mAntTextRegion.getHeight()) / 2;

        mScene = new Scene();

        gameField = new GameField(mScene, this);
        gameField.createField();

        mScene.setBackground(new Background(0.17f, 0.61f, 0f));
        Sprite background = new Sprite(0, 0, this.mBackgroundTexture, this.getVertexBufferObjectManager());
        background.setZIndex(99);
        this.mScene.attachChild(background);

        ant = new Ant(centerX, centerY, this.mAntTextRegion, this.getVertexBufferObjectManager());


        ant.setPosition(gameField.getActiveBlock().getX() + (Block.SIZE / 2) - (Ant.SIZE_X / 2), gameField.getActiveBlock().getY() + (Block.SIZE / 2) - (Ant.SIZE_Y / 2));
        ant.setRotation(gameField.getActiveBlock().getOutDirection().getDegree());

        mScoreText = new Text(10, 10, this.mScoreFont, String.format("%09d + %09d", tmpScore * tmpScore, score), new TextOptions(HorizontalAlign. RIGHT), this.getVertexBufferObjectManager());
        mScene.attachChild(mScoreText);

        mScene.setTouchAreaBindingOnActionDownEnabled(true);

        //TODO: update handler to own class
        mScene.registerUpdateHandler(new GameUpdateHandler(this, gameField, ant));
        mScene.attachChild(ant);

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

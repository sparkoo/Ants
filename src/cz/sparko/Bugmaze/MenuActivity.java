package cz.sparko.Bugmaze;

import android.content.Intent;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.Menu.Main;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

//TODO: strings to some strings.xml
public class MenuActivity extends GBaseGameActivityAND {

    private boolean googleServicesConnected = false;

    private Camera camera;
    private Scene scene;

    private BitmapTextureAtlas mFontTexture;
    private Font mFont;

    private ITextureRegion backgroundTexture;
    private BitmapTextureAtlas mBackgroundTextureAtlas;

    @Override
    public void onSignInFailed() {
        googleServicesConnected = false;
    }

    @Override
    public void onSignInSucceeded() {
        googleServicesConnected = true;
    }

    public void handleLeaderboard() {
        if (!googleServicesConnected) {
            beginUserInitiatedSignIn();
        } else {
            startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
        }
    }

    public void startGame() {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    public Font getMenuFont() {
        return mFont;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    protected void onCreateResources() {
        /* Load Font/Textures. */
        this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        FontFactory.setAssetBasePath("font/");
        this.mFont = FontFactory.createFromAsset(this.getFontManager(), this.mFontTexture, this.getAssets(), "Indie_Flower.ttf", 40, true, Color.WHITE.getABGRPackedInt());
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.getFontManager().loadFont(this.mFont);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBackgroundTextureAtlas, this, "menuBack.png", 0, 0);
        this.mBackgroundTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, backgroundTexture, this.getVertexBufferObjectManager())));

        scene.setChildScene(new Main(this).getMenuScene());

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }
}
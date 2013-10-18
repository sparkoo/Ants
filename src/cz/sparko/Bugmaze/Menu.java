package cz.sparko.Bugmaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import javax.microedition.khronos.opengles.GL10;

//TODO: strings to some strings.xml
public class Menu extends GBaseGameActivityAND implements MenuScene.IOnMenuItemClickListener {

    private boolean googleServicesConnected = false;

    private Camera camera;
    private MenuScene menuScene;
    private Scene scene;

    private BitmapTextureAtlas mFontTexture;
    private Font mFont;

    private ITextureRegion backgroundTexture;
    private BitmapTextureAtlas mBackgroundTextureAtlas;

    /*
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        scoreBtn = (Button)findViewById(R.id.btnScore);
        signInBtn = (SignInButton)findViewById(R.id.signInButton);

        Button btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Menu.this.startActivity(new Intent(Menu.this, Game.class));
                Menu.this.finish();
            }
        });

        scoreBtn.setText("signing in ...");
        signInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                beginUserInitiatedSignIn();
            }
        });

        Button closeButton = (Button)findViewById(R.id.exitButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Menu.this.finish();
            }
        });
    }
*/
    @Override
    public void onSignInFailed() {
        googleServicesConnected = false;
        /*if (scoreBtn == null || signInBtn == null)  return;
        scoreBtn.setText("to enable leaderboard please log in to g+");
        scoreBtn.setOnClickListener(null);
        signInBtn.setVisibility(ViewStub.VISIBLE);
        */
    }

    @Override
    public void onSignInSucceeded() {
        googleServicesConnected = true;
        /*if (scoreBtn == null || signInBtn == null)  return;
        scoreBtn.setText("Leaderboard");
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
            }
        });
        signInBtn.setVisibility(View.GONE);*/
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

        menuScene = new MenuScene(camera);
        menuScene.setBackground(new SpriteBackground(new Sprite(0, 0, backgroundTexture, this.getVertexBufferObjectManager())));

        final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, this.mFont, "Play", this.getVertexBufferObjectManager()), new Color(0.2f, 0.2f, 0.2f), new Color(0.9f, 0.9f, 0.9f));
        playMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        playMenuItem.setPosition(180, 150);
        menuScene.addMenuItem(playMenuItem);

        final IMenuItem leaderboardMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, this.mFont, "Leaderboard", this.getVertexBufferObjectManager()), new Color(0.2f, 0.2f, 0.2f), new Color(0.9f, 0.9f, 0.9f));
        leaderboardMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        leaderboardMenuItem.setPosition(180, 220);
        menuScene.addMenuItem(leaderboardMenuItem);

        final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2, this.mFont, "Options", this.getVertexBufferObjectManager()), new Color(0.2f, 0.2f, 0.2f), new Color(0.9f, 0.9f, 0.9f));
        optionsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        optionsMenuItem.setPosition(180, 290);
        menuScene.addMenuItem(optionsMenuItem);

        final IMenuItem helpMenuItem = new ColorMenuItemDecorator(new TextMenuItem(3, this.mFont, "Help", this.getVertexBufferObjectManager()), new Color(0.2f, 0.2f, 0.2f), new Color(0.9f, 0.9f, 0.9f));
        helpMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        helpMenuItem.setPosition(180, 360);
        menuScene.addMenuItem(helpMenuItem);

        menuScene.setOnMenuItemClickListener(this);

        scene.setChildScene(menuScene);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, Game.CAMERA_WIDTH, Game.CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case 0:
                Menu.this.startActivity(new Intent(Menu.this, Game.class));
                Menu.this.finish();
                break;
            case 1:
                if (!googleServicesConnected) {
                    beginUserInitiatedSignIn();
                } else {
                    startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
                }
                break;
        }
        return false;
    }
}
package cz.sparko.Bugmaze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.Menu.Main;
import cz.sparko.Bugmaze.Menu.Menu;
import cz.sparko.Bugmaze.Menu.MenuEnum;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
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
import org.andengine.util.color.Color;

import java.io.IOException;

//TODO: strings to some strings.xml
public class MenuActivity extends GBaseGameActivityAND {


    private Camera camera;
    private Scene scene;

    Music mMusic;

    private SharedPreferences prefs;
    private final String SHARED_PREFS_KEY = "settings";
    public static final String SETTINGS_MUSIC = "music";
    public static final String SETTINGS_EFFECTS = "effects";
    public static final String SETTINGS_GRAPHICS = "graphics";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
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

    }

    public void playMusic() {
        if (prefs.getBoolean(SETTINGS_MUSIC, true))
            mMusic.play();
    }

    public void stopMusic() {
        mMusic.stop();
    }

    public void pauseMusic() {
        mMusic.pause();
    }

    @Override
    public synchronized void onResumeGame() {
        if (mMusic != null && !mMusic.isPlaying()) {
            playMusic();
        }

        super.onResumeGame();
    }

    @Override
    public synchronized void onPauseGame() {
        if (mMusic != null && mMusic.isPlaying()) {
            pauseMusic();
        }

        super.onPauseGame();
    }

    @Override
    protected Scene onCreateScene() {
        if (prefs.getBoolean(SETTINGS_MUSIC, true))
            mMusic.play();

        scene = new Scene();
        scene.setBackground(new SpriteBackground(new Sprite(0, 0, backgroundTexture, this.getVertexBufferObjectManager())));

        currentMenu = Menu.getMenu(MenuEnum.MAIN);
        scene.setChildScene(currentMenu.getMenuScene());

        return scene;
    }

    public void setCurrentMenu(Menu newMenu) {
        currentMenu = newMenu;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) currentMenu.goBack();
        return false;
    }
}
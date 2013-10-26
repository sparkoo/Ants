package cz.sparko.Bugmaze.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Manager.Manager;
import cz.sparko.Bugmaze.Manager.MenuManager;
import cz.sparko.Bugmaze.Model.ScoreModel;
import cz.sparko.Bugmaze.R;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Bugmaze.Helper.Settings;
import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;

import java.sql.SQLException;

public class Game extends GBaseGameActivityAND {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private boolean googleServicesConnected = false;
    private Scene scene;
    private Camera camera;

    private ScoreModel scoreModel;

    private SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private Manager activeManager;

    private ResourceHandler resourceHandler;

    private Music music;

    public Scene getScene() { return scene; }
    public Camera getCamera() { return camera; }
    public ScoreModel getScoreModel() { return scoreModel; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoreModel = new ScoreModel(this);
        prefs = getSharedPreferences(getString(R.string.shared_preferences_settings_key), Context.MODE_PRIVATE);

        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getSettings(Settings settings) {
        return prefs.getBoolean(settings.toString(), true);
    }

    public void setSettings(Settings key, boolean value) {
        if (editor == null)
            editor = prefs.edit();
        editor.putBoolean(key.toString(), value);
        editor.commit();
    }

    @Override
    public void onCreateResources() {
        resourceHandler.getInstance().loadResource(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activeManager != null)
            activeManager.onResume();
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
        if (activeManager != null)
            activeManager.onPause();
        super.onPause();
    }

    @Override
    public void onSignInFailed() {
        googleServicesConnected = false;
    }

    @Override
    public void onSignInSucceeded() {
        googleServicesConnected = true;
    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        MenuManager.createInstance(this);
        GameManager.createInstance(this);

        switchManager(MenuManager.getInstance());
        return scene;
    }

    public void switchManager(Manager manager) {
        if (activeManager != null)
            activeManager.onSwitchOff();
        activeManager = manager;
        manager.onSwitchOn();

        scene = activeManager.getScene();
        mEngine.setScene(scene);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, Game.CAMERA_WIDTH, Game.CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    public void goToLeaderboard() {
        if (!googleServicesConnected) {
            beginUserInitiatedSignIn();
        } else {
            startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
        }
    }

    public void setMusic(Music music) {
        this.music = music;
        this.music.setLooping(true);
    }

    public void playMusic() {
        music.play();
    }

    public void pauseMusic() {
        music.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //if (keyCode == KeyEvent.KEYCODE_BACK) currentMenu.goBack();
        return false;
    }

    public void saveScore(int score) {
        if (mHelper.isSignedIn()) {
            getGamesClient().submitScore(getString(R.string.leaderboard_id), score);
        }
    }
}

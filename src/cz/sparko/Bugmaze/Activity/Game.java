package cz.sparko.Bugmaze.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import com.google.android.gms.appstate.OnStateDeletedListener;
import com.google.android.gms.appstate.OnStateLoadedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.GameDataEnum;
import cz.sparko.Bugmaze.Helper.Settings;
import cz.sparko.Bugmaze.Manager.GameManager;
import cz.sparko.Bugmaze.Manager.Manager;
import cz.sparko.Bugmaze.Manager.MenuManager;
import cz.sparko.Bugmaze.Model.GameData;
import cz.sparko.Bugmaze.Model.ScoreDTO;
import cz.sparko.Bugmaze.Model.ScoreModel;
import cz.sparko.Bugmaze.R;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;

import com.google.android.gms.appstate.AppStateClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;

public class Game extends GBaseGameActivityAND implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, DialogInterface.OnCancelListener, OnStateLoadedListener, OnStateDeletedListener {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private boolean googleServicesConnected = false;
    private Scene scene;
    private Camera camera;

    private ResourceHandler resourceHandler;

    private ScoreModel scoreModel;

    private SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private Manager activeManager;

    private Music music;

    public Scene getScene() { return scene; }
    public Camera getCamera() { return camera; }
    public ScoreModel getScoreModel() { return scoreModel; }
    public ResourceHandler getResourceHandler() { return resourceHandler; }

    public Game() {
        super(CLIENT_APPSTATE | CLIENT_GAMES);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoreModel = new ScoreModel(this);
        prefs = getSharedPreferences(getString(R.string.shared_preferences_settings_key), Context.MODE_PRIVATE);

        MenuManager.setGameData(GameData.getGameDataFromSharedPreferences(this));
        MenuManager.getGameData().saveGameDataToSharedPreferences(this);

        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getSettingsBoolean(Settings settings) {
        return prefs.getBoolean(settings.toString(), true);
    }

    public void setSettingsBoolean(Settings key, boolean value) {
        if (editor == null)
            editor = prefs.edit();
        editor.putBoolean(key.toString(), value);
        editor.commit();
    }

    public int getGameDataInt(GameDataEnum key) {
        return prefs.getInt(key.toString(), 0);
    }
    public long getGameDataTimestamp() {
        return prefs.getLong(GameDataEnum.TIMESTAMP.toString(), Calendar.getInstance().getTimeInMillis());
    }
    public void setGameData(GameDataEnum key, int value) {
        if (editor == null)
            editor = prefs.edit();
        editor.putInt(key.toString(), value);
        editor.commit();
    }

    public int getGameDataLong(GameDataEnum key) {
        return prefs.getInt(key.toString(), 0);
    }
    public void setGameData(GameDataEnum key, long value) {
        if (editor == null)
            editor = prefs.edit();
        editor.putLong(key.toString(), value);
        editor.commit();
    }

    @Override
    public void onGameCreated() {
        super.onGameCreated();
    }

    @Override
    public void onCreateResources() {
        resourceHandler = new ResourceHandler(this);
        Manager.setResourceHandler(resourceHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public synchronized void onResumeGame() {
        if (this.mEngine != null)
            super.onResumeGame();
        if (activeManager != null)
            activeManager.onResume();
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mEngine.start();
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
        syncScoreWithGoogle();

        //getAppStateClient().deleteState(this, getInteger(R.integer.cloud_game_data_key));

        getAppStateClient().loadState(this, getInteger(R.integer.cloud_game_data_key));
    }

    public void syncScoreWithGoogle() {
        ScoreDTO highScore = null;
        for (ScoreDTO score : scoreModel.getAllScores()) {
            if (highScore == null || score.getScore() > highScore.getScore())
                highScore = Collections.max(scoreModel.getAllScores());
        }
        if (highScore != null)
            saveScore(highScore.getScore());
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
        if (googleServicesConnected) {
            startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
        } else {
            beginUserInitiatedSignIn();
        }
    }

    public void setMusic(Music music) {
        this.music = music;
        this.music.setLooping(true);
    }

    public void playMusic() {
        if (!music.isPlaying())
            music.play();
    }

    public void pauseMusic() {
        if (music.isPlaying())
            music.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        activeManager.onKeyDown(keyCode, event);
        return false;
    }

    public void saveScore(long score) {
        if (mHelper.isSignedIn()) {
            getGamesClient().submitScore(getString(R.string.leaderboard_id), score);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("PRD", "onConnectionFailed");
    }

    private GameData getSyncedGameData(GameData cloud) {
        GameData local = GameData.getGameDataFromSharedPreferences(this);
        if (cloud.compareTo(local) > 0)
            return cloud;
        return local;
    }

    @Override
    public void onStateLoaded(int statusCode, int stateKey, byte[] data) {
        if (statusCode == AppStateClient.STATUS_OK) {
            try {
                MenuManager.setGameData(getSyncedGameData(GameData.getGameDataFromByteStream(data)));
                Log.e("GAMEDATA", MenuManager.getGameData().toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("GAMEDATA", e.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("GAMEDATA", e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                getAppStateClient().updateState(getInteger(R.integer.cloud_game_data_key), MenuManager.getGameData().getByteStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStateConflict(int stateKey, String ver,  byte[] localData, byte[] serverData) {
    }

    public int getInteger(int resId) {
        return getBaseContext().getResources().getInteger(resId);
    }

    @Override
    public void onStateDeleted(int i, int i2) {
    }
}

package cz.sparko.Bugmaze.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import com.google.android.gms.appstate.AppStateClient;
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
import cz.sparko.Bugmaze.Model.Schema;
import cz.sparko.Bugmaze.Model.Score;
import cz.sparko.Bugmaze.R;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import cz.sparko.Database.DBHelper;
import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;

//TODO: make external listener
public class Game extends GBaseGameActivityAND implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, DialogInterface.OnCancelListener, OnStateLoadedListener, OnStateDeletedListener {
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    private boolean googleServicesConnected = false;
    private Scene scene;
    private Camera camera;

    private ResourceHandler resourceHandler;

    private SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private Manager activeManager;

    private Music music;

    public Scene getScene() { return scene; }
    public Camera getCamera() { return camera; }
    public ResourceHandler getResourceHandler() { return resourceHandler; }
    public SQLiteDatabase getDatabase() { return db; }

    public Game() {
        super(CLIENT_APPSTATE | CLIENT_GAMES);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this.getBaseContext(), new Schema(this.getBaseContext()));
        db = dbHelper.getWritableDatabase();

        prefs = getSharedPreferences(getString(R.string.shared_preferences_settings_key), Context.MODE_PRIVATE);

        MenuManager.setGameData(GameData.getGameDataFromSharedPreferences(this));
        MenuManager.getGameData().saveGameDataToSharedPreferences(this);
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

        db = dbHelper.getWritableDatabase();

        mEngine.start();
    }

    @Override
    protected void onPause() {
        dbHelper.close();
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
        Score highScore = null;
        for (Score score : Score.getAllScores(db)) {
            if (highScore == null || score.getScore() > highScore.getScore())
                highScore = Collections.max(Score.getAllScores(db));
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            updateGameData();
        }
    }

    public void updateGameData() {
        if (getAppStateClient().isConnected()) {
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

    public void printGameData() {
    }
}

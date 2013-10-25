package cz.sparko.Bugmaze;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.example.games.basegameutils.GBaseGameActivityAND;
import cz.sparko.Bugmaze.Model.ScoreModel;
import cz.sparko.Bugmaze.Resource.ResourceHandler;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;

import java.sql.SQLException;

public class Game extends GBaseGameActivityAND {
    private boolean googleServicesConnected = false;
    private Scene scene;
    private Camera camera;

    private ScoreModel scoreModel;

    private SharedPreferences prefs;

    private GameManager gameManager;
    private MenuManager menuManager;

    private ResourceHandler resourceHandler;

    public Scene getScene() { return scene; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameManager = GameManager.getInstance();
        menuManager = MenuManager.getInstance();

        scoreModel = new ScoreModel(this);
        prefs = getSharedPreferences(getString(R.string.shared_preferences_settings_key), Context.MODE_PRIVATE);
        playSoundEffects = prefs.getBoolean(SETTINGS_EFFECTS, true);
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateResources() {
        resourceHandler.getInstance().loadResource(this);
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
    }

    @Override
    protected Scene onCreateScene() {
        return null;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        return null;
    }

    public void goToLeaderboard() {
        if (!googleServicesConnected) {
            beginUserInitiatedSignIn();
        } else {
            startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
        }
    }
}

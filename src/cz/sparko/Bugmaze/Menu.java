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
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

//TODO: strings to some strings.xml
public class Menu extends GBaseGameActivityAND implements MenuScene.IOnMenuItemClickListener {

    Button scoreBtn = null;
    SignInButton signInBtn = null;

    Camera camera;
    MenuScene menuScene;
    Scene scene;

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
        if (scoreBtn == null || signInBtn == null)  return;
        scoreBtn.setText("to enable leaderboard please log in to g+");
        scoreBtn.setOnClickListener(null);
        signInBtn.setVisibility(ViewStub.VISIBLE);
    }

    @Override
    public void onSignInSucceeded() {
        if (scoreBtn == null || signInBtn == null)  return;
        scoreBtn.setText("Leaderboard");
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
            }
        });
        signInBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onCreateResources() {
    }

    @Override
    protected Scene onCreateScene() {
        scene = new Scene();
        scene.setBackground(new Background(Color.WHITE));

        menuScene = new MenuScene(camera);

        scene.setChildScene(menuScene);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, Game.CAMERA_WIDTH, Game.CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        return false;
    }
}
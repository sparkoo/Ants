package cz.sparko.Bugmaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.GBaseGameActivity;
//TODO: strings to some strings.xml
public class Menu extends GBaseGameActivity {
    Button scoreBtn = null;
    SignInButton signInBtn = null;

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
}
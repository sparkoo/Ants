package cz.sparko.Bugmaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.GBaseGameActivity;

public class Menu extends GBaseGameActivity {
    private boolean justLoggedIn = false;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Menu.this.startActivity(new Intent(Menu.this, Game.class));
                Menu.this.finish();
            }
        });

        SignInButton signInButton = (SignInButton)findViewById(R.id.signInButton);
        Button btnScore = (Button)findViewById(R.id.btnScore);

        btnScore.setText("to enable leaderboard please log in to g+");
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                beginUserInitiatedSignIn();
                justLoggedIn = true;
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
    }

    @Override
    public void onSignInSucceeded() {
        SignInButton signInButton = (SignInButton)findViewById(R.id.signInButton);
        Button btnScore = (Button)findViewById(R.id.btnScore);
        btnScore.setText("Leaderboard");
        if (justLoggedIn) {
            finish();
            startActivity(getIntent());
            justLoggedIn = false;
        } else {
            btnScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1337);
                }
            });
            signInButton.setVisibility(View.GONE);
        }
    }
}
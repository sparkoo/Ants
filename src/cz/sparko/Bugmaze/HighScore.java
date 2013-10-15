package cz.sparko.Bugmaze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.GBaseGameActivity;
import cz.sparko.Bugmaze.Model.ScoreDTO;
import cz.sparko.Bugmaze.Model.ScoreModel;

import java.sql.SQLException;
import java.util.List;

public class HighScore extends GBaseGameActivity {

    //TODO: db handler
    private ScoreModel scoreModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score);

        Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HighScore.this.startActivity(new Intent(HighScore.this, Menu.class));
                HighScore.this.finish();
            }
        });

        TextView scoreView = (TextView)findViewById(R.id.textView);

        SignInButton signInButton = (SignInButton)findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                beginUserInitiatedSignIn();
            }
        });





        scoreModel = new ScoreModel(this);
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ScoreDTO> scores = scoreModel.getAllScores();
        for (ScoreDTO score : scores)
            scoreView.append(score.getTimestamp() + ": " + score.getScore() + " ( " + score.getId() + " )\n");

        //TODO: display score list in ListView
    }

    @Override
    protected void onResume() {
        try {
            scoreModel.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        scoreModel.close();
        super.onPause();
    }

    @Override
    public void onSignInFailed() {
        System.out.println("Sign-in failed.");
    }

    @Override
    public void onSignInSucceeded() {
        System.out.println("Sign-in succeeded.");
    }
}

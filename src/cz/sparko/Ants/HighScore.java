package cz.sparko.Ants;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cz.sparko.Ants.Models.ScoreDTO;
import cz.sparko.Ants.Models.ScoreModel;

import java.sql.SQLException;
import java.util.List;

public class HighScore extends Activity {

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

        //TODO: display score list
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
}

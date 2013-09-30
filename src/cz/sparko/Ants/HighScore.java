package cz.sparko.Ants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScore extends Activity {

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

        //TODO: display score list
    }

}

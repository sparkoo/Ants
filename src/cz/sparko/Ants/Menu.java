package cz.sparko.Ants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {

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

        Button closeButton = (Button)findViewById(R.id.exitButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Menu.this.finish();
            }
        });
    }
}
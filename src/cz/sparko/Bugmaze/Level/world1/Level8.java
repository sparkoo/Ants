package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level8 extends LevelMinScore {
    public Level8(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 0.9f;
    }

    @Override
    protected int getMinScore() {
        return 1500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level9(game);
    }
}

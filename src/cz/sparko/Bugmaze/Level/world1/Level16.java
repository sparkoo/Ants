package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level16 extends LevelMinScore {
    public Level16(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 0.6f;
    }

    @Override
    protected int getMinScore() {
        return 5000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level17(game);
    }
}

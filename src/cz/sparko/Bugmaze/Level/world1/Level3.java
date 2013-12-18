package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level3 extends LevelMinScore {
    public Level3(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    protected int getMinScore() {
        return 500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level4(game);
    }
}

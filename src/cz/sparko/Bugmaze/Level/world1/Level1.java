package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level1 extends LevelMinScore {
    public Level1(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1;
    }

    @Override
    protected int getMinScore() {
        return 100;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level2(game);
    }
}

package cz.sparko.Bugmaze.Level.world1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level1 extends LevelMinScore {
    public Level1(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 2;
    }

    @Override
    protected int getMinScore() {
        return 500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level2(game);
    }
}

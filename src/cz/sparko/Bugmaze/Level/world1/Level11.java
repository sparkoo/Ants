package cz.sparko.Bugmaze.Level.world1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level11 extends LevelMinScore {
    protected Level11(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 0.8f;
    }

    @Override
    protected int getMinScore() {
        return 3000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level12(game);
    }
}

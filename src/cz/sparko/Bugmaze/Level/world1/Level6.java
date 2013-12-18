package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level6 extends LevelMinScore {
    public Level6(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 0.8f;
    }

    @Override
    protected int getMinScore() {
        return 1000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level7(game);
    }
}

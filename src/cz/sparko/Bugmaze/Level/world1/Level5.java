package cz.sparko.Bugmaze.Level.world1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level5 extends LevelMinScore {
    protected Level5(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    protected int getMinScore() {
        return 1000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level6(game);
    }
}

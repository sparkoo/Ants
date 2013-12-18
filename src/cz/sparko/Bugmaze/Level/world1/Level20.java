package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level20 extends LevelMinScore {
    public Level20(Game game) {
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
}

package cz.sparko.Bugmaze.Level;

import cz.sparko.Bugmaze.Activity.Game;

public class LevelTest extends LevelMinScore {
    public LevelTest(Game game) {
        super(game);
    }

    @Override
    protected int getMinScore() {
        return 5;
    }

    @Override
    public float getSpeed() {
        return 1;
    }
}

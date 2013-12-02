package cz.sparko.Bugmaze.Level.world1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level8 extends LevelMinScore {
    protected Level8(Game game) {
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

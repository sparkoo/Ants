package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level2 extends LevelMinScore {
    public Level2(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1.5f;
    }

    @Override
    protected int getMinScore() {
        return 250;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level3(game);
    }
}

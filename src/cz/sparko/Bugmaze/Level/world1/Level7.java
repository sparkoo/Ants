package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level7 extends LevelMinScore {
    public Level7(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1.2f;
    }

    @Override
    protected int getMinScore() {
        return 1500;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level8(game);
    }
}

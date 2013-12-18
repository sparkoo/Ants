package cz.sparko.Bugmaze.Level.World1;

import cz.sparko.Bugmaze.Activity.Game;
import cz.sparko.Bugmaze.Level.LevelMinScore;

public class Level10 extends LevelMinScore {
    public Level10(Game game) {
        super(game);
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    protected int getMinScore() {
        return 3000;
    }

    @Override
    protected void initNextLevel() {
        this.nextLevel = new Level11(game);
    }
}
